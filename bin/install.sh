WORLD_PATH=/opt/world
JAR_NAME="world-latest"

if [[ -e ${WORLD_PATH} ]]; then
    echo "$WORLD_PATH already exists!"
    exit 1
fi
echo "BEGIN : create dir $WORLD_PATH"
mkdir -p ${WORLD_PATH}
cd ..
echo "maven package..."
mvn clean package -Dmaven.test.skip=true
echo "copy scripts..."
cp bin/* ${WORLD_PATH}
chmod +x ${WORLD_PATH}/*.sh
rm -f ${WORLD_PATH}/install.sh
echo "copy jars..."
cd target
cp world-0.0.1-SNAPSHOT.jar ${WORLD_PATH}/${JAR_NAME}.jar
echo "copy resources..."
tar -xvf world-0.0.1-SNAPSHOT-resources.tar
cp application.properties ${WORLD_PATH}/

echo "END : installed world at $WORLD_PATH"

