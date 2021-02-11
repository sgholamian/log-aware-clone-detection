//,temp,OutOfBandManagementDaoImpl.java,121,157,temp,HAConfigDaoImpl.java,76,111
//,3
public class xxx {
    @Override
    public boolean updateState(HAConfig.HAState currentState, HAConfig.Event event, HAConfig.HAState nextState, HAConfig vo, Object data) {
        HAConfigVO haConfig = (HAConfigVO) vo;
        if (haConfig == null) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Invalid ha config view object provided");
            }
            return false;
        }

        Long newManagementServerId = event.getServerId();
        if (currentState == nextState && (haConfig.getManagementServerId() != null && haConfig.getManagementServerId().equals(newManagementServerId))) {
            return false;
        }

        if (event == HAConfig.Event.Disabled) {
            newManagementServerId = null;
        }

        SearchCriteria<HAConfigVO> sc = StateUpdateSearch.create();
        sc.setParameters("id", haConfig.getId());
        sc.setParameters("haState", currentState);
        sc.setParameters("update", haConfig.getUpdateCount());

        haConfig.incrUpdateCount();
        UpdateBuilder ub = getUpdateBuilder(haConfig);
        ub.set(haConfig, HAStateAttr, nextState);
        ub.set(haConfig, UpdateTimeAttr, DateUtil.currentGMTTime());
        ub.set(haConfig, MsIdAttr, newManagementServerId);

        int result = update(ub, sc, null);
        if (LOG.isTraceEnabled() && result <= 0) {
            LOG.trace(String.format("Failed to update HA state from:%s to:%s due to event:%s for the ha_config id:%d", currentState, nextState, event, haConfig.getId()));
        }
        return result > 0;
    }

};