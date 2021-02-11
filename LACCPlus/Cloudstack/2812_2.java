//,temp,SecurityGroupManagerImpl2.java,168,216,temp,SecurityGroupManagerImpl.java,938,1026
//,3
public class xxx {
    @DB
    public void work() {
        if (s_logger.isTraceEnabled()) {
            s_logger.trace("Checking the database");
        }
        final SecurityGroupWorkVO work = _workDao.take(_serverId);
        if (work == null) {
            if (s_logger.isTraceEnabled()) {
                s_logger.trace("Security Group work: no work found");
            }
            return;
        }
        final Long userVmId = work.getInstanceId();
        if (work.getStep() == Step.Done) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Security Group work: found a job in done state, rescheduling for vm: " + userVmId);
            }
            ArrayList<Long> affectedVms = new ArrayList<Long>();
            affectedVms.add(userVmId);
            scheduleRulesetUpdateToHosts(affectedVms, false, _timeBetweenCleanups * 1000l);
            return;
        }
        s_logger.debug("Working on " + work);

        Transaction.execute(new TransactionCallbackNoReturn() {
            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                UserVm vm = null;
                Long seqnum = null;

                boolean locked = false;
                try {
                    vm = _userVMDao.acquireInLockTable(work.getInstanceId());
                    if (vm == null) {
                        vm = _userVMDao.findById(work.getInstanceId());
                        if (vm == null) {
                            s_logger.info("VM " + work.getInstanceId() + " is removed");
                            locked = true;
                            return;
                        }
                        s_logger.warn("Unable to acquire lock on vm id=" + userVmId);
                        return;
                    }
                    locked = true;
                    Long agentId = null;
                    VmRulesetLogVO log = _rulesetLogDao.findByVmId(userVmId);
                    if (log == null) {
                        s_logger.warn("Cannot find log record for vm id=" + userVmId);
                        return;
                    }
                    seqnum = log.getLogsequence();

                    if (vm != null && vm.getState() == State.Running) {
                        Map<PortAndProto, Set<String>> ingressRules = generateRulesForVM(userVmId, SecurityRuleType.IngressRule);
                        Map<PortAndProto, Set<String>> egressRules = generateRulesForVM(userVmId, SecurityRuleType.EgressRule);
                        agentId = vm.getHostId();
                        if (agentId != null) {
                            // get nic secondary ip address
                            String privateIp = vm.getPrivateIpAddress();
                            NicVO nic = _nicDao.findByIp4AddressAndVmId(privateIp, vm.getId());
                            List<String> nicSecIps = null;
                            if (nic != null) {
                                if (nic.getSecondaryIp()) {
                                    //get secondary ips of the vm
                                    nicSecIps = _nicSecIpDao.getSecondaryIpAddressesForNic(nic.getId());
                                }
                            }
                            SecurityGroupRulesCmd cmd = generateRulesetCmd(vm.getInstanceName(), nic.getIPv6Address(), vm.getPrivateIpAddress(), vm.getPrivateMacAddress(), vm.getId(),
                                    generateRulesetSignature(ingressRules, egressRules), seqnum, ingressRules, egressRules, nicSecIps);
                            Commands cmds = new Commands(cmd);
                            try {
                                _agentMgr.send(agentId, cmds, _answerListener);
                            } catch (AgentUnavailableException e) {
                                s_logger.debug("Unable to send ingress rules updates for vm: " + userVmId + "(agentid=" + agentId + ")");
                                _workDao.updateStep(work.getInstanceId(), seqnum, Step.Done);
                            }

                        }
                    }
                } finally {
                    if (locked) {
                        _userVMDao.releaseFromLockTable(userVmId);
                        _workDao.updateStep(work.getId(), Step.Done);
                    }
                }
            }
        });

    }

};