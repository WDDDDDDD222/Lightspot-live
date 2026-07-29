$ErrorActionPreference = "Stop"

$projectRoot = Split-Path -Parent (Split-Path -Parent (Split-Path -Parent $PSCommandPath))
$composeFile = Join-Path $projectRoot "docker-compose.skywalking.yml"

docker compose -f $composeFile up -d
docker ps --filter "name=lightspot-skywalking" --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"

Write-Host ""
Write-Host "SkyWalking UI: http://localhost:18080"
Write-Host "OAP gRPC agent endpoint: 127.0.0.1:11800"
Write-Host "OAP HTTP endpoint: http://127.0.0.1:12800"
Write-Host "BanyanDB HTTP endpoint: http://127.0.0.1:17913"
