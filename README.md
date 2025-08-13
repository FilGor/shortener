Prerequisites

Java 21 (or Docker)

Postman or curl for testing (Optional because could be done via swagger-ui) 

## How to Run the Application in docker

Build the Docker image:

`docker build -t url-shortener .`

Run the container on port 8080:


`docker run -p 8080:8080 url-shortener`

By default the app uses admin/changeme for the database credentials

Open Swagger UI (API documentation with endpoint descriptions) at:

`http://localhost:8080/swagger-ui/index.html`


Note: Before testing secured endpoints, you need to sign in via the /api/auth/signin endpoint using your email and password.

You will receive a JWT token in the response. Click the Authorize button in the top right corner of the Swagger UI, paste the token into the value field, and then you can execute the protected requests.


## Potential Improvements

CQRS (Command Query Responsibility Segregation) could be applied if there is a sufficiently high number of queries and commands to justify independent scaling of reads and writes. This could be achieved by using two microservices, each with its own database, and synchronizing them (for example, via Kafka).
