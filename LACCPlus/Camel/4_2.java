//,temp,CassandraIdempotentRepository.java,176,181,temp,CassandraIdempotentRepository.java,154,159
//,2
public class xxx {
    @Override
    public boolean contains(String key) {
        Object[] idValues = getPKValues(key);
        LOGGER.debug("Checking key {}", (Object) idValues);
        return isKey(getSession().execute(selectStatement.bind(idValues)));
    }

};