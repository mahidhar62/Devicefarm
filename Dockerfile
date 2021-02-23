FROM python:3.6-slim
MAINTAINER varunkumar032@gmail.com
COPY . /device-farm-test
WORKDIR /device-farm-test
RUN apt update && apt install -y npm
RUN npm i npm@latest -g && npm install -g appium@1.4.10
RUN pip install --no-cache-dir -r requirements.txt
RUN ["pytest", "-v", "--junitxml=reports/result.xml"]
CMD tail -f /dev/null
