//,temp,Ovm3FenceBuilder.java,74,115,temp,XenServerFencer.java,52,100
//,3
public class xxx {
    @Override
    public Boolean fenceOff(VirtualMachine vm, Host host) {
        if (host.getHypervisorType() != HypervisorType.XenServer) {
            s_logger.debug("Don't know how to fence non XenServer hosts " + host.getHypervisorType());
            return null;
        }

        List<HostVO> hosts = _resourceMgr.listAllHostsInCluster(host.getClusterId());
        FenceCommand fence = new FenceCommand(vm, host);

        for (HostVO h : hosts) {
            if (h.getHypervisorType() == HypervisorType.XenServer) {
                if (h.getStatus() != Status.Up) {
                    continue;
                }
                if (h.getId() == host.getId()) {
                    continue;
                }
                FenceAnswer answer;
                try {
                    Answer ans = _agentMgr.send(h.getId(), fence);
                    if (!(ans instanceof FenceAnswer)) {
                        s_logger.debug("Answer is not fenceanswer.  Result = " + ans.getResult() + "; Details = " + ans.getDetails());
                        continue;
                    }
                    answer = (FenceAnswer)ans;
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
        }

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Unable to fence off " + vm.toString() + " on " + host.toString());
        }

        return false;
    }

};