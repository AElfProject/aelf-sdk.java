# Basic
This dic used to contain protobuf-generated java files, which are removed by now.  
If you are facing build issues, run [../build.sh](../build.sh) in your terminal.

# Warning
Since we are using protobuf-java dependency version 3.16.3 (see [pom.xml](../../../../../../../pom.xml)), 
you should use `protoc` console line tool under version 3.16.3.  
If you believe that you have installed a wrong version of `protoc`, run the following script in your terminal:
```shell
proto_version="3.11.4"
brew uninstall protobuf
if [ ! -f "./protobuf-all-${proto_version}.tar.gz" ]; then
wget https://github.com/protocolbuffers/protobuf/releases/download/v${proto_version}/protobuf-all-${proto_version}.tar.gz
fi
if [ ! -d "./protobuf-${proto_version}" ]; then
tar zxvf protobuf-all-${proto_version}.tar.gz
fi
cd protobuf-${proto_version}
./autogen.sh
./configure
make
make check
sudo make install
protoc --version
cd ..
rm -rf ./protobuf-all-${proto_version}.tar.gz
rm -rf ./protobuf-${proto_version}
```
It might take a few minutes to execute the installation, remember that some commands require `sudo`.