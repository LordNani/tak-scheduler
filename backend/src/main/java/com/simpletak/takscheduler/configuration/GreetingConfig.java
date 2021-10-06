package com.simpletak.takscheduler.configuration;

import java.util.HashMap;

public class GreetingConfig extends HashMap<GreetingConfig.GreetingConfigType, String> {

    enum GreetingConfigType {
        Username,
        Name,
        Version
    }
}
