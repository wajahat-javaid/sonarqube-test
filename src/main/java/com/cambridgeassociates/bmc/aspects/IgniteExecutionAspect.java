package com.cambridgeassociates.bmc.aspects;

import com.cambridgeassociates.bmc.analytics.templates.AnalyticTemplate;
import com.cambridgeassociates.bmc.services.IgniteExecutionService;
import org.apache.ignite.Ignite;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class IgniteExecutionAspect {

    @Pointcut("execution(public * com.cambridgeassociates..execute(com.cambridgeassociates..AnalyticTemplate, ..))")
    public void templateExecution() {
    }

    @SuppressWarnings("unchecked")
    @Around("templateExecution()")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        Ignite ignite = ((IgniteExecutionService) pjp.getThis()).getIgnite();
        AnalyticTemplate template = (AnalyticTemplate) pjp.getArgs()[0];
        template.before(ignite);
        Map<String, Object> output = (Map<String, Object>) pjp.proceed();
        template.after(ignite);
        return output;
    }

    private Map<String, Object> metricsInitialValues() {
        Map<String, Object> initialValues = new HashMap<>();
        initialValues.put("executionTime", System.currentTimeMillis());
        return initialValues;
    }

    private Map<String, Object> calculateMetrics(Map<String, Object> initialValues) {
        initialValues.put("executionTime", initialValues.get("executionTime"));
        return initialValues;
    }

}
