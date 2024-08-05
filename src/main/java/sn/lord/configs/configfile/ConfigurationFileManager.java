package sn.lord.configs.configfile;

import sn.lord.configs.configfile.enums.ConfigurationFile;
import sn.lord.configs.configfile.exceptions.ConfigurationFileAlreadyAdded;
import sn.lord.configs.configfile.exceptions.ConfigurationFileNotFound;

public interface ConfigurationFileManager {
    void addConfigFile (ConfigurationFile configurationFile) throws ConfigurationFileAlreadyAdded;
    String getConfigValue(ConfigurationFile configurationFile, String key) throws ConfigurationFileNotFound;
}
