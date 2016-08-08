package io.vertx.main.config;


import io.vertx.core.json.JsonObject;
import io.vertx.main.util.NxUtils;

public class NxmileGateWayConfig {

	private String targetServerIp;
	private int targetServerPort;
	private String gateWayServerIp;
	private int gateWayServerPort;
	
	
	public NxmileGateWayConfig(JsonObject config) {
		this.targetServerIp = NxUtils.defaultString(config.getString("targetServerIp"), "127.0.0.1");
		this.targetServerPort = Integer.valueOf(NxUtils.defaultString(config.getString("targetServerPort"), "19008"));
		this.gateWayServerIp = NxUtils.defaultString(config.getString("gateWayServerIp"), "127.0.0.1");
		this.gateWayServerPort = Integer.valueOf(NxUtils.defaultString(config.getString("gateWayServerPort"),"19009"));
	}


	public String getTargetServerIp() {
		return targetServerIp;
	}

	public int getTargetServerPort() {
		return targetServerPort;
	}

	public String getGateWayServerIp() {
		return gateWayServerIp;
	}

	public int getGateWayServerPort() {
		return gateWayServerPort;
	}




	

}
