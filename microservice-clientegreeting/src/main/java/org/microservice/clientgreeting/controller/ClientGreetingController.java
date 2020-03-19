package org.microservice.clientgreeting.controller;

import java.util.List;
import java.util.logging.Logger;

import org.microservice.clientgreeting.model.Greeting;
import org.microservice.clientgreeting.model.User;
import org.microservice.clientgreeting.service.ClientGreetingService;
import org.microservice.clientgreeting.service.ClientUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Client controller, fetches User info from the microservice via
 * {@link ClientGreetingService}.
 * 
 * @author Roberto Crespo
 */
@Controller
public class ClientGreetingController {

    protected ClientGreetingService helloWorldService;
    protected ClientUserService userService;

    protected Logger logger = Logger.getLogger(ClientGreetingController.class.getName());

    // constructor
    public ClientGreetingController(ClientGreetingService helloWorldService, ClientUserService userService) {
        this.helloWorldService = helloWorldService;
        this.userService = userService;
    }

    @RequestMapping("/greeting")
    public String goHome() {
        return "index";
    }

    @RequestMapping("/greeting/{name}")
    public String greeting(Model model, @PathVariable("name") String name) {

        logger.info("helloWorld-service greeting() invoked: " + name);

        Greeting greeting = helloWorldService.greeting(name);

        logger.info("helloWorld-service greeting() found: " + greeting.getContent());

        model.addAttribute("greeting", greeting.getContent());

        return "greeting";

    }

    @RequestMapping("/user/{id}")
    public String user(Model model, @PathVariable("id") int id) {

        logger.info("helloWorld-service greeting() invoked: " + id);

        User user = userService.getUser(id);

        logger.info("user-service user() found: " + user.toString());

        model.addAttribute("user", user.toString());

        return "user";

    }

    @RequestMapping("/user")
    public String user(Model model) {

        logger.info("helloWorld-service greeting() invoked: ");

        List<User> users = userService.getUsers();

        logger.info("user-service user() found: " + users.size());

        model.addAttribute("users", users);

        return "user";

    }

}
