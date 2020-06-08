#!/bin/sh
APP_SRC_PATH=$APP_SRC_PATH
APP_NAME="world"
JAVA_OPTS="-Xms256m -Xmx256m -Dfile.encoding=UTF-8"
JAR_NAME="world-latest"
psid=0

checkpid() {
   javaps=$(pgrep -f ${JAR_NAME})

   if [[ -n "$javaps" ]]; then
      psid=${javaps}
   else
      psid=0
   fi
}

start() {
   checkpid

   if [[ ${psid} -ne 0 ]]; then
      echo "================================"
      echo "warn: $APP_NAME already started! (pid=$psid)"
      echo "================================"
   else
      echo "Starting $APP_NAME ..."
      nohup java ${JAVA_OPTS} -jar ${JAR_NAME}.jar > logfile 2>&1 &
      sleep 1
      checkpid
      if [[ ${psid} -ne 0 ]]; then
         echo "(pid=$psid) [OK]"
      else
         echo "[Start Failed]"
      fi
   fi
}

stop() {
   checkpid

   if [[ ${psid} -ne 0 ]]; then
      echo -n "Stopping $APP_NAME ...(pid=$psid) "
      kill -9 ${psid}

      if [[ $? -eq 0 ]]; then
         echo "[Stop OK]"
      else
         echo "[Stop Failed]"
      fi

      checkpid
      if [[ ${psid} -ne 0 ]]; then
         stop
      fi
   else
      echo "================================"
      echo "warn: $APP_NAME is not running"
      echo "================================"
   fi
}

status() {
   checkpid
   if [[ ${psid} -ne 0 ]];  then
      echo "$APP_NAME is running! (pid=$psid)"
   else
      echo "$APP_NAME is not running"
   fi
}

showlog() {
   tail -f logfile
}

upgrade() {
    WORLD_PATH=`pwd`
    if [[ -z ${APP_SRC_PATH} ]]; then
        echo "please set APP_SRC_PATH in this script first!"
        exit 1
    elif [[ ! -d ${APP_SRC_PATH} ]]; then
        echo "${APP_SRC_PATH} is not a folder!"
        exit 1
    else
        echo "now upgrade start..."
    fi
    cd ${APP_SRC_PATH}
    echo "git pull..."
    git pull
    echo "maven package..."
    mvn clean package -Dmaven.test.skip=true
    echo "copy scripts..."
    cp -f bin/* ${WORLD_PATH}
    chmod +x ${WORLD_PATH}/*.sh
    rm -f ${WORLD_PATH}/install.sh
    echo "copy jars..."
    cd target
    cp -f world-0.0.1-SNAPSHOT.jar ${WORLD_PATH}/${JAR_NAME}.jar
    echo "copy resources..."
    tar -xvf world-0.0.1-SNAPSHOT-resources.tar
    cp -f application.properties ${WORLD_PATH}/
    echo "upgrade finish, please use 'sh world.sh restart' to restart app!"
}

case "$1" in
   'start')
     start
      ;;
   'stop')
     stop
     ;;
   'restart')
     stop
     start
     ;;
   'status')
     status
     ;;
   'log')
     showlog
     ;;
   'upgrade')
     upgrade
     ;;
  *)
     echo "Usage: $0 {start | stop | restart | status | upgrade | log}"
     exit 1
esac
exit 0