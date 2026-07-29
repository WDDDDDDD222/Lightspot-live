param(
    [string]$RootPassword = "123456"
)

$ErrorActionPreference = "Stop"

docker exec -e "MYSQL_PWD=$RootPassword" lightspot-mysql-slave0 mysql -uroot -e "SHOW SLAVE STATUS\G"
docker exec -e "MYSQL_PWD=$RootPassword" lightspot-mysql-master mysql -uroot -e "SHOW DATABASES LIKE 'Lightspot_live_user';"
docker exec -e "MYSQL_PWD=$RootPassword" lightspot-mysql-slave0 mysql -uroot -e "SHOW DATABASES LIKE 'Lightspot_live_user';"
docker exec -e "MYSQL_PWD=$RootPassword" lightspot-mysql-slave0 mysql -uroot -DLightspot_live_user -e "SELECT COUNT(*) AS table_count FROM information_schema.tables WHERE table_schema='Lightspot_live_user' AND table_name LIKE 't_user_%';"
