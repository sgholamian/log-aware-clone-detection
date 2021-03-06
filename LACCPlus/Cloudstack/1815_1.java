//,temp,LocalNfsSecondaryStorageResourceTest.java,123,140,temp,HypervDirectConnectResourceTest.java,622,639
//,3
public class xxx {
    public static Properties loadProperties() throws ConfigurationException {
        Properties properties = new Properties();
        final File file = PropertiesUtil.findConfigFile("agent.properties");
        if (file == null) {
            throw new ConfigurationException("Unable to find agent.properties.");
        }

        s_logger.info("agent.properties found at " + file.getAbsolutePath());

        try(FileInputStream fs = new FileInputStream(file);) {
            properties.load(fs);
        } catch (final FileNotFoundException ex) {
            throw new CloudRuntimeException("Cannot find the file: " + file.getAbsolutePath(), ex);
        } catch (final IOException ex) {
            throw new CloudRuntimeException("IOException in reading " + file.getAbsolutePath(), ex);
        }
        return properties;
    }

};