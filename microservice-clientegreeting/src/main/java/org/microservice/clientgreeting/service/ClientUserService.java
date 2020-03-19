package org.microservice.clientgreeting.service;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.microservice.clientgreeting.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Hide the access to the microservice inside this local service.
 * 
 * @author Roberto Crespo
 */
@Service
public class ClientUserService {

    @Autowired
    @LoadBalanced
    protected RestTemplate restTemplate;

    protected String serviceUrl;

    protected Logger logger = Logger.getLogger(ClientUserService.class.getName());

    public ClientUserService(String serviceUrl) {
        this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl : "http://" + serviceUrl;
    }

    /**
     * The RestTemplate works because it uses a custom request-factory that uses
     * Ribbon to look-up the service to use. This method simply exists to show this.
     */
    @PostConstruct
    public void demoOnly() {
        // Can't do this in the constructor because the RestTemplate injection
        // happens afterwards.
        logger.warning("The RestTemplate request factory is " + restTemplate.getRequestFactory());
    }

    public User getUser(int id) {
        logger.info("greeting() invoked: for " + id);
        User user = restTemplate.getForObject(serviceUrl + "/users/{userId}", User.class, id);

        return user;

    }

    public List<User> getUsers() {
        logger.info("greeting() invoked: for " );
        List<User> users = restTemplate.getForObject(serviceUrl + "/users", List.class);
        
        return users;
        
    }

}
