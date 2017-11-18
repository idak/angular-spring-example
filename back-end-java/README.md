Securing Spring boot API with JWT
---------------------------------

In this example (user CRUD) we check that the incoming request has a valid JWT token.

Configure Lombok
----------------
This example use [Lombok](https://projectlombok.org/) library to simplify all POJO code .

### Eclipse
Download lombok.jar [here](https://projectlombok.org/downloads/lombok.jar), open eclipse.ini file and add and make entry below
```
-vmargs
```
as

```
-Xbootclasspath/a:lombok.jar
-javaagent:lombok.jar
```

### IntelliJ 
Install the plugin and restart IDE.

**File>Settings>Plugins**

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
PUT /api/users
POST /api/users 







