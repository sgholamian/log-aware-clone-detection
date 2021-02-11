//,temp,GenericDaoBase.java,1657,1677,temp,GenericDaoBase.java,1639,1655
//,3
public class xxx {
    @DB()
    protected T toVO(ResultSet result, boolean cache) throws SQLException {
        T entity;
        try {
            entity = _entityBeanType.newInstance();
        } catch (InstantiationException e1) {
            throw new CloudRuntimeException("Unable to instantiate entity", e1);
        } catch (IllegalAccessException e1) {
            throw new CloudRuntimeException("Illegal Access", e1);
        }
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