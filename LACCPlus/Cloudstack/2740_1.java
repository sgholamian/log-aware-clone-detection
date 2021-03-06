//,temp,KVMInvestigator.java,70,138,temp,KVMHostActivityChecker.java,86,144
//,3
public class xxx {
    @Override
    public Status isAgentAlive(Host agent) {
        if (agent.getHypervisorType() != Hypervisor.HypervisorType.KVM && agent.getHypervisorType() != Hypervisor.HypervisorType.LXC) {
            return null;
        }

        if (haManager.isHAEligible(agent)) {
            return haManager.getHostStatus(agent);
        }

        List<StoragePoolVO> clusterPools = _storagePoolDao.listPoolsByCluster(agent.getClusterId());
        boolean hasNfs = false;
        for (StoragePoolVO pool : clusterPools) {
            if (pool.getPoolType() == StoragePoolType.NetworkFilesystem) {
                hasNfs = true;
                break;
            }
        }
        if (!hasNfs) {
            s_logger.warn(
                    "Agent investigation was requested on host " + agent + ", but host does not support investigation because it has no NFS storage. Skipping investigation.");
            return Status.Disconnected;
        }

        Status hostStatus = null;
        Status neighbourStatus = null;
        CheckOnHostCommand cmd = new CheckOnHostCommand(agent);

        try {
            Answer answer = _agentMgr.easySend(agent.getId(), cmd);
            if (answer != null) {
                hostStatus = answer.getResult() ? Status.Down : Status.Up;
            }
        } catch (Exception e) {
            s_logger.debug("Failed to send command to host: " + agent.getId());
        }
        if (hostStatus == null) {
            hostStatus = Status.Disconnected;
        }

        List<HostVO> neighbors = _resourceMgr.listHostsInClusterByStatus(agent.getClusterId(), Status.Up);
        for (HostVO neighbor : neighbors) {
            if (neighbor.getId() == agent.getId()
                    || (neighbor.getHypervisorType() != Hypervisor.HypervisorType.KVM && neighbor.getHypervisorType() != Hypervisor.HypervisorType.LXC)) {
                continue;
            }
            s_logger.debug("Investigating host:" + agent.getId() + " via neighbouring host:" + neighbor.getId());
            try {
                Answer answer = _agentMgr.easySend(neighbor.getId(), cmd);
                if (answer != null) {
                    neighbourStatus = answer.getResult() ? Status.Down : Status.Up;
                    s_logger.debug("Neighbouring host:" + neighbor.getId() + " returned status:" + neighbourStatus + " for the investigated host:" + agent.getId());
                    if (neighbourStatus == Status.Up) {
                        break;
                    }
                }
            } catch (Exception e) {
                s_logger.debug("Failed to send command to host: " + neighbor.getId());
            }
        }
        if (neighbourStatus == Status.Up && (hostStatus == Status.Disconnected || hostStatus == Status.Down)) {
            hostStatus = Status.Disconnected;
        }
        if (neighbourStatus == Status.Down && (hostStatus == Status.Disconnected || hostStatus == Status.Down)) {
            hostStatus = Status.Down;
        }
        s_logger.debug("HA: HOST is ineligible legacy state " + hostStatus + " for host " + agent.getId());
        return hostStatus;
    }

};