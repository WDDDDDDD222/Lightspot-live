param(
    [string]$AgentImage = "apache/skywalking-java-agent:9.5.0-java17"
)

$ErrorActionPreference = "Stop"

$projectRoot = Split-Path -Parent (Split-Path -Parent (Split-Path -Parent $PSCommandPath))
$agentDir = Join-Path $projectRoot "tools\skywalking\agent"

New-Item -ItemType Directory -Force -Path $agentDir | Out-Null

docker pull $AgentImage

$containerId = docker create $AgentImage
try {
    docker cp "${containerId}:/skywalking/agent/." $agentDir
}
finally {
    docker rm $containerId | Out-Null
}

Write-Host "SkyWalking Java agent installed to: $agentDir"
Write-Host "Agent jar: $agentDir\skywalking-agent.jar"
