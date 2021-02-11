//,temp,Ovm3FenceBuilder.java,74,115,temp,OvmFencer.java,69,117
//,3
public class xxx {
    @Override
    public Boolean fenceOff(VirtualMachine vm, Host host) {
        if (host.getHypervisorType() != HypervisorType.Ovm3) {
            LOGGER.debug("Don't know how to fence non Ovm3 hosts "
                    + host.getHypervisorType());
            return null;
        } else {
            LOGGER.debug("Fencing " + vm + " on host " + host
                    + " with params: "+ fenceParams );
        }

        List<HostVO> hosts = resourceMgr.listAllHostsInCluster(host
                .getClusterId());
        FenceCommand fence = new FenceCommand(vm, host);

        for (HostVO h : hosts) {
            if (h.getHypervisorType() == HypervisorType.Ovm3 &&
                    h.getStatus() == Status.Up &&
                    h.getId() != host.getId()) {
                FenceAnswer answer;
                try {
                    answer = (FenceAnswer) agentMgr.send(h.getId(), fence);
                } catch (AgentUnavailableException | OperationTimedoutException e) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Moving on to the next host because "
                                + h.toString() + " is unavailable", e);
                    }
                    continue;
                }
                if (answer != null && answer.getResult()) {
                    return true;
                }
            }
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Unable to fence off " + vm.toString() + " on "
                    + host.toString());
        }

        return false;
    }

};