package com.cambridgeassociates.bmc.analytics;

import com.cambridgeassociates.bmc.analytics.templates.AnalyticTemplate;
import com.cambridgeassociates.bmc.analytics.templates.exceptions.TemplateLoadingException;
import com.cambridgeassociates.bmc.analytics.templates.exceptions.TemplateNotRegisteredException;
import com.cambridgeassociates.bmc.analytics.templates.exceptions.TemplateRegistrationException;
import com.cambridgeassociates.bmc.entities.Template;
import com.cambridgeassociates.bmc.repositories.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AnalyticsCatalog {

    @Value("${analytics.jars.path}")
    private String jarsPath;
    private TemplateRepository templateRepository;
    private Map<String, AnalyticTemplate> templates = new HashMap<>();

    @Autowired
    public AnalyticsCatalog(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @PostConstruct
    public void createJarsDestination() {
        new File(jarsPath).mkdirs();
    }

    public void register(String name, String className, MultipartFile multiPartFile) throws TemplateRegistrationException, TemplateLoadingException {
        File localFile = saveToFile(name, multiPartFile);
        loadTemplateFromJar(name, className, localFile);
        saveTemplateData(name, className, localFile);
    }

    private void addToLoadedTemplates(String name, AnalyticTemplate template) {
        templates.put(name, template);
    }

    private void saveTemplateData(String name, String className, File localFile) {
        Template templateEntity = new Template(name, className, localFile.getPath());
        templateRepository.save(templateEntity);
    }

    public AnalyticTemplate get(String name) throws TemplateNotRegisteredException, TemplateLoadingException {
        return getLoadedTemplate(name).orElseGet(() -> loadRegisteredTemplate(name));
    }

    public void clearCache() {
        templates.clear();
    }

    private AnalyticTemplate loadTemplateFromJar(String templateName, String className, File jarFile) throws TemplateLoadingException {
        try {
            URLClassLoader jarClassLoader = new URLClassLoader(new URL[]{jarFile.toURI().toURL()}, this.getClass().getClassLoader());
            Class classToLoad = Class.forName(className, true, jarClassLoader);
            Object instance = classToLoad.newInstance();
            AnalyticTemplate template = (AnalyticTemplate) instance;
            addToLoadedTemplates(templateName, template);
            return template;
        } catch (Exception ex) {
            throw new TemplateLoadingException(templateName, ex);
        }
    }

    private AnalyticTemplate loadTemplateFromJar(String templateName, String className, String jarPath) throws TemplateLoadingException {
        return loadTemplateFromJar(templateName, className, new File(jarPath));
    }

    private File saveToFile(String templateName, MultipartFile file) throws TemplateRegistrationException {
        try {
            String filename = file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            File analyticsJar = new File(jarsPath, filename);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(analyticsJar));
            stream.write(bytes);
            stream.close();
            return analyticsJar;
        } catch (Exception ex) {
            throw new TemplateRegistrationException(templateName, ex);
        }
    }

    private Optional<AnalyticTemplate> getLoadedTemplate(String name) {
        return Optional.ofNullable(templates.get(name));
    }

    private AnalyticTemplate loadRegisteredTemplate(String name) throws TemplateNotRegisteredException, TemplateLoadingException {
        Template template = templateRepository.findByName(name).orElseThrow(() -> new TemplateNotRegisteredException(name));
        return loadTemplateFromJar(template.getName(), template.getClosureClass(), template.getJarPath());
    }
}
