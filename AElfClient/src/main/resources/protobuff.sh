#!/bin/bash
# Deprecated - use build.sh file under package **/protobuf/
SOURCE_FOLDER=../java/io/aelf/protobuf/proto
IMP_FOLDER=../java/io/aelf/protobuf/proto
JAVA_COMPILER_PATH=protoc
JAVA_TARGET_PATH=../java
for i in "${SOURCE_FOLDER}"/*.proto
do
echo ${JAVA_COMPILER_PATH} --proto_path=${SOURCE_FOLDER} --java_out=${JAVA_TARGET_PATH} ${i}
${JAVA_COMPILER_PATH} --proto_path=${IMP_FOLDER} --java_out=${JAVA_TARGET_PATH} ${i}
done