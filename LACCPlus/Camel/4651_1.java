//,temp,LevelDBAggregationRepository.java,155,187,temp,LevelDBAggregationRepository.java,104,133
//,3
public class xxx {
    @Override
    public void remove(final CamelContext camelContext, final String key, final Exchange exchange) {
        LOG.debug("Removing key [{}]", key);

        try {
            byte[] lDbKey = keyBuilder(repositoryName, key);
            final String exchangeId = exchange.getExchangeId();
            final Buffer exchangeBuffer = codec.marshallExchange(camelContext, exchange, allowSerializedHeaders);

            // remove the exchange
            byte[] rc = levelDBFile.getDb().get(lDbKey);

            if (rc != null) {
                WriteBatch batch = levelDBFile.getDb().createWriteBatch();
                try {
                    batch.delete(lDbKey);
                    LOG.trace("Removed key index {} -> {}", key, new Buffer(rc));

                    // add exchange to confirmed index
                    byte[] confirmedLDBKey = keyBuilder(getRepositoryNameCompleted(), exchangeId);
                    batch.put(confirmedLDBKey, exchangeBuffer.toByteArray());
                    LOG.trace("Added confirm index {} for repository {}", exchangeId, getRepositoryNameCompleted());

                    levelDBFile.getDb().write(batch, levelDBFile.getWriteOptions());
                } finally {
                    batch.close();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Error removing key " + key + " from repository " + repositoryName, e);
        }
    }

};