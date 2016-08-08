# SocketGateway

SocketGateway를 개발 서버에 설치 하고, 로컬과 SocketGateway를 설치한 개발서버 방화벽만 오픈 하면 로컬에서 Nxmile Socket연동을 할 수 있다. 

```로컬 - 개발(SocketGateway) - Nxmile``` 중간에서 게이트웨이 역활을 해준다. 


### Project Setting 
> [Gradle Task Command] ```eclipse```

#### Gradle install Task 
> [Gradle Task Command] ```clean shadowJar```


#### Server Start
> ```java -jar build/libs/NxmileGateWay-3.3.2-fat.jar```


