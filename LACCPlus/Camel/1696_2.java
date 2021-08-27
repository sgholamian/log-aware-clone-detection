//,temp,Etcd3AggregationRepository.java,318,333,temp,Etcd3AggregationRepository.java,217,231
//,3
public class xxx {
    @Override
    public Exchange recover(CamelContext camelContext, String exchangeId) {
        LOG.trace("Recovering an Exchange with ID {}.", exchangeId);
        CompletableFuture<GetResponse> completableResponse = kvClient
                .get(ByteSequence.from(String.format("%s/%s", persistencePrefixName, exchangeId).getBytes()));
        try {
            GetResponse getResponse = completableResponse.get();
            DefaultExchangeHolder holder
                    = (DefaultExchangeHolder) convertFromEtcd3Format(getResponse.getKvs().get(0).getValue());
            return useRecovery ? unmarshallExchange(camelContext, holder) : null;
        } catch (InterruptedException | ExecutionException | IOException | ClassNotFoundException e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

};