#!/bin/bash

if [ "$(/usr/bin/whoami)" != "root" ] ; then
    echo "Error: This script should be run as root! (current user = $(/usr/bin/whoami))"
    exit 1
fi

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

# also add the domains to /etc/hosts in every container
ADD_HOST_OPT=$ADD_HOST_OPT" --add-host easybuy.com:$IP_NGINX"
ADD_HOST_OPT=$ADD_HOST_OPT" --add-host www.easybuy.com:$IP_NGINX"
ADD_HOST_OPT=$ADD_HOST_OPT" --add-host rest.easybuy.com:$IP_NGINX"
ADD_HOST_OPT=$ADD_HOST_OPT" --add-host search.easybuy.com:$IP_NGINX"
ADD_HOST_OPT=$ADD_HOST_OPT" --add-host sso.easybuy.com:$IP_NGINX"
ADD_HOST_OPT=$ADD_HOST_OPT" --add-host order.easybuy.com:$IP_NGINX"
ADD_HOST_OPT=$ADD_HOST_OPT" --add-host manager.easybuy.com:$IP_NGINX"

docker run --net $NETWORK_NAME --ip $IP_FASTDFS_TRACKER --hostname $HOST_FASTDFS_TRACKER $ADD_HOST_OPT --name $HOST_FASTDFS_TRACKER -dit ebd-img-fastdfs /bin/bash
docker exec $HOST_FASTDFS_TRACKER service sshd start
docker exec $HOST_FASTDFS_TRACKER /usr/local/nginx/sbin/nginx
docker exec $HOST_FASTDFS_TRACKER /usr/bin/fdfs_trackerd /etc/fdfs/tracker.conf
echo "fastdfs tracker started"

docker run --net $NETWORK_NAME --ip $IP_FASTDFS_STORAGE1 --hostname $HOST_FASTDFS_STORAGE1 $ADD_HOST_OPT --name $HOST_FASTDFS_STORAGE1 -dit ebd-img-fastdfs /bin/bash
docker exec $HOST_FASTDFS_STORAGE1 service sshd start
docker exec $HOST_FASTDFS_STORAGE1 /usr/local/nginx/sbin/nginx
echo "fastdfs storage 1 is initializing fs structure for the first run, which may take a while..."
docker exec $HOST_FASTDFS_STORAGE1 /usr/bin/fdfs_storaged /etc/fdfs/storage.conf start
echo "fastdfs storage 1 started"

docker run --net $NETWORK_NAME --ip $IP_FASTDFS_STORAGE2 --hostname $HOST_FASTDFS_STORAGE2 $ADD_HOST_OPT --name $HOST_FASTDFS_STORAGE2 -dit ebd-img-fastdfs /bin/bash
docker exec $HOST_FASTDFS_STORAGE2 service sshd start
docker exec $HOST_FASTDFS_STORAGE2 /usr/local/nginx/sbin/nginx
echo "fastdfs storage 2 is initializing fs structure for the first run, which may take a while..."
docker exec $HOST_FASTDFS_STORAGE2 /usr/bin/fdfs_storaged /etc/fdfs/storage.conf start
echo "fastdfs storage 2 started"

docker run --net $NETWORK_NAME --ip $IP_MYSQL --hostname $HOST_MYSQL $ADD_HOST_OPT --name $HOST_MYSQL -dit ebd-img-mysql /bin/bash
docker exec $HOST_MYSQL service sshd start
docker exec $HOST_MYSQL service mysqld start
#docker exec $HOST_MYSQL # TODO command (to init db)
echo "mysql server started"

docker run --net $NETWORK_NAME --ip $IP_NGINX --hostname $HOST_NGINX $ADD_HOST_OPT --name $HOST_NGINX -dit ebd-img-nginx /bin/bash
docker exec $HOST_NGINX service sshd start
docker exec $HOST_NGINX service nginx start
echo "nginx server started"

docker run --net $NETWORK_NAME --ip $IP_REDIS_MASTER --hostname $HOST_REDIS_MASTER $ADD_HOST_OPT --name $HOST_REDIS_MASTER -dit ebd-img-redis /bin/bash
docker exec $HOST_REDIS_MASTER service sshd start
docker exec $HOST_REDIS_MASTER /usr/sbin/redis-server /etc/redis.master.conf
echo "redis master started"

docker run --net $NETWORK_NAME --ip $IP_REDIS_SLAVE1 --hostname $HOST_REDIS_SLAVE1 $ADD_HOST_OPT --name $HOST_REDIS_SLAVE1 -dit ebd-img-redis /bin/bash
docker exec $HOST_REDIS_SLAVE1 service sshd start
docker exec $HOST_REDIS_SLAVE1 /usr/sbin/redis-server /etc/redis.slave.conf
echo "redis slave 1 started"

docker run --net $NETWORK_NAME --ip $IP_REDIS_SLAVE2 --hostname $HOST_REDIS_SLAVE2 $ADD_HOST_OPT --name $HOST_REDIS_SLAVE2 -dit ebd-img-redis /bin/bash
docker exec $HOST_REDIS_SLAVE2 service sshd start
docker exec $HOST_REDIS_SLAVE2 /usr/sbin/redis-server /etc/redis.slave.conf
echo "redis slave 2 started"

docker run --net $NETWORK_NAME --ip $IP_SOLR_MASTER --hostname $HOST_SOLR_MASTER $ADD_HOST_OPT --name $HOST_SOLR_MASTER -dit ebd-img-solr /bin/bash
docker exec $HOST_SOLR_MASTER service sshd start
#docker exec $HOST_SOLR_MASTER # TODO command
echo "solr master started"

docker run --net $NETWORK_NAME --ip $IP_SOLR_SLAVE1 --hostname $HOST_SOLR_SLAVE1 $ADD_HOST_OPT --name $HOST_SOLR_SLAVE1 -dit ebd-img-solr /bin/bash
docker exec $HOST_SOLR_SLAVE1 service sshd start
#docker exec $HOST_SOLR_SLAVE1 # TODO command
echo "solr slave 1 started"

docker run --net $NETWORK_NAME --ip $IP_SOLR_SLAVE2 --hostname $HOST_SOLR_SLAVE2 $ADD_HOST_OPT --name $HOST_SOLR_SLAVE2 -dit ebd-img-solr /bin/bash
docker exec $HOST_SOLR_SLAVE2 service sshd start
#docker exec $HOST_SOLR_SLAVE2 # TODO command
echo "solr slave 2 started"

docker run --net $NETWORK_NAME --ip $IP_TOMCAT1 --hostname $HOST_TOMCAT1 $ADD_HOST_OPT --name $HOST_TOMCAT1 -dit ebd-img-tomcat /bin/bash
docker exec $HOST_TOMCAT1 service sshd start
docker exec $HOST_TOMCAT1 /opt/tomcats/tomcat-portal/bin/startup.sh
docker exec $HOST_TOMCAT1 /opt/tomcats/tomcat-manager/bin/startup.sh
docker exec $HOST_TOMCAT1 /opt/tomcats/tomcat-rest/bin/startup.sh
docker exec $HOST_TOMCAT1 /opt/tomcats/tomcat-search/bin/startup.sh
docker exec $HOST_TOMCAT1 /opt/tomcats/tomcat-sso/bin/startup.sh
docker exec $HOST_TOMCAT1 /opt/tomcats/tomcat-order/bin/startup.sh
echo "tomcat 1 started"

docker run --net $NETWORK_NAME --ip $IP_TOMCAT2 --hostname $HOST_TOMCAT2 $ADD_HOST_OPT --name $HOST_TOMCAT2 -dit ebd-img-tomcat /bin/bash
docker exec $HOST_TOMCAT2 service sshd start
docker exec $HOST_TOMCAT2 /opt/tomcats/tomcat-portal/bin/startup.sh
docker exec $HOST_TOMCAT2 /opt/tomcats/tomcat-manager/bin/startup.sh
docker exec $HOST_TOMCAT2 /opt/tomcats/tomcat-rest/bin/startup.sh
docker exec $HOST_TOMCAT2 /opt/tomcats/tomcat-search/bin/startup.sh
docker exec $HOST_TOMCAT2 /opt/tomcats/tomcat-sso/bin/startup.sh
docker exec $HOST_TOMCAT2 /opt/tomcats/tomcat-order/bin/startup.sh
echo "tomcat 2 started"

echo "###########################################################################################"
echo "####  all servers in cluster have been brought up, please check out the details above  ####"
echo "###########################################################################################"

