//,temp,OvsTunnelManagerImpl.java,152,172,temp,SecurityGroupManagerImpl.java,816,840
//,3
public class xxx {
    @DB
    protected OvsTunnelInterfaceVO createInterfaceRecord(String ip,
            String netmask, String mac, long hostId, String label) {
        OvsTunnelInterfaceVO ti = null;
        try {
            ti = new OvsTunnelInterfaceVO(ip, netmask, mac, hostId, label);
            // TODO: Is locking really necessary here?
            OvsTunnelInterfaceVO lock = _tunnelInterfaceDao
                    .acquireInLockTable(Long.valueOf(1));
            if (lock == null) {
                s_logger.warn("Cannot lock table ovs_tunnel_account");
                return null;
            }
            _tunnelInterfaceDao.persist(ti);
            _tunnelInterfaceDao.releaseFromLockTable(lock.getId());
        } catch (EntityExistsException e) {
            s_logger.debug("A record for the interface for network " + label
                    + " on host id " + hostId + " already exists");
        }
        return ti;
    }

};