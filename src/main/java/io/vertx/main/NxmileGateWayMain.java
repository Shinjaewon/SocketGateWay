package io.vertx.main;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.net.NetSocket;
import io.vertx.main.config.NxmileGateWayConfig;

public class NxmileGateWayMain extends AbstractVerticle  {

	private static final Logger log = LoggerFactory.getLogger(NxmileGateWayMain.class);
	
	
	@Override
	public void start() throws Exception { 

		NxmileGateWayConfig config = new NxmileGateWayConfig(context.config());

		log.info(config.getTargetServerIp() + ":" + config.getTargetServerPort());
		
	    log.info("-------------------SERVER START---------------------");
	    NetServer gateWayServer = vertx.createNetServer(
    	      new NetServerOptions().setPort(config.getGateWayServerPort()).setHost(config.getGateWayServerIp())
	    );
	    gateWayServer.connectHandler(requestClient -> {
	    	log.info("-------------------SERVER CONNECTED---------------------");
	    	
	    	Buffer requestBufferData = Buffer.buffer();
	    	Buffer resultBufferData = Buffer.buffer();
		    requestClient.handler(serverRequestBuffers -> {
		    	if (!requestClient.writeQueueFull()) {
		    		requestBufferData.appendBuffer(serverRequestBuffers);
		    		int requestBodyLen = Integer.parseInt(new String(requestBufferData.getBytes(), 34, 4));
		    		if (requestBodyLen + 50 == requestBufferData.length()) {
		    			vertx.createNetClient().connect(config.getTargetServerPort(), config.getTargetServerIp(), targetClient -> {
		    				if (targetClient.succeeded()) {
		    					log.info("targetClient to connected");
		    					NetSocket targetClientSocket = targetClient.result();
		    					targetClientSocket.handler(tagetClientResultBuffers -> {
		    						resultBufferData.appendBuffer(tagetClientResultBuffers);
		    						int resultBufferLen = Integer.parseInt(new String(resultBufferData.getBytes(), 34, 4));
		    						if (resultBufferLen + 50 == resultBufferData.length()){
		    							log.info("resultBufferData ::" + resultBufferData.getString(0, resultBufferData.length()));
		    							requestClient.write(resultBufferData);
		    						}
		    					});
		    					log.info("requestBufferData ::" + requestBufferData.getString(0, requestBufferData.length()));
		    					targetClientSocket.write(requestBufferData);
		    				}else{
		    					log.info("targetClient Failed to  connect");
		    				}
		    				
		    			});
		    		}
		    	} else {
		    		log.info("GateWayServer Failed to Send");
		    	}
		    });
	    }).listen();
	}
	
	
	public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new NxmileGateWayMain());
    }
}
