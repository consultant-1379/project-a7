#!/bin/sh

/usr/bin/mysqld_safe --skip-grant-tables &
sleep 5
mysql -u root -e "CREATE DATABASE projectDB"
mysql -u root projectDB </res/dump.sql
