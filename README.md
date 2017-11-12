Securing Spring boot API with JWT
---------------------------------

In this example (user CRUD)  we check that the incoming request has a valid JWT token.

Scala language
----------------
The Scala language is used to simplify the syntax and reduce the number of lines of code

Run API
-----------
```sh
gradle bootRun
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







