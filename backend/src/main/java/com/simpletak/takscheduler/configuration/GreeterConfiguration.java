package com.simpletak.takscheduler.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(Greeter.class)
@EnableConfigurationProperties(GreeterProperties.class)
public class GreeterConfiguration {

    @Autowired
    private GreeterProperties greeterProperties;

    @Bean
    @ConditionalOnMissingBean
    public GreetingConfig greeterConfig() {

        String userName = greeterProperties.getUserName() == null
                ? System.getProperty("username")
                : greeterProperties.getUserName();
        String name = greeterProperties.getName() == null
                ? System.getProperty("name")
                : greeterProperties.getName();


        GreetingConfig greetingConfig = new GreetingConfig();
        greetingConfig.put(GreetingConfig.GreetingConfigType.Username, userName);
        greetingConfig.put(GreetingConfig.GreetingConfigType.Name, name);
        return greetingConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    public Greeter greeter(GreetingConfig greetingConfig) {
        return new Greeter(greetingConfig);
    }
}