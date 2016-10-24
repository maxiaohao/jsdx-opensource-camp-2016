#!/bin/bash

PROJ_ROOT=$(cd `dirname $0`/../../../;pwd)
echo $PROJ_ROOT

docker build -t ebd-img-fastdfs -f $PROJ_ROOT/src/main/script/cluster-conf/fastdfs-dockerfile $PROJ_ROOT
docker build -t ebd-img-mysql -f $PROJ_ROOT/src/main/script/cluster-conf/mysql-dockerfile $PROJ_ROOT
docker build -t ebd-img-nginx -f $PROJ_ROOT/src/main/script/cluster-conf/nginx-dockerfile $PROJ_ROOT

#ebd-img-redis
#ebd-img-solr
#ebd-img-tomcat
