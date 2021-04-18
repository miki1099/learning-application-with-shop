# Spring REST API for learning application connected with shop

## Tools and frameworks used:
- Spring
- Swagger
- JPA
- Hibernate
- JWT

## REST API
### Link to swagger
https://rocky-brook-69170.herokuapp.com/swagger-ui/index.html#/
### API URL
https://rocky-brook-69170.herokuapp.com/
## Login to app
### Request
`POST /login`

    curl -X POST "http://rocky-brook-69170.herokuapp.com/login" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"login\": \"string\", \"password\": \"string\"}"
    
### Response
##### Body:
    {
    "address": {
    "city": "string",
    "country": "string",
    "homeNumber": "string",
    "id": 0,
    "street": "string"
      },
      "email": "string",
      "id": 0,
      "lastName": "string",
    "login": "string",
    "name": "string",
    "orders": [
    {
      "createOrderDate": "2021-04-18",
      "id": 0,
      "price": 0,
      "products": [
        {
          "description": "string",
          "id": 0,
          "name": "string",
          "numberAvailable": 0,
          "picture": "string",
          "price": 0
        }
      ],
      "realised": true,
      "user": {
        "address": {
          "city": "string",
          "country": "string",
          "homeNumber": "string",
          "id": 0,
          "street": "string"
        },
        "email": "string",
        "id": 0,
        "lastName": "string",
        "login": "string",
        "name": "string",
        "password": "string",
        "phone": "string"
      }
    }
    ],
    "phone": "string",
    "questionsLearned": [
    {
      "badAnswer1": "string",
      "badAnswer2": "string",
      "badAnswer3": "string",
      "category": "string",
      "goodAnswer": "string",
      "id": 0,
      "picture": "string",
      "questionName": "string"
    }
    ],
    "roles": [
    {
      "id": 0,
      "name": "string"
    }
    ],
    "scores": [
    {
      "id": 0,
      "score": 0,
      "scoreDate": "2021-04-18"
    }
    ]
    }
##### Header
    authorization: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxLGJlc3RMb2dpbiIsImlzcyI6ImV4YW1wbGUuaW8iLCJpYXQiOjE2MTg3MzMxNzIsImV4cCI6MTYxOTMzNzk3Mn0.-QZ13Er3O3OwR933znLtCMevnD5WgzA_Hznip95UAEg-ih8UrZcsee05kATSWMdAhglwE4GxQuWCqOYTpEvfqg 
    cache-control: no-cacheno-storemax-age=0must-revalidate 
    connection: keep-alive 
    content-type: application/json 
    date: Sun18 Apr 2021 08:06:12 GMT 
    expires: 0 
    keep-alive: timeout=60 
    pragma: no-cache 
    transfer-encoding: chunked 
    vary: OriginAccess-Control-Request-MethodAccess-Control-Request-Headers 
    x-content-type-options: nosniff 
    x-frame-options: DENY 
    x-xss-protection: 1; mode=block 