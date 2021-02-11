//,temp,ServerDBSyncImpl.java,336,347,temp,ServerDBSyncImpl.java,235,246
//,2
public class xxx {
    @SuppressWarnings("unchecked")
    public boolean syncProject() throws Exception {
        final ApiConnector api = _manager.getApiConnector();
        try {
            List<?> dbList = _projectDao.listAll();
            List<?> vncList = api.list(net.juniper.contrail.api.types.Project.class, null);
            return _dbSync.syncGeneric(net.juniper.contrail.api.types.Project.class, dbList, vncList);
        } catch (Exception ex) {
            s_logger.warn("syncProject", ex);
            throw ex;
        }
    }

};