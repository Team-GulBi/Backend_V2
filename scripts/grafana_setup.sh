#!/bin/sh
set -e

chown -R 472:472 /var/lib/grafana

mkdir -p /etc/grafana/provisioning/datasources

cat <<EOF > /etc/grafana/provisioning/datasources/ds-prometheus.yaml
apiVersion: 1
datasources:
- name: Prometheus
  type: prometheus
  access: proxy
  orgId: 1
  url: http://prometheus:9090
  basicAuth: false
  isDefault: false
  version: 1
  editable: false
EOF
cat <<EOF > /etc/grafana/provisioning/datasources/ds.yaml
apiVersion: 1
datasources:
- name: Loki
  type: loki
  access: proxy
  orgId: 1
  url: http://loki:3100
  basicAuth: false
  isDefault: true
  version: 1
  editable: false
EOF

exec /run.sh
