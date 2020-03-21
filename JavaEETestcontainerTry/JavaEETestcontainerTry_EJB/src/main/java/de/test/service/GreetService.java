package de.test.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class GreetService {

    @Inject
    StringBuilderService greetService;

    public String greet(String name) {
        String output = greetService.buildGreetString(name);
        System.out.println(output);

        return output;
    }
}
