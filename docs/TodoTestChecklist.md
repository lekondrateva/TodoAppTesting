| #  | Method | Description                                          | Expected result                                   |
|----|--------|------------------------------------------------------|---------------------------------------------------|
| 1  | POST   | Authorized user creates a Todo                       | 201 Created, Todo appears in the GET list         |
| 2  | POST   | Unauthorized user creates a Todo                     | 201 Created, Todo appears in the GET list         |
| 3  | POST   | Todo with empty `text` or `id` fields is not created | 400 Bad Request                                   |
| -- | ------ | ---------------------------------------------------- | -----------------------------------------         |
| 4  | GET    | Authorized user retrieves the list of Todos          | 200 OK                                            |
| 5  | GET    | Unauthorized user retrieves the list of Todos        | 200 OK                                            |
| 6  | GET    | Results are filtered by offset and limit             | 200 OK                                            |
| 7  | GET    | Error returned for invalid offset or limit           | 400 Bad Request                                   |
| -- | ------ | ---------------------------------------------------- | ----------------------------------------------    |
| 8  | PUT    | Authorized user updates a Todo                       | 200 OK, fields are updated                        |
| 9  | PUT    | Unauthorized user updates a Todo                     | 200 OK, fields are updated                        |
| 10 | PUT    | Error returned when updating a non-existent Todo     | 404 Not Found                                     |
| -- | ------ | ---------------------------------------------------- | ------------------------------------------------- |
| 11 | DELETE | Authorized user deletes a Todo                       | 204 No Content, Todo is removed from the GET list |
| 12 | DELETE | Unauthorized user cannot delete a Todo               | 401 Unauthorized                                  |
| 13 | DELETE | Deleting a non-existent Todo returns an error        | 404 Not Found                                     |
| -- | ------ | ---------------------------------------------------- | ---------------------------------------------     |
| 14 | POST   | WebSocket receives a message with the created task   | Message contains created Todo                     |
