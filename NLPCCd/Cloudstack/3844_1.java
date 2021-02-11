//,temp,sample_1557.java,2,11,temp,sample_8009.java,2,11
//,2
public class xxx {
public static Properties loadProperties() throws ConfigurationException {
Properties properties = new Properties();
final File file = PropertiesUtil.findConfigFile("agent.properties");
if (file == null) {
throw new ConfigurationException("Unable to find agent.properties.");
}


log.info("agent properties found at");
}

};