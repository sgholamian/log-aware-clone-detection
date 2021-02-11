//,temp,ApiConnectorMockito.java,120,124,temp,ApiConnectorMockito.java,84,88
//,3
public class xxx {
    @Override
    public ApiObjectBase findByFQN(Class<? extends ApiObjectBase> arg0, String arg1) throws IOException {
        s_logger.debug("find " + arg0.getName() + " name: " + arg1);
        return _mock.findByFQN(arg0, arg1);
    }

};