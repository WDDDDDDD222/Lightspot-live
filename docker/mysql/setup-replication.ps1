param(
    [string]$RootPassword = "123456",
    [string]$ReplicationUser = "Lightspot_slave",
    [string]$ReplicationPassword = "Lightspot_123456"
)

$ErrorActionPreference = "Stop"
if ($PSVersionTable.PSVersion.Major -ge 7) {
    $PSNativeCommandUseErrorActionPreference = $false
}
$projectRoot = Split-Path -Parent (Split-Path -Parent (Split-Path -Parent $PSCommandPath))
$composeFile = Join-Path $projectRoot "docker-compose.mysql.yml"
$schemaSql = Join-Path $projectRoot "sql\create_lightspot_live_user.sql"

function Invoke-MySqlInContainer {
    param(
        [string]$Container,
        [string]$Sql
    )
    $Sql | docker exec -i -e "MYSQL_PWD=$RootPassword" $Container mysql -uroot
}

function Wait-MySql {
    param([string]$Container)

    for ($i = 0; $i -lt 60; $i++) {
        docker exec -e "MYSQL_PWD=$RootPassword" $Container mysqladmin ping -uroot --silent *> $null
        if ($LASTEXITCODE -eq 0) {
            return
        }
        Start-Sleep -Seconds 2
    }

    throw "MySQL container $Container did not become ready in time."
}

docker compose -f $composeFile up -d

Wait-MySql "lightspot-mysql-master"
Wait-MySql "lightspot-mysql-slave0"

$replicationSql = @"
CREATE USER IF NOT EXISTS '$ReplicationUser'@'%' IDENTIFIED WITH mysql_native_password BY '$ReplicationPassword';
GRANT REPLICATION SLAVE ON *.* TO '$ReplicationUser'@'%';
FLUSH PRIVILEGES;
"@
Invoke-MySqlInContainer "lightspot-mysql-master" $replicationSql

$slaveSql = @"
STOP SLAVE;
RESET SLAVE ALL;
CHANGE MASTER TO
  MASTER_HOST='lightspot-mysql-master',
  MASTER_PORT=3306,
  MASTER_USER='$ReplicationUser',
  MASTER_PASSWORD='$ReplicationPassword',
  MASTER_AUTO_POSITION=1,
  GET_MASTER_PUBLIC_KEY=1;
START SLAVE;
"@
Invoke-MySqlInContainer "lightspot-mysql-slave0" $slaveSql

Get-Content -Raw $schemaSql | docker exec -i -e "MYSQL_PWD=$RootPassword" lightspot-mysql-master mysql -uroot

docker exec -e "MYSQL_PWD=$RootPassword" lightspot-mysql-slave0 mysql -uroot -e "SHOW SLAVE STATUS\G"
