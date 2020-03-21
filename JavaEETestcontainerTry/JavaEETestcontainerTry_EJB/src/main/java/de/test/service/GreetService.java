package de.test.service;

import javax.inject.Inject;

public class GreetService {

    @Inject
    StringBuilderService greetService;


    public String greet(String name) {
        String output = greetService.buildGreetString(name);
        System.out.println(output);

        return output;
    }
}
