//,temp,AgentRoutingResource.java,302,309,temp,AgentStorageResource.java,101,109
//,2
public class xxx {
    @Override
    public boolean configure(String name, Map<String, Object> params) throws ConfigurationException {
        if (!super.configure(name, params)) {
            s_logger.warn("Base class was unable to configure");
            return false;
        }
        return true;
    }

};