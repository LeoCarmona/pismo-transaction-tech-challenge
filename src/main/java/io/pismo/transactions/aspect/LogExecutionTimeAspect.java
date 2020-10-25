package io.pismo.transactions.aspect;

import ch.qos.logback.classic.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author leonardo.carmona (https://www.linkedin.com/in/leonardo-carmona/)
 */
@Aspect
@Component
public class LogExecutionTimeAspect {

    private static final Logger log = (Logger) LoggerFactory.getLogger(LogExecutionTimeAspect.class);

    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(final ProceedingJoinPoint jointPoint) throws Throwable {
        final long started = System.nanoTime();
        log.debug(String.format("%s - STARTED args: %s", jointPoint.getSignature(), Arrays.toString(jointPoint.getArgs())));

        final Object result;

        try {
            result = jointPoint.proceed();
        } catch (Throwable t) {
            log.error(String.format("%s - An exception occurred: %s", jointPoint.getSignature(), t.getMessage()), t);
            throw t;
        } finally {
            final long finished = System.nanoTime() - started;
            final long ms = finished / 1000000L;
            final long module = finished % 1000000L;
            final long us = module / 1000L;
            final long ns = module % 1000L;

            log.info(String.format("%s - FINISHED (took: %s ms, %s us and %s ns)", jointPoint.getSignature(), ms, us, ns));
        }

        return result;
    }

}
