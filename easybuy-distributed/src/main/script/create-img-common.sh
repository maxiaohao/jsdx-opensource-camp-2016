#!/bin/bash

if [ "$(/usr/bin/whoami)" != "root" ] ; then
    echo "Error: This script should be run as root! (current user = $(/usr/bin/whoami))"
    exit 1
fi

# if this doesn't work, you may simply assign the absolute path of project root, e.g. PROJ_ROOT=/home/someone/easybuy-distrituted
PROJ_ROOT=$(cd `dirname $0`/../../../;pwd)

# build common ebd image
docker build -t ebd-img-common -f $PROJ_ROOT/src/main/script/cluster-conf/ebd-common-dockerfile $PROJ_ROOT

exit 0
