package org.microservice.clientgreeting;

import org.microservice.clientgreeting.controller.ClientGreetingController;
import org.microservice.clientgreeting.controller.ClientGreetingHomeController;
import org.microservice.clientgreeting.service.ClientGreetingService;
import org.microservice.clientgreeting.service.ClientUserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * TestHelloWorld Server. Works as a microservice client, fetching data from the
 * HelloWorld-Service. Uses the Discovery Server (Eureka) to find the
 * microservice.
 * 
 * @author Roberto Crespo
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(useDefaultFilters = false) // Disable component scanner ...
@EnableCircuitBreaker
public class ClientGreetingServer {

    /**
     * URL uses the logical name of user-service - upper or lower case, doesn't
     * matter.
     */
    public static final String SERVICE_URL = "http://GREETING-SERVICE";
    public static final String SERVICE_USER_URL = "http://USER-SERVICE";

    /**
     * Run the application using Spring Boot and an embedded server engine.
     * 
     * @param args Program arguments - ignored.
     */
    public static void main(String[] args) {
        // Tell server to look for web-server.properties or web-server.yml
        // System.setProperty("spring.config.name", "greeting-client");
        SpringApplication.run(ClientGreetingServer.class, args);
    }

    /**
     * A customized RestTemplate that has the ribbon load balancer build in. Note
     * that prior to the "Brixton"
     * 
     * @return
     */
    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * The UserService encapsulates the interaction with the micro-service.
     * 
     * @return A new service instance.
     */
    @Bean
    public ClientGreetingService helloWorldService() {
        return new ClientGreetingService(SERVICE_URL);
    }

    @Bean
    public ClientUserService userService() {
        return new ClientUserService(SERVICE_USER_URL);
    }

    /**
     * Create the controller, passing it the {@link ClientGreetingService} to use.
     * 
     * @return
     */
    @Bean
    public ClientGreetingController helloWorldController() {
        return new ClientGreetingController(helloWorldService(), userService());
    }

    @Bean
    public ClientGreetingHomeController homeController() {
        return new ClientGreetingHomeController();
    }
}
