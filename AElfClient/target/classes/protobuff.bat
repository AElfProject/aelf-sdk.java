set SOURCE_FOLDER=..\java\io\aelf\protobuf\proto
set IMP_FOLDER=..\java\io\aelf\protobuf\proto
set JAVA_COMPILER_PATH=protoc
set JAVA_TARGET_PATH=..\java
for /f "delims=" %%i in ('dir /b "%SOURCE_FOLDER%\*.proto"') do (
echo %JAVA_COMPILER_PATH% --proto_path=%SOURCE_FOLDER% --java_out=%JAVA_TARGET_PATH% %SOURCE_FOLDER%\%%i
%JAVA_COMPILER_PATH% --proto_path=%IMP_FOLDER% --java_out=%JAVA_TARGET_PATH% %SOURCE_FOLDER%\%%i
)