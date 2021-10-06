package com.simpletak.takscheduler.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Greeter {
    GreetingConfig config;

    Greeter(GreetingConfig config){
        this.config = config;
    }

    public String greet(){
        StringBuilder sb = new StringBuilder();
        sb
                .append("Hello, ")
                .append(config.get(GreetingConfig.GreetingConfigType.Username))
                .append("\nBut we know that your name is ")
                .append(config.get(GreetingConfig.GreetingConfigType.Name));
        return sb.toString();
    }
}
