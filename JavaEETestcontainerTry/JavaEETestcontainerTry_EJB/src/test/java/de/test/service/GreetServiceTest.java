package de.test.service;

import org.assertj.core.api.Assertions;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;

@ExtendWith(WeldJunit5Extension.class)
public class GreetServiceTest {

    @Inject
    GreetService sut;

    @Test
    public void greet() {

        String result = sut.greet("Florian");
        Assertions.assertThat(result).isEqualTo("Hallo Florian");
    }
}
