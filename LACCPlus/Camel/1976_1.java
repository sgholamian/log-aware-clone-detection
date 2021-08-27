//,temp,LevelDBAggregationRepository.java,240,280,temp,LevelDBAggregationRepository.java,203,238
//,3
public class xxx {
    @Override
    public Set<String> scan(CamelContext camelContext) {
        final Set<String> answer = new LinkedHashSet<>();

        if (!isRunAllowed()) {
            return null;
        }

        DBIterator it = levelDBFile.getDb().iterator();

        String keyBuffer;
        try {
            String prefix = getRepositoryNameCompleted() + '\0';

            for (it.seek(keyBuilder(getRepositoryNameCompleted(), "")); it.hasNext(); it.next()) {
                keyBuffer = asString(it.peekNext().getKey());

                if (!keyBuffer.startsWith(prefix)) {
                    break;
                }
                String exchangeId = keyBuffer.substring(prefix.length());

                LOG.trace("Scan exchangeId [{}]", exchangeId);
                answer.add(exchangeId);
            }
        } finally {
            // Make sure you close the iterator to avoid resource leaks.
            IOHelper.close(it);
        }

        if (answer.isEmpty()) {
            LOG.trace("Scanned and found no exchange to recover.");
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Scanned and found {} exchange(s) to recover (note some of them may already be in progress).",
                        answer.size());
            }
        }
        return answer;

    }

};