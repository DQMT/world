WORLD_PATH=/opt/world
JAR_NAME="world-latest"

if [[ -e ${WORLD_PATH} ]]; then
    echo "$WORLD_PATH already exists!"
    exit 1
fi

mkdir -p ${WORLD_PATH}
cd ..
#git pull
mvn clean package -Dmaven.test.skip=true

cp bin/* ${WORLD_PATH}
chmod +x ${WORLD_PATH}/*
rm -f ${WORLD_PATH}/install.sh
cp target/world-0.0.1-SNAPSHOT.jar ${WORLD_PATH}/${JAR_NAME}.jar
echo "installed world at $WORLD_PATH"


