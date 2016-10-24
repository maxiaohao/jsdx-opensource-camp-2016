#!/bin/bash

PROJ_ROOT=$(cd `dirname $0`/../../../;pwd)

NETWORK_NAME="ebd-network"

HOST_LAPTOP="laptop"
HOST_FASTDFS_TRACKER="fastdfs-tracker"
HOST_FASTDFS_STORAGE1="fastdfs-storage1"
HOST_FASTDFS_STORAGE2="fastdfs-storage2"
HOST_MYSQL="mysql"
HOST_NGINX="nginx"
HOST_REDIS_MASTER="redis-master"
HOST_REDIS_SLAVE1="redis-slave1"
HOST_REDIS_SLAVE2="redis-slave2"
HOST_SOLR_MASTER="solr-master"
HOST_SOLR_SLAVE1="solr-slave1"
HOST_SOLR_SLAVE2="solr-slave2"
HOST_TOMCAT1="tomcat1"
HOST_TOMCAT2="tomcat2"

IP_LAPTOP="192.168.25.1"
IP_FASTDFS_TRACKER="192.168.25.133"
IP_FASTDFS_STORAGE1="192.168.25.134"
IP_FASTDFS_STORAGE2="192.168.25.135"
IP_MYSQL="192.168.25.11"
IP_NGINX="192.168.25.2"
IP_REDIS_MASTER="192.168.25.151"
IP_REDIS_SLAVE1="192.168.25.152"
IP_REDIS_SLAVE2="192.168.25.153"
IP_SOLR_MASTER="192.168.25.161"
IP_SOLR_SLAVE1="192.168.25.162"
IP_SOLR_SLAVE2="192.168.25.163"
IP_TOMCAT1="192.168.25.21"
IP_TOMCAT2="192.168.25.22"

ADD_HOST_OPT=" --add-host $HOST_LAPTOP:$IP_LAPTOP"
ADD_HOST_OPT=" --add-host $HOST_FASTDFS_TRACKER:$IP_FASTDFS_TRACKER $ADD_HOST_OPT"
ADD_HOST_OPT=" --add-host $HOST_FASTDFS_STORAGE1:$IP_FASTDFS_STORAGE1 $ADD_HOST_OPT"
ADD_HOST_OPT=" --add-host $HOST_FASTDFS_STORAGE2:$IP_FASTDFS_STORAGE2 $ADD_HOST_OPT"
ADD_HOST_OPT=" --add-host $HOST_MYSQL:$IP_MYSQL $ADD_HOST_OPT"
ADD_HOST_OPT=" --add-host $HOST_NGINX:$IP_NGINX $ADD_HOST_OPT"
ADD_HOST_OPT=" --add-host $HOST_REDIS_MASTER:$IP_REDIS_MASTER $ADD_HOST_OPT"
ADD_HOST_OPT=" --add-host $HOST_REDIS_SLAVE1:$IP_REDIS_SLAVE1 $ADD_HOST_OPT"
ADD_HOST_OPT=" --add-host $HOST_REDIS_SLAVE2:$IP_REDIS_SLAVE2 $ADD_HOST_OPT"
ADD_HOST_OPT=" --add-host $HOST_SOLR_MASTER:$IP_SOLR_MASTER $ADD_HOST_OPT"
ADD_HOST_OPT=" --add-host $HOST_SOLR_SLAVE1:$IP_SOLR_SLAVE1 $ADD_HOST_OPT"
ADD_HOST_OPT=" --add-host $HOST_SOLR_SLAVE2:$IP_SOLR_SLAVE2 $ADD_HOST_OPT"
ADD_HOST_OPT=" --add-host $HOST_TOMCAT1:$IP_TOMCAT1 $ADD_HOST_OPT"
ADD_HOST_OPT=" --add-host $HOST_TOMCAT2:$IP_TOMCAT2 $ADD_HOST_OPT"

docker run --net $NETWORK_NAME --ip $IP_FASTDFS_TRACKER --hostname $HOST_FASTDFS_TRACKER $ADD_HOST_OPT --name $HOST_FASTDFS_TRACKER -dit ebd-img-fastdfs /bin/bash
docker exec -it $HOST_FASTDFS_TRACKER /usr/bin/fdfs_trackerd /etc/fdfs/tracker.conf
docker exec -it $HOST_FASTDFS_TRACKER /usr/local/nginx/sbin/nginx

docker run --net $NETWORK_NAME --ip $IP_FASTDFS_STORAGE1 --hostname $HOST_FASTDFS_STORAGE1 $ADD_HOST_OPT --name $HOST_FASTDFS_STORAGE1 -dit ebd-img-fastdfs /bin/bash
echo "fastdfs storage 1 is initializing fs structure for the first run, which may take a few minutes..."
docker exec -it $HOST_FASTDFS_STORAGE1 /usr/bin/fdfs_storaged /etc/fdfs/storage.conf start
docker exec -it $HOST_FASTDFS_STORAGE1 /usr/local/nginx/sbin/nginx

docker run --net $NETWORK_NAME --ip $IP_FASTDFS_STORAGE2 --hostname $HOST_FASTDFS_STORAGE2 $ADD_HOST_OPT --name $HOST_FASTDFS_STORAGE2 -dit ebd-img-fastdfs /bin/bash
echo "fastdfs storage 2 is initializing fs structure for the first run, which may take a few minutes..."
docker exec -it $HOST_FASTDFS_STORAGE2 /usr/bin/fdfs_storaged /etc/fdfs/storage.conf start
docker exec -it $HOST_FASTDFS_STORAGE2 /usr/local/nginx/sbin/nginx

docker run --net $NETWORK_NAME --ip $IP_MYSQL --hostname $HOST_MYSQL $ADD_HOST_OPT --name $HOST_MYSQL -dit ebd-img-mysql /bin/bash
docker exec -it $HOST_MYSQL service mysqld start

docker run --net $NETWORK_NAME --ip $IP_NGINX --hostname $HOST_NGINX $ADD_HOST_OPT --name $HOST_NGINX -dit ebd-img-nginx /bin/bash
docker exec -it $HOST_NGINX service nginx start

#ebd-img-redis
#ebd-img-solr
#ebd-img-tomcat
