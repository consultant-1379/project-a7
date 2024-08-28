#!/bin/sh
### Database Dumping Script ###

datenow=$(date +"%Y-%m-%dT%H:%M:%sZ")
docker exec -i docker-db mysqldump -uroot -p1234 --databases projectDB --skip-comments >/tmp/projectDB_dump-"$datenow".sql
