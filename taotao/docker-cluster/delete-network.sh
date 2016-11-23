#!/bin/bash

if [ "$(/usr/bin/whoami)" != "root" ] ; then
    echo "Error: This script should be run as root! (current user = $(/usr/bin/whoami))"
    exit 1
fi

docker network rm ebd-network
