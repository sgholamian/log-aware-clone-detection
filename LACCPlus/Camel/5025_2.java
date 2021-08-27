//,temp,Kinesis2Consumer.java,53,99,temp,Ddb2StreamConsumer.java,54,80
//,3
public class xxx {
    @Override
    protected int poll() throws Exception {
        GetRecordsResponse result;
        try {
            GetRecordsRequest.Builder req
                    = GetRecordsRequest.builder().shardIterator(shardIteratorHandler.getShardIterator(null))
                            .limit(getEndpoint().getConfiguration().getMaxResultsPerRequest());
            result = getClient().getRecords(req.build());
        } catch (ExpiredIteratorException e) {
            LOG.warn("Expired Shard Iterator, attempting to resume from {}", lastSeenSequenceNumber, e);
            GetRecordsRequest.Builder req
                    = GetRecordsRequest.builder().shardIterator(shardIteratorHandler.getShardIterator(lastSeenSequenceNumber))
                            .limit(getEndpoint().getConfiguration().getMaxResultsPerRequest());
            result = getClient().getRecords(req.build());
        }
        List<Record> records = result.records();

        Queue<Exchange> exchanges = createExchanges(records, lastSeenSequenceNumber);
        int processedExchangeCount = processBatch(CastUtils.cast(exchanges));

        shardIteratorHandler.updateShardIterator(result.nextShardIterator());
        if (!records.isEmpty()) {
            lastSeenSequenceNumber = records.get(records.size() - 1).dynamodb().sequenceNumber();
        }

        return processedExchangeCount;
    }

};