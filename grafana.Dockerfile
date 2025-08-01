FROM grafana/grafana:latest

USER root

COPY ./scripts/grafana_setup.sh /grafana_setup.sh

RUN chmod +x /grafana_setup.sh

ENTRYPOINT ["/bin/sh", "-c", "/grafana_setup.sh && /run.sh"]

USER grafana