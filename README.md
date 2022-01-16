# bill-management-service
Java REST api to support bill-management-ui

# API
- http://localhost:8080/billmgnt/api/v1/create
- POST payload 
- ```json{
       "bill": {
           "accountName": "account name 456",
           "userName": "username",
           "company": "company",
           "password": "password",
           "tags": [
               "tag1",
               "tag2"
           ],
           "description": "some description"
       }
   }
