//,temp,sample_48.java,2,8,temp,sample_49.java,2,9
//,3
public class xxx {
public ExecutionController() throws IOException {
String executionContextConfigurationFile = System.getProperty(CONF_PROPERTY, "").trim();
Preconditions.checkArgument(!executionContextConfigurationFile.isEmpty(), CONF_PROPERTY + " is required");


log.info("reading configuration from file");
}

};