//,temp,ApiConnectorMockito.java,96,100,temp,ApiConnectorMockito.java,66,70
//,3
public class xxx {
    @Override
    public void delete(Class<? extends ApiObjectBase> arg0, String arg1) throws IOException {
        s_logger.debug("create " + arg0.getName() + " id: " + arg1);
        _spy.delete(arg0, arg1);
    }

};