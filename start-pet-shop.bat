@echo off
setlocal

set "PROJECT_DIR=%~dp0"
set "JAVA_HOME=D:\JDK21"
set "MAVEN_CMD=C:\Users\王仪杰\Desktop\其他\IntelliJ IDEA 2024.3.1.1\plugins\maven\lib\maven3\bin\mvn.cmd"

if not exist "%JAVA_HOME%\bin\java.exe" (
  echo 未找到 JDK：%JAVA_HOME%
  echo 请确认 D:\JDK21 是否存在，或者在脚本里修改 JAVA_HOME。
  pause
  exit /b 1
)

if not exist "%MAVEN_CMD%" (
  echo 未找到 Maven：%MAVEN_CMD%
  echo 请确认 IntelliJ IDEA 的 Maven 插件目录是否存在。
  pause
  exit /b 1
)

cd /d "%PROJECT_DIR%"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo 正在打包宠物商店项目...
call "%MAVEN_CMD%" -q -DskipTests package
if errorlevel 1 (
  echo 打包失败，请查看上面的错误信息。
  pause
  exit /b 1
)

echo.
echo 项目启动后请打开：http://localhost:8080/
echo H2 控制台：http://localhost:8080/h2-console
echo 管理员账号：admin
echo 管理员密码：123456
echo.
"%JAVA_HOME%\bin\java.exe" -Dfile.encoding=UTF-8 -jar "%PROJECT_DIR%target\pet-shop-learning-1.0.0.war"

endlocal
