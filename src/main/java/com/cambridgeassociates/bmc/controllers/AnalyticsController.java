package com.cambridgeassociates.bmc.controllers;

import com.cambridgeassociates.bmc.analytics.templates.exceptions.TemplateLoadingException;
import com.cambridgeassociates.bmc.analytics.templates.exceptions.TemplateNotRegisteredException;
import com.cambridgeassociates.bmc.analytics.templates.exceptions.TemplateRegistrationException;
import com.cambridgeassociates.bmc.controllers.dto.ExecutionRequest;
import com.cambridgeassociates.bmc.controllers.dto.ExecutionResponse;
import com.cambridgeassociates.bmc.entities.Execution;
import com.cambridgeassociates.bmc.repositories.ExecutionRepository;
import com.cambridgeassociates.bmc.services.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/templates")
public class AnalyticsController {

    private AnalyticsService analyticsService;
    private ExecutionRepository executionRepository;

    @Autowired
    public AnalyticsController(AnalyticsService analyticsService, ExecutionRepository executionRepository) {
        this.analyticsService = analyticsService;
        this.executionRepository = executionRepository;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestParam("templateName") String templateName,
                         @RequestParam("className") String className,
                         @RequestParam("file") MultipartFile file) throws TemplateRegistrationException, TemplateLoadingException {
        analyticsService.register(templateName, className, file);
    }

    @RequestMapping(value = "/execute", method = RequestMethod.POST)
    public ExecutionResponse execute(@RequestBody(required = true) ExecutionRequest request) throws TemplateNotRegisteredException, TemplateLoadingException {
        ExecutionResponse response = analyticsService.execute(request);
        analyticsService.saveExecutionData(request, response);
        return response;
    }

    @RequestMapping(value = "/clearCache", method = RequestMethod.POST)
    public void clearCache() {
        analyticsService.clearCache();
    }

    @RequestMapping(value = "/executions")
    public Iterable<Execution> getExecutions() {
        return executionRepository.findAll();
    }

    @RequestMapping(value = "/executions/{id}")
    public Execution getExecutions(@PathVariable("id") String id) {
        return executionRepository.findOne(id);
    }
}
