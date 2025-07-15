# Payment Service
* This service gets the requests from **Order Service** and then it interacts with payment gateway for processing payments, checking the status of past payments and refunds.
* At present only **Order Service** interacts with this payment service via **service discovery**. So haven't implemented authentication check on payment service any way all the requests from client side to order service have authentication check.
* Integrated **Stripe** payment gateway with payment service for accepting the payments

**API Endpoints:**

### 1.`[POST]` api/payment
Creates a session for payment and returns the URL for making payment.

**Request body:**

Sample request payload:
````json
{
  "orderId":1234,
  "amount":5000,
  "orderItemDtos":[{
    "productName":"Dell keyboard 115",
    "unitPrice":2500,
    "quantity":2
  }]
} 
````


**Response body:**

Sample Response:
````json
{
"paymentLink":"https://strpie.checkout.com/..."
}
````   

### 2.`[GET]` api/payment/status/{session_id}

Path variable: session_id*

Here session_id is the gateway session id which will be get from the redirected url after processing the payment.

**Response body:**
Sample Response:
````json
{
"orderId":1234,
"amount":5000,
"paymentStatus": "Initiated/Processing/Completed/Failed/Cancelled"
}
````

**Database Tables:**
* **payment**
  * id
  * order_id
  * amount
  * currency
  * paidDate
  * status (Initiated/Processing/Completed/Failed/Cancelled)
  * gateway_session_id
  * created_date
  * updated_date

* **transaction**
  * id
  * amount
  * reference_id
  * gateway_response
  * status (Initiated/Processing/Failed/Completed)
  * type (payment/refund)
  * payment_id 

**Technologies:**
* Java 17
* Spring Boot
* My SQL

**Maven dependencies:**
* lombok
* spring-boot-starter-data-jpa
* mysql-connector-j
* stripe-java
* spring-cloud-starter-netflix-eureka-client


   

