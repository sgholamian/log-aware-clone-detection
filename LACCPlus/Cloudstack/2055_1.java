//,temp,LoadBalancingRulesManagerImpl.java,785,851,temp,LoadBalancingRulesManagerImpl.java,383,434
//,3
public class xxx {
    @DB
    @Override
    @ActionEvent(eventType = EventTypes.EVENT_LB_HEALTHCHECKPOLICY_DELETE, eventDescription = "revoking LB HealthCheck policy ", async = true)
    public boolean deleteLBHealthCheckPolicy(long healthCheckPolicyId, boolean apply) {
        boolean success = true;

        CallContext caller = CallContext.current();
        LBHealthCheckPolicyVO healthCheckPolicy = _lb2healthcheckDao.findById(healthCheckPolicyId);

        if (healthCheckPolicy == null) {
            throw new InvalidParameterException("Invalid HealthCheck policy id value: " + healthCheckPolicyId);
        }
        LoadBalancerVO loadBalancer = _lbDao.findById(Long.valueOf(healthCheckPolicy.getLoadBalancerId()));
        if (loadBalancer == null) {
            throw new InvalidParameterException("Invalid Load balancer : " + healthCheckPolicy.getLoadBalancerId() + " for HealthCheck policy id: " + healthCheckPolicyId);
        }
        final long loadBalancerId = loadBalancer.getId();
        FirewallRule.State backupState = loadBalancer.getState();
        _accountMgr.checkAccess(caller.getCallingAccount(), null, true, loadBalancer);

        if (apply) {
            if (loadBalancer.getState() == FirewallRule.State.Active) {
                loadBalancer.setState(FirewallRule.State.Add);
                _lbDao.persist(loadBalancer);
            }

            boolean backupStickyState = healthCheckPolicy.isRevoke();
            healthCheckPolicy.setRevoke(true);
            _lb2healthcheckDao.persist(healthCheckPolicy);
            s_logger.debug("Set health check policy to revoke for loadbalancing rule id : " + loadBalancerId + ", healthCheckpolicyID " + healthCheckPolicyId);

            // removing the state of services set by the monitor.
            final List<LoadBalancerVMMapVO> maps = _lb2VmMapDao.listByLoadBalancerId(loadBalancerId);
            if (maps != null) {
                Transaction.execute(new TransactionCallbackNoReturn() {
                    @Override
                    public void doInTransactionWithoutResult(TransactionStatus status) {
                        s_logger.debug("Resetting health state policy for services in loadbalancing rule id : " + loadBalancerId);
                        for (LoadBalancerVMMapVO map : maps) {
                            map.setState(null);
                            _lb2VmMapDao.persist(map);
                        }
                    }
                });
            }

            try {
                if (!applyLoadBalancerConfig(loadBalancerId)) {
                    s_logger.warn("Failed to remove load balancer rule id " + loadBalancerId + " for healthCheckpolicyID " + healthCheckPolicyId);
                    throw new CloudRuntimeException("Failed to remove load balancer rule id " + loadBalancerId + " for healthCheckpolicyID " + healthCheckPolicyId);
                }
            } catch (ResourceUnavailableException e) {
                if (isRollBackAllowedForProvider(loadBalancer)) {
                    healthCheckPolicy.setRevoke(backupStickyState);
                    _lb2healthcheckDao.persist(healthCheckPolicy);
                    loadBalancer.setState(backupState);
                    _lbDao.persist(loadBalancer);
                    s_logger.debug("LB Rollback rule id: " + loadBalancer.getId() + "  while deleting healthcheck policy: " + healthCheckPolicyId);
                }
                s_logger.warn("Unable to apply the load balancer config because resource is unavaliable.", e);
                success = false;
            }
        } else {
            _lb2healthcheckDao.remove(healthCheckPolicy.getLoadBalancerId());
        }
        return success;
    }

};