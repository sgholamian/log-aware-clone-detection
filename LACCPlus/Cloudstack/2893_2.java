//,temp,ServerDBSyncImpl.java,336,347,temp,ServerDBSyncImpl.java,235,246
//,2
public class xxx {
    public boolean syncDomain() throws Exception {
        final ApiConnector api = _manager.getApiConnector();
        try {
            List<?> dbList = _domainDao.listAll();
            @SuppressWarnings("unchecked")
            List<?> vncList = api.list(net.juniper.contrail.api.types.Domain.class, null);
            return _dbSync.syncGeneric(net.juniper.contrail.api.types.Domain.class, dbList, vncList);
        } catch (Exception ex) {
            s_logger.warn("syncDomain", ex);
            throw ex;
        }
    }

};