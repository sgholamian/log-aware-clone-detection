//,temp,Athena2Producer.java,332,341,temp,Athena2Producer.java,321,330
//,2
public class xxx {
    private String determineDatabase(final Exchange exchange) {
        String database = exchange.getIn().getHeader(Athena2Constants.DATABASE, String.class);

        if (ObjectHelper.isEmpty(database)) {
            database = getConfiguration().getDatabase();
            LOG.trace("AWS Athena database is missing, using default one [{}]", database);
        }

        return database;
    }

};