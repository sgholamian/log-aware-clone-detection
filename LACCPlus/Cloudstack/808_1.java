//,temp,ApiConnectorMockito.java,96,100,temp,ApiConnectorMockito.java,66,70
//,3
public class xxx {
    @Override
    public String findByName(Class<? extends ApiObjectBase> arg0, List<String> arg1) throws IOException {
        s_logger.debug("find " + arg0.getName() + " name: " + arg1);
        return _mock.findByName(arg0, arg1);
    }

};