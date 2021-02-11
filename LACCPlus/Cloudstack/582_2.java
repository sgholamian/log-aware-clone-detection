//,temp,XenServerFencer.java,52,100,temp,OvmFencer.java,69,117
//,3
public class xxx {
    @Override
    public Boolean fenceOff(VirtualMachine vm, Host host) {
        if (host.getHypervisorType() != HypervisorType.Ovm) {
            s_logger.debug("Don't know how to fence non Ovm hosts " + host.getHypervisorType());
            return null;
        }

        List<HostVO> hosts = _resourceMgr.listAllHostsInCluster(host.getClusterId());
        FenceCommand fence = new FenceCommand(vm, host);

        for (HostVO h : hosts) {
            if (h.getHypervisorType() != HypervisorType.Ovm) {
                continue;
            }

            if (h.getStatus() != Status.Up) {
                continue;
            }

            if (h.getId() == host.getId()) {
                continue;
            }

            FenceAnswer answer;
            try {
                answer = (FenceAnswer)_agentMgr.send(h.getId(), fence);
            } catch (AgentUnavailableException e) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Moving on to the next host because " + h.toString() + " is unavailable", e);
                }
                continue;
            } catch (OperationTimedoutException e) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Moving on to the next host because " + h.toString() + " is unavailable", e);
                }
                continue;
            }

            if (answer != null && answer.getResult()) {
                return true;
            }
        }

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Unable to fence off " + vm.toString() + " on " + host.toString());
        }

        return false;
    }

};