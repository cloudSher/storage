package com.eternity.storage.config;

import java.io.IOException;
import java.util.Map;

/**
 * Created by cloudsher on 2016/8/10.
 */
public interface ConfigurationProvider {

    void loadConfig(String path) throws IOException;

    Map getConfigMap(String path);
}
