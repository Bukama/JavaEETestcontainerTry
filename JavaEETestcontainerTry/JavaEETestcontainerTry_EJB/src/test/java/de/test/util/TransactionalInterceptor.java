package de.test.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Stack;


@Interceptor
@Transactional
public class TransactionalInterceptor {
    @Inject
    @PersistenceContext
    EntityManager em;


    private static final Logger logger = LogManager.getLogger(TransactionalInterceptor.class);

    private static final ThreadLocal<Stack<String>> stackHolder=
            new ThreadLocal<Stack<String>>() {
                @Override
                protected Stack<String> initialValue() {
                    return new Stack<String>();
                }
            };
    @AroundInvoke
    public Object manageTransaction(InvocationContext ctx)
            throws Exception {
        TransactionAttribute transaction =
                ctx.getTarget().getClass().getAnnotation(
                        TransactionAttribute.class);
        logger.debug("EntityManager in interceptor {}", em);
        if (!em.getTransaction().isActive()) {
            logger.debug("Transaction is not active. " +
                    "A new transaction will be activated");
            em.getTransaction().begin();
        }
        String clazzName = ctx.getMethod().getDeclaringClass().getName();
        stackHolder.get().push(clazzName + "." + ctx.getMethod().getName());
        Object result = ctx.proceed();
        if (em.isOpen() && em.getTransaction().isActive() &&
                stackHolder.get().isEmpty()) {
            logger.debug(
                    "Transaction is active and will be commited for {}.{}",
                    clazzName, ctx.getMethod().getName());
            em.getTransaction().commit();
        }
        return result;
    }
}
