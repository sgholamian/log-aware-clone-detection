//,temp,ApiConnectorMockito.java,132,136,temp,ApiConnectorMockito.java,54,58
//,2
public class xxx {
    @Override
    public boolean update(ApiObjectBase arg0) throws IOException {
        s_logger.debug("update " + arg0.getClass().getName() + " id: " + arg0.getUuid());
        return _spy.update(arg0);
    }

};