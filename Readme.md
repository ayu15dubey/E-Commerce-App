E-commerce application using Microservices Architecture

-> Here we have developed various microservices application and every individual service has it's own particularity.
->The four services are as follows:
   1. Order service.
   2. Product service.
   3. Inventory service.
   4. Customer service.

-->>Product service:- For viewing the different products we made product service.

-->>Order service:- After viewing the product this service is being called for placing your order.

-->>Inventory service:- If that product is present in the stock this keeps track of that.

-->>Customer service:- This service is also much important because if you have to place any order then you have to be the customer, so using this service you can become customer.

-->>Aggregator service:- Most important service the whole use-case is defined in this service like view product, become customer, place order, view order, update order delete order and etc.


# Have implemented all the microservices tools like Zuul API gateway, Ribbon(load balancing), hystrix(circuit breaker, Eureka server, config server/client and etc.

## Included docker file which will make the docker image and can be use to containerize services which is independent of environment.

Please have a look and can mail me at ayu16dubey@gmail.com for any queries.

Thankyou.

##Every line code is written by us##

