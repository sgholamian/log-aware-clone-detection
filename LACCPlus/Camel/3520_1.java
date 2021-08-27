//,temp,CassandraAggregationRepository.java,206,222,temp,CassandraAggregationRepository.java,179,191
//,3
public class xxx {
    @Override
    public Exchange get(CamelContext camelContext, String key) {
        Object[] pkValues = getPKValues(key);
        LOGGER.debug("Selecting key {}", pkValues);
        Row row = getSession().execute(selectStatement.bind(pkValues)).one();
        Exchange exchange = null;
        if (row != null) {
            try {
                exchange = exchangeCodec.unmarshallExchange(camelContext, row.getByteBuffer(exchangeColumn));
            } catch (IOException iOException) {
                throw new CassandraAggregationException("Failed to read exchange", exchange, iOException);
            } catch (ClassNotFoundException classNotFoundException) {
                throw new CassandraAggregationException("Failed to read exchange", exchange, classNotFoundException);
            }
        }
        return exchange;
    }

};