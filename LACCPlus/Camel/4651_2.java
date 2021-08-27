//,temp,LevelDBAggregationRepository.java,155,187,temp,LevelDBAggregationRepository.java,104,133
//,3
public class xxx {
    @Override
    public Exchange add(final CamelContext camelContext, final String key, final Exchange exchange) {
        LOG.debug("Adding key [{}] -> {}", key, exchange);
        try {
            byte[] lDbKey = keyBuilder(repositoryName, key);
            final Buffer exchangeBuffer = codec.marshallExchange(camelContext, exchange, allowSerializedHeaders);

            byte[] rc = null;
            if (isReturnOldExchange()) {
                rc = levelDBFile.getDb().get(lDbKey);
            }

            LOG.trace("Adding key index {} for repository {}", key, repositoryName);
            levelDBFile.getDb().put(lDbKey, exchangeBuffer.toByteArray(), levelDBFile.getWriteOptions());
            LOG.trace("Added key index {}", key);

            if (rc == null) {
                return null;
            }

            // only return old exchange if enabled
            if (isReturnOldExchange()) {
                return codec.unmarshallExchange(camelContext, new Buffer(rc));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error adding to repository " + repositoryName + " with key " + key, e);
        }

        return null;
    }

};