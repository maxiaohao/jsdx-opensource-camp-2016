#!/bin/bash

if [ "$(/usr/bin/whoami)" != "root" ] ; then
    echo "Error: This script should be run as root! (current user = $(/usr/bin/whoami))"
    exit 1
fi

# if this doesn't work, you may simply assign the absolute path of project root, e.g. PROJ_ROOT=/home/someone/easybuy-distrituted
PROJ_ROOT=$(cd `dirname $0`/../../../;pwd)

# build all the component images that are based on ebd-img-common
docker build -t ebd-img-fastdfs -f $PROJ_ROOT/src/main/script/cluster-conf/fastdfs-dockerfile $PROJ_ROOT
docker build -t ebd-img-mysql -f $PROJ_ROOT/src/main/script/cluster-conf/mysql-dockerfile $PROJ_ROOT
docker build -t ebd-img-nginx -f $PROJ_ROOT/src/main/script/cluster-conf/nginx-dockerfile $PROJ_ROOT
docker build -t ebd-img-redis -f $PROJ_ROOT/src/main/script/cluster-conf/redis-dockerfile $PROJ_ROOT
docker build -t ebd-img-solr -f $PROJ_ROOT/src/main/script/cluster-conf/solr-dockerfile $PROJ_ROOT
docker build -t ebd-img-tomcat -f $PROJ_ROOT/src/main/script/cluster-conf/tomcat-dockerfile $PROJ_ROOT

exit 0
