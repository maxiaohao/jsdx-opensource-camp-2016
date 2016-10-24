# quick start #
These scripts help to create a dozen of containers (as vms) along with your host in a private network utilizing the development of easybuy-distributed.
```
sudo ./create-network.sh
sudo ./create-images.sh
sudo ./run-containers.sh
```

## specs ##

### network (1) ###
- ebd-network

### images built (6) ###
- ebd-img-fastdfs
- ebd-img-mysql
- ebd-img-nginx
- ebd-img-redis
- ebd-img-solr
- ebd-img-tomcat

### containers/hosts (13) ###
- fastdfs-storage1
- fastdfs-storage2
- fastdfs-tracker
- mysql
- nginx
- redis-master
- redis-slave1
- redis-slave2
- solr-master
- solr-slave1
- solr-slave2
- tomcat1
- tomcat2

### hosts (you may add following to your /etc/hosts or %SYSTEM%/hosts ###
- add the following into the hosts file on your host(laptop) so that you could access the containers (vms) with hostname
```
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
```
