//,temp,ApiConnectorMockito.java,132,136,temp,ApiConnectorMockito.java,126,130
//,2
public class xxx {
    @Override
    public boolean read(ApiObjectBase arg0) throws IOException {
        s_logger.debug("read " + arg0.getClass().getName() + " id: " + arg0.getUuid());
        return _mock.read(arg0);
    }

};