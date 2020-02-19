#!/bin/bash
AElfClient_path=AElfClient/
AElfClientTestpath=AElfClientTest/
for i in $AElfClient_path $AElfClientTestpath;
do
    cd $i && mvn clean package && mvn compile && cd ..
done
cd $AElfClientTestpath && mvn test
