//,temp,Kinesis2Consumer.java,53,99,temp,Ddb2StreamConsumer.java,54,80
//,3
public class xxx {
    @Override
    protected int poll() throws Exception {
        String shardIterator = getShardIterator();

        if (shardIterator == null) {
            // probably closed. Returning 0 as nothing was processed

            return 0;
        }

        GetRecordsRequest req = GetRecordsRequest
                .builder()
                .shardIterator(shardIterator)
                .limit(getEndpoint()
                        .getConfiguration()
                        .getMaxResultsPerRequest())
                .build();
        GetRecordsResponse result = getClient().getRecords(req);

        Queue<Exchange> exchanges = createExchanges(result.records());
        int processedExchangeCount = processBatch(CastUtils.cast(exchanges));

        // May cache the last successful sequence number, and pass it to the
        // getRecords request. That way, on the next poll, we start from where
        // we left off, however, I don't know what happens to subsequent
        // exchanges when an earlier exchange fails.

        currentShardIterator = result.nextShardIterator();
        if (isShardClosed) {
            switch (getEndpoint().getConfiguration().getShardClosed()) {
                case ignore:
                    LOG.warn("The shard {} is in closed state", currentShardIterator);
                    break;
                case silent:
                    break;
                case fail:
                    LOG.info("Shard Iterator reaches CLOSE status:{} {}", getEndpoint().getConfiguration().getStreamName(),
                            getEndpoint().getConfiguration().getShardId());
                    throw new ReachedClosedStatusException(
                            getEndpoint().getConfiguration().getStreamName(), getEndpoint().getConfiguration().getShardId());
                default:
                    throw new IllegalArgumentException("Unsupported shard closed strategy");
            }
        }

        return processedExchangeCount;
    }

};