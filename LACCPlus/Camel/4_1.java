//,temp,CassandraIdempotentRepository.java,176,181,temp,CassandraIdempotentRepository.java,154,159
//,2
public class xxx {
    @Override
    public boolean remove(String key) {
        Object[] idValues = getPKValues(key);
        LOGGER.debug("Deleting key {}", (Object) idValues);
        return isApplied(getSession().execute(deleteStatement.bind(idValues)));
    }

};