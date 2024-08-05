package sn.lord.configs.configfile;

import sn.lord.configs.configfile.enums.ConfigurationFile;
import sn.lord.configs.configfile.exceptions.ConfigurationFileAlreadyAdded;
import sn.lord.configs.configfile.exceptions.ConfigurationFileNotFound;

import java.util.HashMap;
import java.io.File;
import java.util.Properties;
import java.io.FileInputStream;


public class ConfigurationFileManagerImpl implements ConfigurationFileManager {
    private static ConfigurationFileManagerImpl instance;
    private final HashMap<ConfigurationFile, Properties> configFilesProperties;
    protected ConfigurationFileManagerImpl() {
        configFilesProperties = new HashMap<>();
    }

    public static ConfigurationFileManagerImpl getInstance() {
        if (instance == null) instance = new ConfigurationFileManagerImpl();
        return instance;
    }

    public void addConfigFile (ConfigurationFile configurationFile) throws ConfigurationFileAlreadyAdded {
        if ( configFilesProperties.containsKey(configurationFile) ) throw new ConfigurationFileAlreadyAdded(configurationFile.name()+" already added");
        Properties configProperties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(getAbsolutePath()+"/"+configurationFile.getPath());
            configProperties.load(fileInputStream);
            fileInputStream.close();
            configFilesProperties.put(configurationFile, configProperties);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getConfigValue(ConfigurationFile configurationFile, String key) throws ConfigurationFileNotFound {
        if ( !configFilesProperties.containsKey(configurationFile) ) throw new ConfigurationFileNotFound(configurationFile.name()+" not yet add");
        return configFilesProperties.get(configurationFile).getProperty(key);
    }

    String getAbsolutePath() {
        return new File(".").getAbsolutePath();
    }

}
