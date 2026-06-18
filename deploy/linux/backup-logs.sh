#!/usr/bin/env bash
set -euo pipefail

BACKUP_DIR="/var/backups/petshop-logs"
TODAY="$(date +%Y%m%d-%H%M%S)"

sudo mkdir -p "$BACKUP_DIR"

sudo tar -czf "$BACKUP_DIR/nginx-$TODAY.tar.gz" /var/log/nginx || true
sudo tar -czf "$BACKUP_DIR/tomcat9-$TODAY.tar.gz" /opt/tomcat9/logs || true

sudo find "$BACKUP_DIR" -type f -name "*.tar.gz" -mtime +7 -delete

echo "log backup saved to $BACKUP_DIR"
