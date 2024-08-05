package sn.lord;

import sn.lord.configs.configfile.ConfigurationFileManagerImpl;
import sn.lord.configs.configfile.enums.ConfigurationFile;
import sn.lord.configs.configfile.exceptions.ConfigurationFileAlreadyAdded;
import sn.lord.firebase.auth.WithEmailAndPassword;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        try {
            ConfigurationFileManagerImpl.getInstance().addConfigFile(ConfigurationFile.FIREBASE_CONFIGURATION_FILE);
            WithEmailAndPassword.signIn("lord@test.com", "passer");


        } catch (ConfigurationFileAlreadyAdded e) {
            throw new RuntimeException(e);
        }
    }
}