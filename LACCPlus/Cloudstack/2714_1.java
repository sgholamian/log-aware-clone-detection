//,temp,OutOfBandManagementDaoImpl.java,121,157,temp,HAConfigDaoImpl.java,76,111
//,3
public class xxx {
    @Override
    public boolean updateState(OutOfBandManagement.PowerState oldStatus, OutOfBandManagement.PowerState.Event event, OutOfBandManagement.PowerState newStatus, OutOfBandManagement vo, Object data) {
        OutOfBandManagementVO oobmHost = (OutOfBandManagementVO) vo;
        if (oobmHost == null) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Invalid out-of-band management host view object provided");
            }
            return false;
        }

        Long newManagementServerId = event.getServerId();
        // Avoid updates when old ownership and state are same as new
        if (oldStatus == newStatus && (oobmHost.getManagementServerId() != null && oobmHost.getManagementServerId().equals(newManagementServerId))) {
            return false;
        }

        if (event == OutOfBandManagement.PowerState.Event.Disabled) {
            newManagementServerId = null;
        }

        SearchCriteria<OutOfBandManagementVO> sc = StateUpdateSearch.create();
        sc.setParameters("status", oldStatus);
        sc.setParameters("id", oobmHost.getId());
        sc.setParameters("update", oobmHost.getUpdateCount());

        oobmHost.incrUpdateCount();
        UpdateBuilder ub = getUpdateBuilder(oobmHost);
        ub.set(oobmHost, PowerStateAttr, newStatus);
        ub.set(oobmHost, UpdateTimeAttr, DateUtil.currentGMTTime());
        ub.set(oobmHost, MsIdAttr, newManagementServerId);

        int result = update(ub, sc, null);
        if (LOG.isDebugEnabled() && result <= 0) {
            LOG.debug(String.format("Failed to update out-of-band management power state from:%s to:%s due to event:%s for the host id:%d", oldStatus, newStatus, event, oobmHost.getHostId()));
        }
        return result > 0;
    }

};