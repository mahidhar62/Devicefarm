FROM maven:latest
COPY . /device-farm-test
WORKDIR /device-farm-test
COPY ./aws-config /root/.aws/config 
COPY ./aws-creds /root/.aws/credentials
CMD /bin/sh -c /device-farm-test/runtest.sh
