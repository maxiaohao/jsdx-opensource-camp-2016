#!/bin/bash

# this file should only be called in 'fastdfs-dockerfile', to setup fastdfs and its nginx module in docker image

mkdir -p $INST_TMP

# installl libfastcommon
tar xzf $INST_STUFF/libfastcommon-1.0.7.tar.gz -C $INST_TMP
cd $INST_TMP/libfastcommon-1.0.7
./make.sh && ./make.sh install

# install fastdfs
tar xzf $INST_STUFF/fastdfs-5.05.tar.gz -C $INST_TMP
cd $INST_TMP/fastdfs-5.05
./make.sh && ./make.sh install
cp $INST_TMP/fastdfs-5.05/conf/* /etc/fdfs/

# configure fastdfs (tracker & storage nodes use the same conf)
mkdir -p /opt/fastdfs/storage
sed -i 's/base_path=\/home\/yuqing\/fastdfs/base_path=\/opt\/fastdfs/g' /etc/fdfs/tracker.conf
sed -i 's/base_path=\/home\/yuqing\/fastdfs/base_path=\/opt\/fastdfs/g' /etc/fdfs/storage.conf
sed -i 's/store_path0=\/home\/yuqing\/fastdfs/store_path0=\/opt\/fastdfs\/storage/g' /etc/fdfs/storage.conf
sed -i 's/tracker_server=192\.168\.209\.121:22122/tracker_server=fastdfs-tracker:22122/g' /etc/fdfs/storage.conf
sed -i 's/base_path=\/home\/yuqing\/fastdfs/base_path=\/opt\/fastdfs/g' /etc/fdfs/client.conf
sed -i 's/tracker_server=192\.168\.0\.197:22122/tracker_server=fastdfs-tracker:22122/g' /etc/fdfs/client.conf

# extract & configure fastdfs-nginx-module
tar xzf $INST_STUFF/fastdfs-nginx-module-20161024.tar.gz -C $INST_TMP
sed -i 's/local\/include/include/g' $INST_TMP/fastdfs-nginx-module/src/config

# compile and install nginx (with fastdfs-nginx-module)
mkdir -p /var/temp/nginx/
tar xzf $INST_STUFF/nginx-1.10.2.tar.gz -C $INST_TMP
cd $INST_TMP/nginx-1.10.2
./configure \
--prefix=/usr/local/nginx \
--pid-path=/var/run/nginx/nginx.pid \
--lock-path=/var/lock/nginx.lock \
--error-log-path=/var/log/nginx/error.log \
--http-log-path=/var/log/nginx/access.log \
--with-http_gzip_static_module \
--http-client-body-temp-path=/var/temp/nginx/client \
--http-proxy-temp-path=/var/temp/nginx/proxy \
--http-fastcgi-temp-path=/var/temp/nginx/fastcgi \
--http-uwsgi-temp-path=/var/temp/nginx/uwsgi \
--http-scgi-temp-path=/var/temp/nginx/scgi \
--add-module=$INST_TMP/fastdfs-nginx-module/src

make && make install

# configure fastdfs-nginx-module 
cp $INST_TMP/fastdfs-nginx-module/src/mod_fastdfs.conf /etc/fdfs/
mkdir -p /opt/fastdfs/nginx-log
mkdir -p /opt/fastdfs/storage
sed -i 's/base_path=\/tmp/base_path=\/opt\/fastdfs\/nginx-log/g' /etc/fdfs/mod_fastdfs.conf
sed -i 's/tracker_server=tracker:22122/tracker_server=fastdfs-tracker:22122/g' /etc/fdfs/mod_fastdfs.conf
sed -i 's/url_have_group_name = false/url_have_group_name = true/g' /etc/fdfs/mod_fastdfs.conf
sed -i 's/store_path0=\/home\/yuqing\/fastdfs/store_path0=\/opt\/fastdfs\/storage/g' /etc/fdfs/mod_fastdfs.conf

exit 0

