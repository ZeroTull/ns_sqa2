package com.pinger.automation.utils;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ConfigLoader {
    private static final Config config = ConfigFactory.load();

    public static String getString(String key) {
        return config.hasPath(key) ? config.getString(key) : "";
    }
}
