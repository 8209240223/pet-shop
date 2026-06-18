#!/usr/bin/env bash
set -euo pipefail

APP_NAME="pet-shop-learning"
PROJECT_DIR="$(cd "$(dirname "$0")/../.." && pwd)"
TOMCAT_HOME="/opt/tomcat9"
TOMCAT_WEBAPPS="$TOMCAT_HOME/webapps"
NGINX_HTML="/usr/share/nginx/petshop"
NGINX_CONF="/etc/nginx/conf.d/petshop.conf"

echo "1. build backend war"
cd "$PROJECT_DIR"
mvn -DskipTests package

echo "2. deploy backend war to Tomcat 9 ROOT"
sudo systemctl stop tomcat9 || true
sudo mkdir -p "$TOMCAT_WEBAPPS" "$TOMCAT_HOME/data" "$TOMCAT_HOME/temp"
sudo rm -rf "$TOMCAT_WEBAPPS/ROOT" "$TOMCAT_WEBAPPS/ROOT.war"
sudo cp "$PROJECT_DIR/target/${APP_NAME}-1.0.0.war" "$TOMCAT_WEBAPPS/ROOT.war"
sudo chown -R stu:stu "$TOMCAT_HOME"
sudo systemctl start tomcat9

echo "3. deploy frontend static files to Nginx"
sudo mkdir -p "$NGINX_HTML"
sudo find "$NGINX_HTML" -mindepth 1 -maxdepth 1 -exec rm -rf {} +
sudo cp -r "$PROJECT_DIR/src/main/resources/static/." "$NGINX_HTML/"
sudo cp "$PROJECT_DIR/deploy/nginx/petshop.conf" "$NGINX_CONF"
sudo nginx -t
sudo systemctl reload nginx

echo "4. deployment finished"
echo "open http://192.168.132.128/"
