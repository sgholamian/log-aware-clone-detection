//,temp,ApiConnectorMockito.java,60,64,temp,ApiConnectorMockito.java,54,58
//,3
public class xxx {
    @Override
    public void delete(ApiObjectBase arg0) throws IOException {
        s_logger.debug("delete " + arg0.getClass().getName() + " id: " + arg0.getUuid());
        _spy.delete(arg0);
    }

};