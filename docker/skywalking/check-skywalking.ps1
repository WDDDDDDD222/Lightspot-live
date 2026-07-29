$ErrorActionPreference = "Stop"

docker ps --filter "name=lightspot-skywalking" --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"

try {
    $body = @{ query = "query { version }" } | ConvertTo-Json -Compress
    $response = Invoke-RestMethod -UseBasicParsing "http://127.0.0.1:12800/graphql" -Method POST -ContentType "application/json" -Body $body
    Write-Host ""
    Write-Host "OAP GraphQL response:"
    Write-Host ($response | ConvertTo-Json -Compress)
}
catch {
    Write-Host ""
    Write-Host "OAP GraphQL check failed:"
    Write-Host $_.Exception.Message
}
