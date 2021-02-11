//,temp,ApiConnectorMockito.java,90,94,temp,ApiConnectorMockito.java,84,88
//,2
public class xxx {
    @Override
    public ApiObjectBase findById(Class<? extends ApiObjectBase> arg0, String arg1) throws IOException {
        s_logger.debug("find " + arg0.getName() + " id: " + arg1);
        return _mock.findById(arg0, arg1);
    }

};