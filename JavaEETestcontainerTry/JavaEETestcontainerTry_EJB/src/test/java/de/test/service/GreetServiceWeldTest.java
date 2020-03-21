package de.test.service;

import org.assertj.core.api.Assertions;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@EnableAutoWeld
@AddPackages(GreetService.class)
public class GreetServiceWeldTest {

    @Inject
    GreetService sut;

    @Test
    public void greet() {
        String result = sut.greet("Nameless");
        Assertions.assertThat(result).isEqualTo("Hallo Nameless");
    }
}
