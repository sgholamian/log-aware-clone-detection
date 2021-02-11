//,temp,VMSnapshotDaoImpl.java,128,183,temp,VMInstanceDaoImpl.java,476,545
//,3
public class xxx {
    @Override
    public boolean updateState(State oldState, Event event, State newState, VirtualMachine vm, Object opaque) {
        if (newState == null) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("There's no way to transition from old state: " + oldState.toString() + " event: " + event.toString());
            }
            return false;
        }

        @SuppressWarnings("unchecked")
        Pair<Long, Long> hosts = (Pair<Long, Long>)opaque;
        Long newHostId = hosts.second();

        VMInstanceVO vmi = (VMInstanceVO)vm;
        Long oldHostId = vmi.getHostId();
        Long oldUpdated = vmi.getUpdated();
        Date oldUpdateDate = vmi.getUpdateTime();
        if (newState.equals(oldState) && newHostId != null && newHostId.equals(oldHostId)) {
            // state is same, don't need to update
            return true;
        }
        if(ifStateUnchanged(oldState,newState, oldHostId, newHostId)) {
            return true;
        }

        // lock the target row at beginning to avoid lock-promotion caused deadlock
        lockRow(vm.getId(), true);

        SearchCriteria<VMInstanceVO> sc = StateChangeSearch.create();
        sc.setParameters("id", vmi.getId());
        sc.setParameters("states", oldState);
        sc.setParameters("host", vmi.getHostId());
        sc.setParameters("update", vmi.getUpdated());

        vmi.incrUpdated();
        UpdateBuilder ub = getUpdateBuilder(vmi);

        ub.set(vmi, "state", newState);
        ub.set(vmi, "hostId", newHostId);
        ub.set(vmi, "podIdToDeployIn", vmi.getPodIdToDeployIn());
        ub.set(vmi, _updateTimeAttr, new Date());

        int result = update(vmi, sc);
        if (result == 0) {
            VMInstanceVO vo = findByIdIncludingRemoved(vm.getId());

            if (s_logger.isDebugEnabled()) {
                if (vo != null) {
                    StringBuilder str = new StringBuilder("Unable to update ").append(vo.toString());
                    str.append(": DB Data={Host=").append(vo.getHostId()).append("; State=").append(vo.getState().toString()).append("; updated=").append(vo.getUpdated())
                            .append("; time=").append(vo.getUpdateTime());
                    str.append("} New Data: {Host=").append(vm.getHostId()).append("; State=").append(vm.getState().toString()).append("; updated=").append(vmi.getUpdated())
                            .append("; time=").append(vo.getUpdateTime());
                    str.append("} Stale Data: {Host=").append(oldHostId).append("; State=").append(oldState).append("; updated=").append(oldUpdated).append("; time=")
                            .append(oldUpdateDate).append("}");
                    s_logger.debug(str.toString());

                } else {
                    s_logger.debug("Unable to update the vm id=" + vm.getId() + "; the vm either doesn't exist or already removed");
                }
            }

            if (vo != null && vo.getState() == newState) {
                // allow for concurrent update if target state has already been matched
                s_logger.debug("VM " + vo.getInstanceName() + " state has been already been updated to " + newState);
                return true;
            }
        }
        return result > 0;
    }

};