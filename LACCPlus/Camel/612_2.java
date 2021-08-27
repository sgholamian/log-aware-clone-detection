//,temp,MongoDbEndpoint.java,329,335,temp,GridFsEndpoint.java,126,132
//,3
public class xxx {
    @Override
    protected void doInit() throws Exception {
        mongoConnection = CamelContextHelper.mandatoryLookup(getCamelContext(), connectionBean, MongoClient.class);
        LOG.debug("Resolved the connection with the name {} as {}", connectionBean, mongoConnection);
        setWriteReadOptionsOnConnection();
        super.doInit();
    }

};