package sn.lord.configs.configfile.enums;

public enum ConfigurationFile {
    FIREBASE_CONFIGURATION_FILE("firebase.config.properties");

    private final String path;
    ConfigurationFile(String path) {
        this.path = "src/main/resources/" + path;
    }

    public String getPath() {
        return path;
    }
}
