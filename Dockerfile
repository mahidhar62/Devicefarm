FROM python:3.6-slim
MAINTAINER varunkumar032@gmail.com
COPY . /device-farm-test
WORKDIR /device-farm-test
RUN apt update && apt install -y npm && apt install -y sudo
#RUN npm install -g update-node && update-node 14.16.0
RUN sudo npm install -g appium@1.4.10 --unsafe-perm=true --allow-root
RUN pip install --no-cache-dir -r requirements.txt
RUN mkdir ~/.aws
COPY ./aws-config ~/.aws/config 
COPY ./aws-creds ~/.aws/credentials
RUN ["pytest", "-v", "--junitxml=reports/result.xml"]
CMD tail -f /dev/null
