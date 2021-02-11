//,temp,ApiConnectorMockito.java,132,136,temp,ApiConnectorMockito.java,54,58
//,2
public class xxx {
    @Override
    public boolean create(ApiObjectBase arg0) throws IOException {
        s_logger.debug("create " + arg0.getClass().getName() + " id: " + arg0.getUuid());
        return _spy.create(arg0);
    }

};