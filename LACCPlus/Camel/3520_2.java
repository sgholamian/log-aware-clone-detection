//,temp,CassandraAggregationRepository.java,206,222,temp,CassandraAggregationRepository.java,179,191
//,3
public class xxx {
    @Override
    public Exchange add(CamelContext camelContext, String key, Exchange exchange) {
        final Object[] idValues = getPKValues(key);
        LOGGER.debug("Inserting key {} exchange {}", idValues, exchange);
        try {
            ByteBuffer marshalledExchange = exchangeCodec.marshallExchange(camelContext, exchange, allowSerializedHeaders);
            Object[] cqlParams = concat(idValues, new Object[] { exchange.getExchangeId(), marshalledExchange });
            getSession().execute(insertStatement.bind(cqlParams));
            return exchange;
        } catch (IOException iOException) {
            throw new CassandraAggregationException("Failed to write exchange", exchange, iOException);
        }
    }

};