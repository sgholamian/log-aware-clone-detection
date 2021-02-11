//,temp,LocalNfsSecondaryStorageResourceTest.java,123,140,temp,AgentShell.java,216,232
//,3
public class xxx {
    void loadProperties() throws ConfigurationException {
        final File file = PropertiesUtil.findConfigFile("agent.properties");

        if (null == file) {
            throw new ConfigurationException("Unable to find agent.properties.");
        }

        s_logger.info("agent.properties found at " + file.getAbsolutePath());

        try {
            PropertiesUtil.loadFromFile(_properties, file);
        } catch (final FileNotFoundException ex) {
            throw new CloudRuntimeException("Cannot find the file: " + file.getAbsolutePath(), ex);
        } catch (final IOException ex) {
            throw new CloudRuntimeException("IOException in reading " + file.getAbsolutePath(), ex);
        }
    }

};