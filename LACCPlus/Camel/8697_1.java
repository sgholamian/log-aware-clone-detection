//,temp,LevelDBAggregationRepository.java,282,300,temp,LevelDBAggregationRepository.java,135,153
//,3
public class xxx {
    @Override
    public Exchange recover(CamelContext camelContext, final String exchangeId) {
        Exchange answer = null;

        try {
            byte[] completedLDBKey = keyBuilder(getRepositoryNameCompleted(), exchangeId);

            byte[] rc = levelDBFile.getDb().get(completedLDBKey);

            if (rc != null) {
                answer = codec.unmarshallExchange(camelContext, new Buffer(rc));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error recovering exchangeId " + exchangeId + " from repository " + repositoryName, e);
        }

        LOG.debug("Recovering exchangeId [{}] -> {}", exchangeId, answer);
        return answer;
    }

};