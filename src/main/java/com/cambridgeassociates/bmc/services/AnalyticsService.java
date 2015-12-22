package com.cambridgeassociates.bmc.services;

import com.cambridgeassociates.bmc.analytics.AnalyticsCatalog;
import com.cambridgeassociates.bmc.analytics.templates.AnalyticTemplate;
import com.cambridgeassociates.bmc.analytics.templates.exceptions.ExecutePersistException;
import com.cambridgeassociates.bmc.analytics.templates.exceptions.TemplateLoadingException;
import com.cambridgeassociates.bmc.analytics.templates.exceptions.TemplateNotRegisteredException;
import com.cambridgeassociates.bmc.analytics.templates.exceptions.TemplateRegistrationException;
import com.cambridgeassociates.bmc.controllers.dto.ExecutionRequest;
import com.cambridgeassociates.bmc.controllers.dto.ExecutionResponse;
import com.cambridgeassociates.bmc.entities.Execution;
import com.cambridgeassociates.bmc.repositories.ExecutionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class AnalyticsService {

    private AnalyticsCatalog analyticsCatalog;
    private IgniteExecutionService igniteExecutionService;
    private ExecutionRepository executionRepository;
    private final ObjectMapper mapper;

    @Autowired
    public AnalyticsService(AnalyticsCatalog analyticsCatalog, IgniteExecutionService igniteExecutionService, ExecutionRepository executionRepository, ObjectMapper mapper) {
        this.analyticsCatalog = analyticsCatalog;
        this.igniteExecutionService = igniteExecutionService;
        this.executionRepository = executionRepository;
        this.mapper = mapper;
    }

    public ExecutionResponse execute(ExecutionRequest request) throws TemplateNotRegisteredException, TemplateLoadingException {
        String templateName = request.getName();
        AnalyticTemplate analyticTemplate = analyticsCatalog.get(templateName);
        Map<String, Object> params = request.getParams();
        Map<String, Object> execResult = igniteExecutionService.execute(analyticTemplate, params);
        ExecutionResponse response = createResponse(request, execResult);
        return response;
    }

    public void register(String templateName, String className, MultipartFile file) throws TemplateRegistrationException, TemplateLoadingException {
        analyticsCatalog.register(templateName, className, file);
    }

    private ExecutionResponse createResponse(ExecutionRequest request, Map<String, Object> result) {
        ExecutionResponse response = new ExecutionResponse();
        response.setName(request.getName());
        response.setUniqueId(request.getUniqueId());
        response.setRequestedBy(request.getRequestedBy());
        response.setResult(result);
        return response;
    }

    public void clearCache() {
        analyticsCatalog.clearCache();
    }

    public void saveExecutionData(ExecutionRequest request, ExecutionResponse response) throws ExecutePersistException {
        try {
            Execution executionData = new Execution(request.getUniqueId(), request.getName(), mapper.writeValueAsString(request.getParams()), mapper.writeValueAsString(response.getResult()), mapper.writeValueAsString(response.getMetrics()));
            this.executionRepository.save(executionData);
        } catch (Exception e) {
            throw new ExecutePersistException(request.getName(), e);
        }

    }
}
