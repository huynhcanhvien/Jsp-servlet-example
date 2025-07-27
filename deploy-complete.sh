#!/bin/bash

TOMCAT_WEBAPPS="/Users/ngaofinn/tomcat/webapps"
PROJECT_NAME="Baithuchanh5"
DEST_DIR="$TOMCAT_WEBAPPS/$PROJECT_NAME"

echo "======================"
echo "🔁 Xóa thư mục cũ nếu tồn tại..."
rm -rf "$DEST_DIR"

echo "📁 Copy toàn bộ WebContent vào $DEST_DIR ..."
cp -r WebContent "$DEST_DIR"

echo "Cleaning old classes..."
rm -rf $TOMCAT_WEBAPPS/$PROJECT_NAME/WEB-INF/classes/*

echo "Compiling Java files in correct order..."

# 1. Compile utilities first
echo "1. Compiling utils..."
javac -cp "$TOMCAT_WEBAPPS/$PROJECT_NAME/WEB-INF/lib/*" \
      -d $TOMCAT_WEBAPPS/$PROJECT_NAME/WEB-INF/classes \
      src/utils/*.java

# 2. Compile bean classes (models)
echo "2. Compiling beans..."
javac -cp "$TOMCAT_WEBAPPS/$PROJECT_NAME/WEB-INF/lib/*" \
      -d $TOMCAT_WEBAPPS/$PROJECT_NAME/WEB-INF/classes \
      src/model/bean/*.java

# 3. Compile DAO classes
echo "3. Compiling DAOs..."
javac -cp "$TOMCAT_WEBAPPS/$PROJECT_NAME/WEB-INF/lib/*:$TOMCAT_WEBAPPS/$PROJECT_NAME/WEB-INF/classes" \
      -d $TOMCAT_WEBAPPS/$PROJECT_NAME/WEB-INF/classes \
      src/model/dao/*.java

# 4. Compile BO classes
echo "4. Compiling BOs..."
javac -cp "$TOMCAT_WEBAPPS/$PROJECT_NAME/WEB-INF/lib/*:$TOMCAT_WEBAPPS/$PROJECT_NAME/WEB-INF/classes" \
      -d $TOMCAT_WEBAPPS/$PROJECT_NAME/WEB-INF/classes \
      src/model/bo/*.java

# 5. Finally compile controllers
echo "5. Compiling controllers..."
javac -cp "$TOMCAT_WEBAPPS/$PROJECT_NAME/WEB-INF/lib/*:$TOMCAT_WEBAPPS/$PROJECT_NAME/WEB-INF/classes" \
      -d $TOMCAT_WEBAPPS/$PROJECT_NAME/WEB-INF/classes \
      src/controller/*.java

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    
    # Copy properties
    if [ -f "db.properties" ]; then
        cp db.properties $TOMCAT_WEBAPPS/$PROJECT_NAME/WEB-INF/classes/
    fi
    
    echo "Ready! Access: http://localhost:8080/$PROJECT_NAME"
else
    echo "Compilation failed!"
fi