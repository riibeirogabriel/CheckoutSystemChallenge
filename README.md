# How to use this Application?

## Requirements
* Java sdk 17 in the path environment of your terminal
* Maven 3.8 in the path environment of your terminal

Tip: You can use sdkman to install these dependencies in an easy way.

## Deploy and Usage

* Ensure that the provided wiremock products API is running
* execute the `./run.sh` command in terminal
* Access the Application REST API definition at `http://localhost:8080/swagger-ui/index.html`
(You also can consume the API with swagger (clicking on "Try It Out" button) or use other REST API Client like Postman)
* Add products in basket using the `/basket` route, inserting the productId (same Id of provided API) and the amount
* Then, access `/checkout` route to see the checkout details (added products, total price, total discount, total payable)

# Follow-up Questions

# 1. How long did you spend on the test?
Approximately 15 hours, but I could spend less time implementing an application more simple, without
the Clean Architecture concepts, the way that I have used(using Clean Architecture) will ensure a more 
decoupled Application and changes in any part of the Application, like the infrastructure aspects 
(e.g. changing the Products REST API to a GraphQL API) will be more easy and quick.


# 2. What would you add if you had more time?
* The integration tests to test all the application parts working together and also the application behavior through the controller

* The Persistence Layer in the Application. The Checkout class is storing the content inside its own object, 
but the correct approach would be to create a Persistence Interface and to store the values at the Drivers Layer, 
similar like I did with the Products API. So will be easier to change a memory database like H2 to a disk database 
like MongoDB or MySQL.

* This application REST API capability to also read the already added products in basket, before accomplish the checkout.

* A better openAPI (Swagger) documentation, because the generated openAPI doesn't have so much information in some 
endpoints.

# 3. How would you improve the product APIs that you had to consume?

* Create a Promotion entity/endpoint, and refer the promotions at Product info endpoint using the promotion Id.
* Use Pagination if the amount of listed Products was too big.


# 4. What did you find most difficult?

Plan this Application using the Clean Architecture concepts.

# 5. How did you find the overall experience, any feedback for us?

Thank you for this exercise, I think that even if I won't pass to the next step in the interview process, this 
was a good experience to work with Java and its frameworks and to put in practice the Clean Architecture knowledge.
