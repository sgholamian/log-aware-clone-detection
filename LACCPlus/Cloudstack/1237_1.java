//,temp,ServerDBSyncImpl.java,942,966,temp,ServerDBSyncImpl.java,450,473
//,3
public class xxx {
    @SuppressWarnings({ "unchecked" })
    public boolean syncNetworkPolicy() throws Exception {
        final ApiConnector api = _manager.getApiConnector();
        try {

            List<NetworkACLVO> dbAcls = _manager.findManagedACLs();
            if (dbAcls == null) {
                dbAcls = new ArrayList<NetworkACLVO>();
            }

            List<NetworkPolicy> pList = (List<NetworkPolicy>) api.list(NetworkPolicy.class, null);
            List<NetworkPolicy> vncList = new ArrayList<NetworkPolicy>();

            for (NetworkPolicy policy:pList) {
                if (!_manager.isSystemDefaultNetworkPolicy(policy)) {
                    vncList.add(policy);
                }
            }
            s_logger.debug("sync Network Policy - DB size: " + dbAcls.size() + " VNC Size: " + vncList.size());
            return _dbSync.syncGeneric(NetworkPolicy.class, dbAcls, vncList);
        } catch (Exception ex) {
            s_logger.warn("sync network-policys", ex);
            throw ex;
        }
    }

};