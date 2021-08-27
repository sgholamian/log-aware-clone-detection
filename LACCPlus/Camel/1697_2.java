//,temp,Etcd3AggregationRepository.java,448,463,temp,Etcd3AggregationRepository.java,190,215
//,3
public class xxx {
    @Override
    public Set<String> scan(CamelContext camelContext) {
        if (useRecovery) {
            LOG.trace("Scanning for exchanges to recover in {} context", camelContext.getName());
            CompletableFuture<GetResponse> completableGetResponse = kvClient.get(
                    ByteSequence.from(persistencePrefixName.getBytes()),
                    GetOption.newBuilder().withPrefix(ByteSequence.from(persistencePrefixName.getBytes())).build());
            try {
                GetResponse getResponse = completableGetResponse.get();
                Set<String> keys = new TreeSet<>();
                getResponse.getKvs().forEach(kv -> keys.add(new String(kv.getKey().getBytes())));
                Set<String> scanned = Collections.unmodifiableSet(keys);
                LOG.trace("Found {} keys for exchanges to recover in {} context", scanned.size(),
                        camelContext.getName());
                return scanned;
            } catch (InterruptedException | ExecutionException e) {
                LOG.error(e.getMessage(), e);
                throw new RuntimeException(e.getMessage(), e);
            }
        } else {
            LOG.warn(
                    "What for to run recovery scans in {} context while prefix {} is running in non-recoverable aggregation repository mode?!",
                    camelContext.getName(), prefixName);
            return Collections.emptySet();
        }
    }

};