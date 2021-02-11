//,temp,LoadBalancingRulesManagerImpl.java,785,851,temp,LoadBalancingRulesManagerImpl.java,383,434
//,3
public class xxx {
    @Override
    @DB
    public boolean configureLbAutoScaleVmGroup(final long vmGroupid, String currentState) throws ResourceUnavailableException {
        final AutoScaleVmGroupVO vmGroup = _autoScaleVmGroupDao.findById(vmGroupid);
        boolean success = false;

        final LoadBalancerVO loadBalancer = _lbDao.findById(vmGroup.getLoadBalancerId());

        FirewallRule.State backupState = loadBalancer.getState();

        if (vmGroup.getState().equals(AutoScaleVmGroup.State_New)) {
            loadBalancer.setState(FirewallRule.State.Add);
            _lbDao.persist(loadBalancer);
        } else if (loadBalancer.getState() == FirewallRule.State.Active && vmGroup.getState().equals(AutoScaleVmGroup.State_Revoke)) {
            loadBalancer.setState(FirewallRule.State.Add);
            _lbDao.persist(loadBalancer);
        }

        try {
            success = applyAutoScaleConfig(loadBalancer, vmGroup, currentState);
        } catch (ResourceUnavailableException e) {
            s_logger.warn("Unable to configure AutoScaleVmGroup to the lb rule: " + loadBalancer.getId() + " because resource is unavaliable:", e);
            if (isRollBackAllowedForProvider(loadBalancer)) {
                loadBalancer.setState(backupState);
                _lbDao.persist(loadBalancer);
                s_logger.debug("LB Rollback rule id: " + loadBalancer.getId() + " lb state rolback while creating AutoscaleVmGroup");
            }
            throw e;
        } finally {
            if (!success) {
                s_logger.warn("Failed to configure LB Auto Scale Vm Group with Id:" + vmGroupid);
            }
        }

        if (success) {
            if (vmGroup.getState().equals(AutoScaleVmGroup.State_New)) {
                Transaction.execute(new TransactionCallbackNoReturn() {
                    @Override
                    public void doInTransactionWithoutResult(TransactionStatus status) {
                        loadBalancer.setState(FirewallRule.State.Active);
                        s_logger.debug("LB rule " + loadBalancer.getId() + " state is set to Active");
                        _lbDao.persist(loadBalancer);
                        vmGroup.setState(AutoScaleVmGroup.State_Enabled);
                        _autoScaleVmGroupDao.persist(vmGroup);
                        s_logger.debug("LB Auto Scale Vm Group with Id: " + vmGroupid + " is set to Enabled state.");
                    }
                });
            }
            s_logger.info("Successfully configured LB Autoscale Vm Group with Id: " + vmGroupid);
        }
        return success;
    }

};