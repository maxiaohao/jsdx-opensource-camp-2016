# prerequisites #
- Docker is needed and latest version is encouraged (while 1.10.1+ should work) since there are some new network/host related arguments used in the scripts. However, I am not sure of the minium version.

# quick start #
These scripts help to create a dozen of containers (as vms) along with your host in a private network utilizing the development of easybuy-distributed.
```
./create-network.sh
./create-img-common.sh      # this will take 5 or 10 mins
./create-imgs-cluster.sh    # build the 6 images, taking no more than 5 mins
./run-containers.sh
```

TIPS: 
- Run `docker exec -it <container_id|container_name> /bin/bash` to attach your console into the container.
- `docker images` lists all images.
- `docker ps -a` lists all containers.
- sshd are also enabled in the containers so that you could ssh to them (`root/password`) in case you access from some other hosts in the private network.
- There are some `delete-*.sh` and `kill-*.sh` you can make use of if you need to clear up things quickly.
- It is recommended to pull the official centos 6 image to local (`docker pull centos:6`) because all our images are based on it and that will accelerate repeated common image rebuilding opeartion.

# specs #

### network (1) ###
- ebd-network

### images built (7) ###
- ebd-img-common (only works as a dependency for the rest 6 images)
- ebd-img-fastdfs
- ebd-img-mysql
- ebd-img-nginx
- ebd-img-redis
- ebd-img-solr
- ebd-img-tomcat

### containers(vms) (13) ###
- fastdfs-tracker
- fastdfs-storage1
- fastdfs-storage2
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

### hosts ###
- You may need to append the following into the hosts (e.g. /etc/hosts) file on your host(laptop) so that you could access the containers (vms) with hostname:
```
# ebd hosts
192.168.25.1 laptop #(an alias of your host)
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

# ebd domains
192.168.25.2 easybuy.com
192.168.25.2 www.easybuy.com
192.168.25.2 rest.easybuy.com
192.168.25.2 search.easybuy.com
192.168.25.2 sso.easybuy.com
192.168.25.2 order.easybuy.com
192.168.25.2 manager.easybuy.com
```

### TODO ###
- configure redis auto syncing to mysql
- add solr (for now its image/containers are empty)

### Licence ###
MIT

