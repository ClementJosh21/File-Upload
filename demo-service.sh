#!/bin/bash -e
# demo Service
echo "Starting demo Service"
java -Xmx1024m -jar $APP_HOME/demo-0.0.1-SNAPSHOT.jar --port=$DEMO_SERVICE_PORT