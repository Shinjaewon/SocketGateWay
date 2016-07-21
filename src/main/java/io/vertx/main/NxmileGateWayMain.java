package io.vertx.main;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.net.NetSocket;

public class NxmileGateWayMain extends AbstractVerticle  {

	private static final Logger log = LoggerFactory.getLogger(NxmileGateWayMain.class);
	
	@Override
	public void start() throws Exception { 

		JsonObject config = context.config();
		String serverIp = config.getString("serverIp");
		int serverPort = Integer.valueOf(config.getString("serverPort"));
		String gateWayClientIp = config.getString("gateWayClientIp");
		int gateWayClientPort = Integer.valueOf(config.getString("gateWayClientPort"));
		
		System.out.println(serverIp + "|||" + serverPort + "|||" + gateWayClientIp);
		
	    log.info("-------------------START---------------------");
	    NetServer server = vertx.createNetServer(
    	      new NetServerOptions().setPort(serverPort).setHost(serverIp)
	    );
	    server.connectHandler(sock -> {
	    	log.info("-------------------SERVER CONNECTED---------------------");
	    	
	    	Buffer requestBufferData = Buffer.buffer();
	    	Buffer resultBufferData = Buffer.buffer();
		    sock.handler(requestBuffers -> {
		    	if (!sock.writeQueueFull()) {
		    		requestBufferData.appendBuffer(requestBuffers);
		    		int requestBodyLen = Integer.parseInt(new String(requestBufferData.getBytes(), 34, 4));
		    		if (requestBodyLen + 50 == requestBufferData.length()) {
		    			vertx.createNetClient().connect(gateWayClientPort, gateWayClientIp, res -> {
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
