package de.test.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class GreetService {

    @Inject
    StringBuilderService greetService;

    public String greet(String name) {
        String output = greetService.buildGreetString(name);
        System.out.println(output);

        return output;
    }
}
