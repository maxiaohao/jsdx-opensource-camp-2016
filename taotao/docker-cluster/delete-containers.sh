#!/bin/bash

if [ "$(/usr/bin/whoami)" != "root" ] ; then
    echo "Error: This script should be run as root! (current user = $(/usr/bin/whoami))"
    exit 1
fi

docker ps -a | grep "ebd-img-" | grep -v grep | awk '{printf "%s ",$1}' | xargs docker rm
