package de.test.service;

import org.assertj.core.api.Assertions;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@EnableAutoWeld
public class StringBuilderWeldTest {

    @Inject
    StringBuilderService sut;

    @Test
    public void greet() {
        String result = sut.buildGreetString("Nameless");
        Assertions.assertThat(result).isEqualTo("Hallo Nameless");
    }
}
