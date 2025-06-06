package com.self.codemaker.config;

import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.kie.spring.KModuleBeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

/**
 * @author huangchangjun
 * @date 2024/7/28
 */
@Slf4j
@Configuration
public class DroolsConfig {

    //  制定规则文件路径
    private static final String RULES_PATH = "rules/";

    private final KieServices kieServices = KieServices.Factory.get();

//    @Bean
//    public KieContainer kieContainer() {
//        log.info("Creating KieContainer with KieFileSystem ,kieServices = {}",kieServices);
//        //  获取kie容器对象
//        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
//        kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_DRL));
//
//        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
//        kieBuilder.buildAll();
//
//        KieModule kieModule = kieBuilder.getKieModule();
//        KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());
//        return kieContainer;
//    }

    @Bean
    @ConditionalOnMissingBean
    public KieFileSystem kieFileSystem() throws IOException {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        ResourcePatternResolver resourcePatternResolver =
                new PathMatchingResourcePatternResolver();
        Resource[] files = resourcePatternResolver.getResources("classpath*:" + RULES_PATH + "*.*");
        String path = null;
        for (Resource file : files) {
            path = RULES_PATH + file.getFilename();
            kieFileSystem.write(ResourceFactory.newClassPathResource(path, "UTF-8"));
        }
        return kieFileSystem;
    }

    @Bean
    @ConditionalOnMissingBean
    public KieContainer kieContainer() throws IOException {
        KieRepository kieRepository = kieServices.getRepository();
        kieRepository.addKieModule(kieRepository::getDefaultReleaseId);
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem());
        kieBuilder.buildAll();
        return kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
    }

    @Bean
    @ConditionalOnMissingBean
    public KieBase kieBase() throws IOException {
        return kieContainer().getKieBase();
    }

    @Bean
    @ConditionalOnMissingBean
    public KModuleBeanFactoryPostProcessor kiePostProcessor() {
        return new KModuleBeanFactoryPostProcessor();
    }
}
