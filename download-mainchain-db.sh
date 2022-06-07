#!/bin/bash
yesterday=$(date +%Y%m%d --date '1 days ago')
number=$1
if [ "$1" = "" ];then
    wget https://aelf-backup.s3.ap-northeast-2.amazonaws.com/snapshot/mainnet/aelf-mainnet-mainchain-chaindb-$yesterday.tar.gz
    wget https://aelf-backup.s3.ap-northeast-2.amazonaws.com/snapshot/mainnet/aelf-mainnet-mainchain-statedb-$yesterday.tar.gz
else
   echo "no"
    wget https://aelf-backup.s3.ap-northeast-2.amazonaws.com/snapshot/mainnet/aelf-mainnet-mainchain-chaindb-$number.tar.gz
    wget https://aelf-backup.s3.ap-northeast-2.amazonaws.com/snapshot/mainnet/aelf-mainnet-mainchain-statedb-$number.tar.gz
fi