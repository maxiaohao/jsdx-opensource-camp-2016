#!/bin/bash

# this file should only be called in 'tomcat-dockerfile', to setup fastdfs and its nginx module in docker image

mkdir -p $INST_TMP

TOMCATS=/opt/tomcats
mkdir -p $TOMCATS

# copy tomcats
tar xzf $INST_STUFF/apache-tomcat-7.0.70.tar.gz -C $INST_TMP

cp -r $INST_TMP/apache-tomcat-7.0.70 $TOMCATS/tomcat-portal
cp -r $INST_TMP/apache-tomcat-7.0.70 $TOMCATS/tomcat-manager
cp -r $INST_TMP/apache-tomcat-7.0.70 $TOMCATS/tomcat-rest
cp -r $INST_TMP/apache-tomcat-7.0.70 $TOMCATS/tomcat-search
cp -r $INST_TMP/apache-tomcat-7.0.70 $TOMCATS/tomcat-sso
cp -r $INST_TMP/apache-tomcat-7.0.70 $TOMCATS/tomcat-order

sed -i 's/<Connector port="8080" protocol="HTTP\/1\.1"/<Connector port="8081" protocol="org\.apache\.coyote\.http11\.Http11NioProtocol"/g' $TOMCATS/tomcat-portal/conf/server.xml
sed -i 's/<Connector port="8080" protocol="HTTP\/1\.1"/<Connector port="8082" protocol="org\.apache\.coyote\.http11\.Http11NioProtocol"/g' $TOMCATS/tomcat-manager/conf/server.xml
sed -i 's/<Connector port="8080" protocol="HTTP\/1\.1"/<Connector port="8083" protocol="org\.apache\.coyote\.http11\.Http11NioProtocol"/g' $TOMCATS/tomcat-rest/conf/server.xml
sed -i 's/<Connector port="8080" protocol="HTTP\/1\.1"/<Connector port="8084" protocol="org\.apache\.coyote\.http11\.Http11NioProtocol"/g' $TOMCATS/tomcat-search/conf/server.xml
sed -i 's/<Connector port="8080" protocol="HTTP\/1\.1"/<Connector port="8085" protocol="org\.apache\.coyote\.http11\.Http11NioProtocol"/g' $TOMCATS/tomcat-sso/conf/server.xml
sed -i 's/<Connector port="8080" protocol="HTTP\/1\.1"/<Connector port="8086" protocol="org\.apache\.coyote\.http11\.Http11NioProtocol"/g' $TOMCATS/tomcat-order/conf/server.xml

sed -i 's/8005/8015/g' $TOMCATS/tomcat-portal/conf/server.xml
sed -i 's/8005/8025/g' $TOMCATS/tomcat-manager/conf/server.xml
sed -i 's/8005/8035/g' $TOMCATS/tomcat-rest/conf/server.xml
sed -i 's/8005/8045/g' $TOMCATS/tomcat-search/conf/server.xml
sed -i 's/8005/8055/g' $TOMCATS/tomcat-sso/conf/server.xml
sed -i 's/8005/8065/g' $TOMCATS/tomcat-order/conf/server.xml

sed -i 's/8009/8019/g' $TOMCATS/tomcat-portal/conf/server.xml
sed -i 's/8009/8029/g' $TOMCATS/tomcat-manager/conf/server.xml
sed -i 's/8009/8039/g' $TOMCATS/tomcat-rest/conf/server.xml
sed -i 's/8009/8049/g' $TOMCATS/tomcat-search/conf/server.xml
sed -i 's/8009/8059/g' $TOMCATS/tomcat-sso/conf/server.xml
sed -i 's/8009/8069/g' $TOMCATS/tomcat-order/conf/server.xml

TOMCAT_USER_CONTENT='<tomcat-users><user name="tomcat" password="tomcat" roles="manager-gui,manager-script" /></tomcat-users>'

echo $TOMCAT_USER_CONTENT > $TOMCATS/tomcat-portal/conf/tomcat-users.xml
echo $TOMCAT_USER_CONTENT > $TOMCATS/tomcat-manager/conf/tomcat-users.xml
echo $TOMCAT_USER_CONTENT > $TOMCATS/tomcat-rest/conf/tomcat-users.xml
echo $TOMCAT_USER_CONTENT > $TOMCATS/tomcat-search/conf/tomcat-users.xml
echo $TOMCAT_USER_CONTENT > $TOMCATS/tomcat-sso/conf/tomcat-users.xml
echo $TOMCAT_USER_CONTENT > $TOMCATS/tomcat-order/conf/tomcat-users.xml

exit 0

