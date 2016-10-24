## images (6) ##
ebd-img-fastdfs
ebd-img-mysql
ebd-img-nginx
ebd-img-redis
ebd-img-solr
ebd-img-tomcat

## networks (1) ##
ebd-network

## containers/hosts (13) ##
fastdfs-storage1
fastdfs-storage2
fastdfs-tracker
mysql
nginx
redis-master
redis-slave1
redis-slave2
solr-master
solr-slave1
solr-slave2
tomcat1
tomcat2

## add the following into the hosts file on your host(laptop) so that you could access the containers (vms) with hostname ##
192.168.25.133 fastdfs-tracker
192.168.25.134 fastdfs-storage1
192.168.25.135 fastdfs-storage1
192.168.25.11 mysql
192.168.25.2 nginx
192.168.25.151 redis-master
192.168.25.152 redis-slave1
192.168.25.153 redis-slave2
192.168.25.161 solr-master
192.168.25.162 solr-slave1
192.168.25.163 solr-slave2
192.168.25.21 tomcat1
192.168.25.22 tomcat2
