//,temp,LevelDBAggregationRepository.java,240,280,temp,LevelDBAggregationRepository.java,203,238
//,3
public class xxx {
    @Override
    public Set<String> getKeys() {
        final Set<String> keys = new LinkedHashSet<>();

        // interval task could potentially be running while we are shutting down so check for that
        if (!isRunAllowed()) {
            return null;
        }

        DBIterator it = levelDBFile.getDb().iterator();

        String keyBuffer;
        try {
            String prefix = repositoryName + '\0';
            for (it.seek(keyBuilder(repositoryName, "")); it.hasNext(); it.next()) {
                if (!isRunAllowed()) {
                    break;
                }
                keyBuffer = asString(it.peekNext().getKey());

                if (!keyBuffer.startsWith(prefix)) {
                    break;
                }

                String key = keyBuffer.substring(prefix.length());

                LOG.trace("getKey [{}]", key);
                keys.add(key);
            }
        } finally {
            // Make sure you close the iterator to avoid resource leaks.
            IOHelper.close(it);
        }

        return Collections.unmodifiableSet(keys);
    }

};