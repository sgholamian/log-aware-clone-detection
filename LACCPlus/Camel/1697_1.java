//,temp,Etcd3AggregationRepository.java,448,463,temp,Etcd3AggregationRepository.java,190,215
//,3
public class xxx {
    @Override
    public Set<String> getKeys() {
        CompletableFuture<GetResponse> completableGetResponse = kvClient.get(ByteSequence.from(prefixName.getBytes()),
                GetOption.newBuilder().withRange(ByteSequence.from(prefixName.getBytes())).build());
        Set<String> scanned = Collections.unmodifiableSet(new TreeSet<>());
        try {
            GetResponse getResponse = completableGetResponse.get();
            Set<String> keys = new TreeSet<>();
            getResponse.getKvs().forEach(kv -> keys.add(new String(kv.getKey().getBytes())));
            scanned = Collections.unmodifiableSet(keys);
        } catch (InterruptedException | ExecutionException e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
        return scanned;
    }

};