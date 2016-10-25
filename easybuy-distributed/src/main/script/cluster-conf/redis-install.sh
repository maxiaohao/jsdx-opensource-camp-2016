#!/bin/bash

# this file should only be called in 'redis-dockerfile', to setup basic redis.master.conf and redis.slave.conf

cp /etc/redis.conf /etc/redis.master.conf
cp /etc/redis.conf /etc/redis.slave.conf
rm -f /etc/redis.conf

sed -i 's/bind 127\.0\.0\.1/#bind 127\.0\.0\.1/g' /etc/redis.master.conf
sed -i 's/bind 127\.0\.0\.1/#bind 127\.0\.0\.1/g' /etc/redis.slave.conf
sed -i 's/# slaveof <masterip> <masterport>/slaveof redis-master 6379/g' /etc/redis.slave.conf

