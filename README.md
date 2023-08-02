# rest-api-crud-example-2
Version +1.0 

## API REST CRUD Spring Boot with MySQL Database Connection ## 

This is a README for the REST API developed with Spring Boot that provides endpoints to interact with a MySQL database with the dynamics of a store. This API will allow to perform CRUD (Create, Read, Update and Delete) operations to the Customer, Item and Sale models in the database through HTTP requests.

## Requirements
To run this API, you will need to have the following installed:

1. Java 17 or higher.
2. A MySQL database server.
3. A tool for testing HTTP requests such as Postman.

## Database Configuration
1. Create a container in Docker with the following configuration and run it:
   **docker run -d --rm --name mysql_container -e MYSQL_ROOT_PASSWORD=pass -p 3306:3306 -v mysql_data:/var/lib/mysql mysql:8 --default-authentication-plugin=mysql_native_password**

2. In the container with MySQL, create an empty database.

3. Open the application.yml file located in the src/main/resources folder.

4. Modify the following lines to match the data in your database:

  datasource:
      url: jdbc:mysql://localhost:3306/[YOUR_DATABASE]?serverTimezone=UTC
      username: [YOUR_USERNAME]
      password: [YOUR_PASSWORD]
      driver-class-name: com.mysql.cj.jdbc.Driver

## Application Execution

1. Clone this repository or download the files to your local machine.

3. Open the project in your selected IDE.

5. Compile and run the Spring Boot application with the command mvn clean spring-boot:run

## Endpoints
The API provides the following endpoints to interact with the database:

- GET /api/customer: Gets all the customers stored in the database.
- GET /api/customer/{id}: Gets a specific customer by its ID.
- POST /api/customer: Creates a new customer in the database.
- PUT /api/customer/{id}: Updates an existing customer by its ID, and creates a new one if the ID does not exist.
- DELETE /api/customer/{id}: Deletes an existing customer by its ID.
- GET /api/customer/bystatus/{status}: Gets all the customers stored in the database by status.

- GET /api/item: Gets all the items stored in the database.
- GET /api/item/{id}: Gets a specific item by its ID.
- POST /api/item: Creates a new item in the database.
- PUT /api/item/{id}: Updates an existing item by its ID, and creates a new one if the ID does not exist.
- DELETE /api/item/{id}: Deletes an existing item by its ID.
- GET /api/item/bystatus/{status}: Gets all the items stored in the database by status.

- GET /api/sale: Gets all the sales stored in the database.
- GET /api/sale/{id}: Gets a specific sale by its ID.
- POST /api/sale: Creates a new sale in the database.
- PUT /api/sale/{id}: Updates an existing sale by its ID, and creates a new one if the ID does not exist.
- DELETE /api/sale/{id}: Deletes an existing sale by its ID.
- GET /api/sale/bystatus/{status}: Gets all the sale stored in the database by status.
- GET /api/sale/{id}/items: Gets all items stored in the database by sale.

## Data Format

The API expects to receive and send data in JSON format. Make sure you set up your request headers correctly so that the API can understand the data format you send and respond to you with data in JSON format as well.

Example of the insertion of a new customer:

**curl -X POST -H "Content-Type: application/json" -d '{
  "address": "address example",
  "email": "email@example-com",
  "id": null,
  "lastName": "lastname",
  "name": "name",
  "phone": "phone example",
  "status": 1
}' http://localhost:8080/api/customer******

## Responses
The API will respond with appropriate HTTP status codes and JSON response messages. Be sure to review the status codes and the content of the responses to understand the outcome of your requests.

## Contributions
If you find bugs or have suggestions for improving this API, feel free to make a pull request or open an issue in the repository.

Thanks for contributing!
