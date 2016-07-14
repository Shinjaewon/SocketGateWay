package io.vertx.main;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.net.NetSocket;

public class NxmileGateWayMain {

	private static final Logger log = LoggerFactory.getLogger(NxmileGateWayMain.class);
	public static void main(String[] args) { 
	 
	    Vertx vertx = Vertx.vertx(); 
	    NetServer server = vertx.createNetServer(
    	      new NetServerOptions().setPort(19006).setHost("127.0.0.1")
	    );
	    server.connectHandler(sock -> {
	    	System.out.println("Server to connected");
	    	Buffer requestBufferData = Buffer.buffer();
	    	Buffer resultBufferData = Buffer.buffer();
		    sock.handler(requestBuffers -> {
		    	if (!sock.writeQueueFull()) {
		    		requestBufferData.appendBuffer(requestBuffers);
		    		int requestBodyLen = Integer.parseInt(new String(requestBufferData.getBytes(), 34, 4));
		    		if (requestBodyLen + 50 == requestBufferData.length()) {
		    			vertx.createNetClient().connect(19008, "127.0.0.1", res -> {
		    				if (res.succeeded()) {
		    					System.out.println("res to connected");
		    					NetSocket socket = res.result();
		    					socket.handler(resultBuffers -> {
		    						resultBufferData.appendBuffer(resultBuffers);
		    						int resultBufferLen = Integer.parseInt(new String(resultBufferData.getBytes(), 34, 4));
		    						if (resultBufferLen + 50 == resultBufferData.length()){
		    							System.out.println("resultBufferData ::" + resultBufferData.getString(0, resultBufferData.length()));
		    							sock.write(resultBufferData);
		    						}
		    					});
		    					System.out.println("requestBufferData ::" + requestBufferData.getString(0, requestBufferData.length()));
		    					socket.write(requestBufferData);
		    				}else{
		    					System.out.println("Socket Failed to  connect");
		    				}
		    				
		    			});
		    		}
		    	} else {
		    		System.out.println("Sock Failed to connect");
		    	}
		    });
	    }).listen();
	}
}
