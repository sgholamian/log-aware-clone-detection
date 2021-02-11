//,temp,GenericDaoBase.java,1657,1677,temp,GenericDaoBase.java,1639,1655
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    @DB()
    protected T toEntityBean(final ResultSet result, final boolean cache) throws SQLException {
        final T entity = (T)_factory.newInstance(new Callback[] {NoOp.INSTANCE, new UpdateBuilder(this)});

        toEntityBean(result, entity);

        if (cache && _cache != null) {
            try {
                _cache.put(new Element(_idField.get(entity), entity));
            } catch (final Exception e) {
                s_logger.debug("Can't put it in the cache", e);
            }
        }

        return entity;
    }

};