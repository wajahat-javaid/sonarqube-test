package com.cambridgeassociates.bmc.aspects;

import com.cambridgeassociates.bmc.controllers.dto.ExecutionResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class AnalyticsMetricsAspect {

    @Pointcut("execution(public com.cambridgeassociates..ExecutionResponse com.cambridgeassociates..AnalyticsService.execute(com.cambridgeassociates..ExecutionRequest))")
    public void templateExecution() {
    }

    @SuppressWarnings("unchecked")
    @Around("templateExecution()")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        Map<String, Object> initialValues = metricsInitialValues();
        ExecutionResponse response = (ExecutionResponse) pjp.proceed();
        response.setMetrics(calculateMetrics(initialValues));
        return response;
    }

    private Map<String, Object> metricsInitialValues() {
        Map<String, Object> initialValues = new HashMap<>();
        initialValues.put("executionTime", System.currentTimeMillis());
        return initialValues;
    }

    private Map<String, Object> calculateMetrics(Map<String, Object> initialValues) {
        Long startTime = (Long) initialValues.get("executionTime");
        long executionTime = System.currentTimeMillis() - startTime;
        initialValues.put("executionTime", executionTime);
        return initialValues;
    }

}
