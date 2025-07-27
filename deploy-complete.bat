@echo off
set TOMCAT_WEBAPPS=C:\Users\ngaofinn\tomcat\webapps
set PROJECT_NAME=Baithuchanh5
set DEST_DIR=%TOMCAT_WEBAPPS%\%PROJECT_NAME%

echo ======================
echo 🔁 Xóa thư mục cũ nếu tồn tại...
rmdir /S /Q "%DEST_DIR%"

echo 📁 Copy toàn bộ WebContent vào %DEST_DIR% ...
xcopy WebContent "%DEST_DIR%" /E /I /Y

echo Cleaning old classes...
rmdir /S /Q "%DEST_DIR%\WEB-INF\classes"
mkdir "%DEST_DIR%\WEB-INF\classes"

echo 1. Compiling utils...
javac -cp "%DEST_DIR%\WEB-INF\lib\*" -d "%DEST_DIR%\WEB-INF\classes" src\utils\*.java

echo 2. Compiling beans...
javac -cp "%DEST_DIR%\WEB-INF\lib\*" -d "%DEST_DIR%\WEB-INF\classes" src\model\bean\*.java

echo 3. Compiling DAOs...
javac -cp "%DEST_DIR%\WEB-INF\lib\*;%DEST_DIR%\WEB-INF\classes" -d "%DEST_DIR%\WEB-INF\classes" src\model\dao\*.java

echo 4. Compiling BOs...
javac -cp "%DEST_DIR%\WEB-INF\lib\*;%DEST_DIR%\WEB-INF\classes" -d "%DEST_DIR%\WEB-INF\classes" src\model\bo\*.java

echo 5. Compiling controllers...
javac -cp "%DEST_DIR%\WEB-INF\lib\*;%DEST_DIR%\WEB-INF\classes" -d "%DEST_DIR%\WEB-INF\classes" src\controller\*.java

IF %ERRORLEVEL% EQU 0 (
    echo Compilation successful!
    IF EXIST db.properties (
        copy db.properties "%DEST_DIR%\WEB-INF\classes"
    )
    echo Ready! Access: http://localhost:8080/%PROJECT_NAME%
) ELSE (
    echo Compilation failed!
)
