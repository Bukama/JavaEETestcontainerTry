package de.test.service;

import de.test.util.BeanManagerHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

//@ExtendWith(WeldJunit5Extension.class)
public class GreetServiceWeldTest {


//    @WeldSetup
//    public WeldInitiator weld = WeldInitiator.of(GreetService.class);

    private static BeanManagerHelper bm;
    @BeforeAll
    public static void initialiseBeanManager() {
        bm = new BeanManagerHelper();
    }


//    @Test
//    public void greet() {
//        //String result = sut.greet("Florian");
//        String result = weld.select(GreetService.class).get().greet("Florian");
//        Assertions.assertThat(result).isEqualTo("Hallo Florian");
//    }

    @Test
    public void greet() {

        System.out.println(bm.getClass().getName());

        GreetService sut = bm.createInstance(GreetService.class);
        bm.createInstance(StringBuilderService.class);

        String result = sut.greet("Florian");
        Assertions.assertThat(result).isEqualTo("Hallo Florian");
    }
}
