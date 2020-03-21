package de.test.util;

import org.apache.deltaspike.core.util.metadata.AnnotationInstanceProvider;
import org.apache.deltaspike.core.util.metadata.builder.AnnotatedTypeBuilder;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.inject.Inject;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

public class CDITestExtension implements Extension {
    public<X> void processInjectionTarget(
            @Observes ProcessAnnotatedType<X> pat) {
        if(pat.getAnnotatedType().isAnnotationPresent(Stateless.class)) {
            createEJBWrapper(pat, pat.getAnnotatedType());
        }
    }
    private<X> void createEJBWrapper(ProcessAnnotatedType<X> pat,
                                     final AnnotatedType<X> at) {
        Transactional transactionalAnnotation=
                AnnotationInstanceProvider.of(Transactional.class);
        RequestScoped requestScopedAnnotation=
                AnnotationInstanceProvider.of(RequestScoped.class);
        AnnotatedTypeBuilder<X> builder=
                new AnnotatedTypeBuilder<X>().readFromType(at);
        builder.addToClass(transactionalAnnotation).
                addToClass(requestScopedAnnotation) ;
        Inject injectAnnotaiont= AnnotationInstanceProvider.of(Inject.class);
        for(AnnotatedField<? super X> field:at.getFields()) {
            if ((field.isAnnotationPresent(EJB.class) ||
                    field.isAnnotationPresent(PersistenceContext.class))&&
                    ! field.isAnnotationPresent(Inject.class)) {
                builder.addToField(field, injectAnnotaiont);
            }
        }
        // Wrapper anstatt der tatsächlichen Bean setzen
        pat.setAnnotatedType(builder.create());
    }
}
