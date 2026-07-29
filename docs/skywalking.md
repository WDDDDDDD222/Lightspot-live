# Lightspot SkyWalking Local Setup

## Start SkyWalking

```powershell
powershell -ExecutionPolicy Bypass -File .\docker\skywalking\start-skywalking.ps1
```

UI:

```text
http://localhost:18080
```

Endpoints:

```text
gRPC: 127.0.0.1:11800
OAP HTTP: http://127.0.0.1:12800
BanyanDB HTTP: http://127.0.0.1:17913
```

## Install Java Agent

```powershell
powershell -ExecutionPolicy Bypass -File .\docker\skywalking\install-agent.ps1
```

The agent is installed under:

```text
tools\skywalking\agent\skywalking-agent.jar
```

## IDEA VM Options

Project run configurations have been added under `.run/`.

Add these VM options to `ApiWebApplication`:

```text
-javaagent:C:\Users\wd\Desktop\Lightspot-live\tools\skywalking\agent\skywalking-agent.jar
-Dskywalking.agent.service_name=Lightspot-live-api
-Dskywalking.collector.backend_service=127.0.0.1:11800
-Dskywalking.logging.level=WARN
-Dskywalking.logging.output=FILE
```

Add these VM options to `UserProviderApplication`:

```text
-javaagent:C:\Users\wd\Desktop\Lightspot-live\tools\skywalking\agent\skywalking-agent.jar
-Dskywalking.agent.service_name=Lightspot-live-user-provider
-Dskywalking.collector.backend_service=127.0.0.1:11800
-Dskywalking.logging.level=WARN
-Dskywalking.logging.output=FILE
```

Restart both applications, then request:

```text
http://localhost:8080/user/getUserInfo?userId=1
```

The trace should show `Lightspot-live-api` calling `Lightspot-live-user-provider`.
