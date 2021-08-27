//,temp,sample_8361.java,2,13,temp,sample_8362.java,2,16
//,3
public class xxx {
protected void initJMXAgent() throws Exception {
CamelJMXAgentDefinition camelJMXAgent = getCamelJMXAgent();
boolean disabled = false;
if (camelJMXAgent != null) {
disabled = camelJMXAgent.getDisabled() != null && CamelContextHelper.parseBoolean(getContext(), camelJMXAgent.getDisabled());
}
if (disabled) {


log.info("jmxagent disabled");
}
}

};