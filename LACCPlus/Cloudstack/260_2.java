//,temp,ClusterMO.java,620,638,temp,HostMO.java,878,896
//,3
public class xxx {
    @Override
    public void unmountDatastore(String uuid) throws Exception {

        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - unmountDatastore(). target MOR: " + _mor.getValue() + ", uuid: " + uuid);

        HostDatastoreSystemMO hostDatastoreSystemMo = getHostDatastoreSystemMO();
        if (!hostDatastoreSystemMo.deleteDatastore(uuid)) {
            String msg = "Unable to unmount datastore. uuid: " + uuid;
            s_logger.error(msg);

            if (s_logger.isTraceEnabled())
                s_logger.trace("vCenter API trace - unmountDatastore() done(failed)");
            throw new Exception(msg);
        }

        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - unmountDatastore() done");
    }

};