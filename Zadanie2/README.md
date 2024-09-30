# How to use application

## Users endpoints:

1. To add a new user an API consumer can send a `POST` request to the endpoint `/api/users` with schema:
```json
{
    "firstName": ${firstName},
    "lastName": ${lastName},
    "email": ${email}
}
```
2. To delete an existing user an API consumer can send a `DELETE` request to the endpoint `/api/users/{id}`
### Example:
```
curl -X DELETE http://localhost:8080/api/users/1
```
3. To retrieve information about a single user, send a `GET` request to endpoint `/api/users/{id}`
### Example:
```
curl -X GET http://localhost:8080/api/users/1
```
4. To retrieve information about more users, use endpoint `/api/users/all`. It is also possible to filter users using the following parameters:
- `firstName`, parameter takes string values and is not case sensitive
- `lastName`, parameter takes string values and is not case sensitive
- `email`, parameter takes string values and is not case sensitive
### Example:
```
curl -X GET http://localhost:8080/api/users/all?firstName=${firstName}&lastName=${lastName}&email=${email}
```

## Tasks endpoints:

1. To add new task an API consumer can send a `POST` request to the endpoint `/api/task` with schema:
```json
{
  "title": ${title},
  "description": ${description},
  "status": ${taskStatus},
  "deadline": ${date in format YYYY-MM-DD},
  "assignedUsers": [
    ${usersID}
  ]
}
```
2. To delete an existing user an API consumer can send a `DELETE` request to the endpoint `/api/tasks/{id}`
### Example:
```
curl -X DELETE http://localhost:8080/api/tasks/1
```
3. To retrieve information about a single task, send a `GET` request to endpoint `/api/tasks/{id}`
### Example:
```
curl -X GET http://localhost:8080/api/users/1
```
4. To retrieve information about more tasks, use endpoint `/api/tasks/all`. It is also possible to filter tasks using the following parameters:
- `title`, parameter takes string values and is not case sensitive
- `description`, parameter takes string values and is not case sensitive
- `status`, parameter takes values such as `SUBMITTED`, `IN_PROGRESS`, `REOPENED`, `DONE`
- `deadlineFrom`, parameter accepts date values in the format `YYYY-MM-DD`
- `deadlineTo`, parameter accepts date values in the format `YYYY-MM-DD`
### Example:
```
curl -X GET http://localhost:8080/api/tasks/all?title=${title}&description=${description}&status=${taskStatus}&deadlineFrom=${YYYY-MM-DD}&deadlineTo=${YYYY-MM-DD}
```
5. There are two methods for task editing using endpoint `/api/tasks/{id}`
- If we want to edit the entire task we use a `PUT` request sending the entire schema
- If we want to edit only selected fields we use the 'PATCH' request only with the edited fields
### Example:
```
curl -X PATCH http://localhost/api/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{"status": "DONE"}'
```