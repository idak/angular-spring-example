Securing Spring boot API with JWT
---------------------------------

In this example (user CRUD)  we check that the incoming request has a valid JWT token.


Run API
-----------

**Java API version**

```sh
back-end-java/gradlew bootRun
```


**Scala API version**

```sh
back-end-scala/gradlew bootRun
```

### Authentication
To simulate authentication and generate JWT token, call /api/auth with body :
```json
{
  "username":"admin", 
  "password":"admin"
}
```

### Use JWT token
Add token in Authorisation header to call other resources :

```html
GET /api/users
DELETE /api/users/{id}
POST /api/users
```

Angular Webapp
----------------

### Start 

```sh
npm start 
```

Docker
----------------

### Run Api and Webapp

add volume front-end/src to **docker-compose-dev.yml** file and run it :

```sh
docker-compose -f docker/docker-compose-dev.yml up
```

