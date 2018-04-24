package services.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class RepositoryAspect {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Pointcut("within(@org.springframework.stereotype.Repository *)")
    public void repositoryClassMethods() {};

   /* @Pointcut("execution(* services.UserService*(..))")
    public void meth() {};

    @Pointcut("@target(org.springframework.stereotype.Repository)")
    public void some() {};

    @After("some()")
    public Object method(){return new Object();}

    @After("some()")
    public void method2(){}*/

    @Around("repositoryClassMethods()")
    public Object measureMethodExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        Object retval = null;
        System.out.println("ura");
        try {
            logger.debug("Before method: " + methodName + "executed");
            long start = System.nanoTime();
            retval = pjp.proceed();
            long end = System.nanoTime();
            logger.info("After method: " + methodName + " executed");
            if (retval != null) {
                logger.info("Method " + methodName + " return " + retval.getClass().getName() + " instance");
            }

            logger.info("Execution of " + methodName + " took " +
                    TimeUnit.NANOSECONDS.toMillis(end - start) + " ms");
            System.out.println("end");
        } catch (Throwable e){
            logger.error("Method : " + methodName + " was failed");
            logger.error("Method throw exception : " + e.getMessage());
            logger.error("Exception",e);
            System.out.println("save god");

            throw e;
        }
        return retval;
    }
}
