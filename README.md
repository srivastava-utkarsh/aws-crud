# AWS RDS Deployment Guide

## Manual Deployment

### Beanstalk
Upload jar file from target folder in Beanstalk. MySql DB is automatically created and env variables RDS_HOSTNAME, RDS_PORT, RDS_DB_NAME, RDS_USERNAME, RDS_PASSWORD are automatically populated by Beanstalk. Its very simple. just upload jar and app will deploy

### Fargate
RDS has to be created separately and in ECS task definition file we've to manually set env variables --> RDS_HOSTNAME, RDS_PORT, RDS_DB_NAME, RDS_USERNAME, RDS_PASSWORD

Manually create docker file in local code and push to ECR:
1. docker build --platform linux/amd64 -t aws-rds-app .  (note platform params, this is to make dockerfile aws linux compatible)
2. docker tag aws-rds-app:latest 339495302685.dkr.ecr.ap-south-1.amazonaws.com/utk/aws-rds:latest. (tag with AWS ECR id)
3. docker push 339495302685.dkr.ecr.ap-south-1.amazonaws.com/utk/aws-rds:latest (using aws cli tool after login from terminal)

Access app using --> http://hostname:9898/swagger-ui.html


### Running local

1. RDS instance created in AWS
2. ./mvnw clean install -DskipTests
3. docker build -t aws-crud .
4. docker run -p 9898:9898 aws-crud
5. Check env variables in Dockerfile


### Once the application is running, you can access:
Swagger UI: http://localhost:9898/swagger-ui.html
API Docs (JSON): http://localhost:9898/v3/api-docs 
