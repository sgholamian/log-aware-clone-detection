//,temp,KVMInvestigator.java,70,138,temp,KVMHostActivityChecker.java,86,144
//,3
public class xxx {
    private boolean isAgentActive(Host agent) {
        if (agent.getHypervisorType() != Hypervisor.HypervisorType.KVM && agent.getHypervisorType() != Hypervisor.HypervisorType.LXC) {
            throw new IllegalStateException("Calling KVM investigator for non KVM Host of type " + agent.getHypervisorType());
        }
        Status hostStatus = Status.Unknown;
        Status neighbourStatus = Status.Unknown;
        final CheckOnHostCommand cmd = new CheckOnHostCommand(agent);
        try {
            Answer answer = agentMgr.easySend(agent.getId(), cmd);
            if (answer != null) {
                hostStatus = answer.getResult() ? Status.Down : Status.Up;
                if ( hostStatus == Status.Up ){
                    return true;
                }
            }
            else {
                hostStatus = Status.Disconnected;
            }
        } catch (Exception e) {
            LOG.warn("Failed to send command to host: " + agent.getId());
        }

        List<HostVO> neighbors = resourceManager.listHostsInClusterByStatus(agent.getClusterId(), Status.Up);
        for (HostVO neighbor : neighbors) {
            if (neighbor.getId() == agent.getId() || (neighbor.getHypervisorType() != Hypervisor.HypervisorType.KVM && neighbor.getHypervisorType() != Hypervisor.HypervisorType.LXC)) {
                continue;
            }
            if (LOG.isTraceEnabled()){
                LOG.trace("Investigating host:" + agent.getId() + " via neighbouring host:" + neighbor.getId());
            }
            try {
                Answer answer = agentMgr.easySend(neighbor.getId(), cmd);
                if (answer != null) {
                    neighbourStatus = answer.getResult() ? Status.Down : Status.Up;
                    if (LOG.isTraceEnabled()){
                        LOG.trace("Neighbouring host:" + neighbor.getId() + " returned status:" + neighbourStatus + " for the investigated host:" + agent.getId());
                    }
                    if (neighbourStatus == Status.Up) {
                        break;
                    }
                }
            } catch (Exception e) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("Failed to send command to host: " + neighbor.getId());
                }
            }
        }
        if (neighbourStatus == Status.Up && (hostStatus == Status.Disconnected || hostStatus == Status.Down)) {
            hostStatus = Status.Disconnected;
        }
        if (neighbourStatus == Status.Down && (hostStatus == Status.Disconnected || hostStatus == Status.Down)) {
            hostStatus = Status.Down;
        }

        if (LOG.isTraceEnabled()){
            LOG.trace("Resource state = " + hostStatus.name());
        }
        return hostStatus == Status.Up;
    }

};