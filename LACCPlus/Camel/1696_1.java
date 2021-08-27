//,temp,Etcd3AggregationRepository.java,318,333,temp,Etcd3AggregationRepository.java,217,231
//,3
public class xxx {
    @Override
    public Exchange get(CamelContext camelContext, String key) {
        CompletableFuture<GetResponse> completableResponse = kvClient
                .get(ByteSequence.from(String.format("%s/%s", prefixName, key).getBytes()));
        try {
            GetResponse getResponse = completableResponse.get();
            DefaultExchangeHolder holder = null;
            if (!getResponse.getKvs().isEmpty()) {
                holder = (DefaultExchangeHolder) convertFromEtcd3Format(getResponse.getKvs().get(0).getValue());
            }
            return unmarshallExchange(camelContext, holder);
        } catch (InterruptedException | ExecutionException | IOException | ClassNotFoundException e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

};