# peter-mac-web-app-backend
`https://9xgzyeg1c1.execute-api.ap-southeast-2.amazonaws.com/staging` <br>

This is the backend service for the PeterMac Web application. <br>
This is deployed onto AWS and is using AWS Amplify and the Amplify CLI develop the Serverless APIs

## Documentation
Documentation is using openapi swagger, saved in `api/swagger.yml`

## First time set up 
install the aws-amplify cli globally using npm <br>

`npm install -g @aws-amplify/cli`

In `backend` folder run the following command in terminal 

`amplify pull --appId d3313yaw6v22vl --envName staging`

This should give access to the AWS Amplify backend console for our project

## Useful amplify commands
`amplify function build`: builds all of your functions currently in the project<br>
`amplify mock function <functionName>`: runs your function locally, test cases can be added to lambda's event.json file<br>
`amplify push`: builds all of your local backend resources and provisions them in the cloud<br>
`amplify pull`: pull the latest version of backend service from AWS<br>
`amplify api update`: allows to update the api, remove path, update path, add new path<br>

# Terraform

Terraform will be used to customize and deploy our non-serverless infrastructure to the cloud, S3 and RDS<br>
S3 will be used to store the certificates and RDS database will be running postgres

`terraform init`: start terraform and download the providers requested (AWS)<br>
`terraform plan`: see an outline of the changes that will be made<br>
`terraform apply`: apply changes to AWS<br>