//,temp,ApiConnectorMockito.java,114,118,temp,ApiConnectorMockito.java,90,94
//,3
public class xxx {
    @Override
    public <T extends ApiPropertyBase> List<? extends ApiObjectBase> getObjects(Class<? extends ApiObjectBase> arg0, List<ObjectReference<T>> arg1) throws IOException {
        s_logger.debug("getObjects" + arg0.getName());
        return _mock.getObjects(arg0, arg1);
    }

};