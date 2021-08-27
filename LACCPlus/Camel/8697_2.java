//,temp,LevelDBAggregationRepository.java,282,300,temp,LevelDBAggregationRepository.java,135,153
//,3
public class xxx {
    @Override
    public Exchange get(final CamelContext camelContext, final String key) {
        Exchange answer = null;

        try {
            byte[] lDbKey = keyBuilder(repositoryName, key);
            LOG.trace("Getting key index {}", key);
            byte[] rc = levelDBFile.getDb().get(lDbKey);

            if (rc != null) {
                answer = codec.unmarshallExchange(camelContext, new Buffer(rc));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error getting key " + key + " from repository " + repositoryName, e);
        }

        LOG.debug("Getting key  [{}] -> {}", key, answer);
        return answer;
    }

};