package io.vertx.main.config;


import io.vertx.core.json.JsonObject;
import io.vertx.main.util.NxUtils;

public class NxmileGateWayConfig {

	private String serverIp;
	private int serverPort;
	private String gateWayClientIp;
	private int gateWayClientPort;
	
	
	public NxmileGateWayConfig(JsonObject config) {
		this.serverIp = NxUtils.defaultString(config.getString("serverIp"), "127.0.0.1");
		this.serverPort = Integer.valueOf(NxUtils.defaultString(config.getString("serverPort"), "19008"));
		this.gateWayClientIp = NxUtils.defaultString(config.getString("gateWayClientIp"), "127.0.0.1");
		this.gateWayClientPort = Integer.valueOf(NxUtils.defaultString(config.getString("gateWayClientPort"),"19009"));
	}


	public String getServerIp() {
		return serverIp;
	}

	public int getServerPort() {
		return serverPort;
	}

	public String getGateWayClientIp() {
		return gateWayClientIp;
	}

	public int getGateWayClientPort() {
		return gateWayClientPort;
	}

}
