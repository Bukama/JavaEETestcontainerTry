package de.test.util;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.core.api.provider.BeanProvider;

import javax.enterprise.inject.spi.BeanManager;
import java.lang.annotation.Annotation;

public class BeanManagerHelper {

    private CdiContainer cdiContainer;

    public BeanManagerHelper() {
        cdiContainer = CdiContainerLoader.getCdiContainer();
        cdiContainer.boot();
        cdiContainer.getContextControl().startContexts();
    }

    public <T> T createInstance(Class<T> clazz) {
        return BeanProvider.getContextualReference(clazz);
    }

    public <T> T createInstance(Class<T> clazz,
                                Annotation... annotations) {
        return BeanProvider.getContextualReference(clazz, annotations);
    }

    public BeanManager getBeanManager() {
        return cdiContainer.getBeanManager();
    }
    public void shutdown() {
        cdiContainer.shutdown();
    }

}
