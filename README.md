# NxmileGateWay

로컬과 개발서버(NxmileGateWay 실행) 방화벽만 오픈 하면 로컬에서 Nxmile Socket연동을 할 수 있다. 

```로컬 - 개발(SocketGateway) - Nxmile``` 중간에서 게이트웨이 역활을 해준다. 


## environment
* [vert.x] (http://d2.naver.com/helloworld/163784)

* jdk 1.8

* eclipse

* gradle


## Project Setting 
----
eclipse 명령어를 하기 전에 프로젝트를 gradle 프로젝트로 만들어야 한다.

 > [Gradle Task Command] ```eclipse```

## Gradle install Task 
----
shadowjar 라이브러를 통하여 jar로 만든다.

 > [Gradle Task Command] ```clean shadowJar```


## Server configuration & Server Start
-----
### 서버 IP & 포트 변경 및 실행 하기.


#### main/config/NxmileGateWayConfig.java 수정

```java
	public NxmileGateWayConfig(JsonObject config) {
		this.targetServerIp = NxUtils.defaultString(config.getString("targetServerIp"), "127.0.0.1");
		this.targetServerPort = Integer.valueOf(NxUtils.defaultString(config.getString("targetServerPort"), "19008"));
		this.gateWayServerIp = NxUtils.defaultString(config.getString("gateWayServerIp"), "127.0.0.1");
		this.gateWayServerPort = Integer.valueOf(NxUtils.defaultString(config.getString("gateWayServerPort"),"19009"));
	}
```
> [Java Command]  ```java -jar build/libs/NxmileGateWay-3.3.2-fat.jar```

### or 


#### src/main/config.json 수정
```
{
  "targetServerIp": "127.0.0.1",
  "targetServerPort": "19006",
  "gateWayServerIp": "127.0.0.1",
  "gateWayServerPort": "19008"
}
```

>  [Java Command] ``` java -jar build/libs/NxmileGateWay-3.3.2-fat.jar -conf src/main/config.json ```














