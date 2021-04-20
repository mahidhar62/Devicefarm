export AWS_ACCESS_KEY_ID=`cat /root/.aws/credentials | grep aws_access_key_id | awk -F'=' '{print $2}'`
export AWS_SECRET_ACCESS_KEY=`cat /root/.aws/credentials | grep aws_secret_access_key | awk -F'=' '{print $2}'`
mvn test
