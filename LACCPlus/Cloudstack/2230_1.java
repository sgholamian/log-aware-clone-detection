//,temp,ApiConnectorMockito.java,120,124,temp,ApiConnectorMockito.java,84,88
//,3
public class xxx {
    @Override
    public List<? extends ApiObjectBase> list(Class<? extends ApiObjectBase> arg0, List<String> arg1) throws IOException {
        s_logger.debug("list" + arg0.getName());
        return _mock.list(arg0, arg1);
    }

};