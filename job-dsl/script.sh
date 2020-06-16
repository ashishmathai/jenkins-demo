#!/bin/bash
sudo mkdir -p /opt/NodeProject/
cd /opt/NodeProject
git clone https://github.com/ashishmathai/nodejssample.git
cd /opt/NodeProject/nodejssample/
npm install
sudo chown -R ashish:ashish /opt/NodeProject
cd /opt/NodeProject/nodejssample
node index.js &
echo "---------------------"
echo 'Verify Node running index.js'
echo "---------------------"
ps aux | grep -i index.js"
echo "---------------------"
echo "Local Curl Test"
curl localhost:3000; curl -I localhost:3000"
echo "---------------------"
echo "Success"
