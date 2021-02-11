//,temp,SecurityGroupManagerImpl2.java,168,216,temp,SecurityGroupManagerImpl.java,938,1026
//,3
public class xxx {
    public void sendRulesetUpdates(SecurityGroupWork work) {
        Long userVmId = work.getInstanceId();
        UserVm vm = _userVMDao.findById(userVmId);

        if (vm != null && vm.getState() == State.Running) {
            if (s_logger.isTraceEnabled()) {
                s_logger.trace("SecurityGroupManager v2: found vm, " + userVmId + " state=" + vm.getState());
            }
            Map<PortAndProto, Set<String>> ingressRules = generateRulesForVM(userVmId, SecurityRuleType.IngressRule);
            Map<PortAndProto, Set<String>> egressRules = generateRulesForVM(userVmId, SecurityRuleType.EgressRule);
            Long agentId = vm.getHostId();
            if (agentId != null) {
                String privateIp = vm.getPrivateIpAddress();
                NicVO nic = _nicDao.findByIp4AddressAndVmId(privateIp, vm.getId());
                List<String> nicSecIps = null;
                if (nic != null) {
                    if (nic.getSecondaryIp()) {
                        nicSecIps = _nicSecIpDao.getSecondaryIpAddressesForNic(nic.getId());
                    }
                }
                SecurityGroupRulesCmd cmd =
                    generateRulesetCmd(vm.getInstanceName(), vm.getPrivateIpAddress(), nic.getIPv6Address(), vm.getPrivateMacAddress(), vm.getId(), null, work.getLogsequenceNumber(),
                        ingressRules, egressRules, nicSecIps);
                cmd.setMsId(_serverId);
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("SecurityGroupManager v2: sending ruleset update for vm " + vm.getInstanceName() + ":ingress num rules=" +
                        cmd.getIngressRuleSet().size() + ":egress num rules=" + cmd.getEgressRuleSet().size() + " num cidrs=" + cmd.getTotalNumCidrs() + " sig=" +
                        cmd.getSignature());
                }
                Commands cmds = new Commands(cmd);
                try {
                    _agentMgr.send(agentId, cmds, _answerListener);
                    if (s_logger.isTraceEnabled()) {
                        s_logger.trace("SecurityGroupManager v2: sent ruleset updates for " + vm.getInstanceName() + " curr queue size=" + _workQueue.size());
                    }
                } catch (AgentUnavailableException e) {
                    s_logger.debug("Unable to send updates for vm: " + userVmId + "(agentid=" + agentId + ")");
                    _workTracker.handleException(agentId);
                }
            }
        } else {
            if (s_logger.isDebugEnabled()) {
                if (vm != null)
                    s_logger.debug("No rules sent to vm " + vm + "state=" + vm.getState());
                else
                    s_logger.debug("Could not find vm: No rules sent to vm " + userVmId);
            }
        }
    }

};