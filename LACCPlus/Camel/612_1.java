//,temp,MongoDbEndpoint.java,329,335,temp,GridFsEndpoint.java,126,132
//,3
public class xxx {
    private MongoClient resolveMongoConnection() {
        MongoClient mongoClient = CamelContextHelper.mandatoryLookup(getCamelContext(), connectionBean, MongoClient.class);
        LOG.debug("Resolved the connection provided by {} context reference as {}", connectionBean,
                mongoConnection);

        return mongoClient;
    }

};