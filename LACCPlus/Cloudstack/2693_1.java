//,temp,LoadBalancingRulesManagerImpl.java,1238,1289,temp,LoadBalancingRulesManagerImpl.java,733,783
//,3
public class xxx {
    @Override
    @DB
    @ActionEvent(eventType = EventTypes.EVENT_LB_CERT_REMOVE, eventDescription = "removing certificate from load balancer", async = true)
    public boolean removeCertFromLoadBalancer(long lbRuleId) {
        CallContext caller = CallContext.current();

        LoadBalancerVO loadBalancer = _lbDao.findById(lbRuleId);
        LoadBalancerCertMapVO lbCertMap = _lbCertMapDao.findByLbRuleId(lbRuleId);

        if (loadBalancer == null) {
            throw new InvalidParameterException("Invalid load balancer value: " + lbRuleId);
        }

        if (lbCertMap == null) {
            throw new InvalidParameterException("No certificate is bound to lb with id: " + lbRuleId);
        }

        _accountMgr.checkAccess(caller.getCallingAccount(), null, true, loadBalancer);

        boolean success = false;
        FirewallRule.State backupState = loadBalancer.getState();
        try {

            loadBalancer.setState(FirewallRule.State.Add);
            _lbDao.persist(loadBalancer);
            lbCertMap.setRevoke(true);
            _lbCertMapDao.persist(lbCertMap);

            if (!applyLoadBalancerConfig(lbRuleId)) {
                s_logger.warn("Failed to remove cert from load balancer rule id " + lbRuleId);
                CloudRuntimeException ex = new CloudRuntimeException("Failed to remove certificate load balancer rule id " + lbRuleId);
                ex.addProxyObject(loadBalancer.getUuid(), "loadBalancerId");
                throw ex;
            }
            success = true;
        } catch (ResourceUnavailableException e) {
            if (isRollBackAllowedForProvider(loadBalancer)) {
                lbCertMap.setRevoke(false);
                _lbCertMapDao.persist(lbCertMap);
                loadBalancer.setState(backupState);
                _lbDao.persist(loadBalancer);
                s_logger.debug("Rolled back certificate removal lb id " + lbRuleId);
            }
            s_logger.warn("Unable to apply the load balancer config because resource is unavaliable.", e);
            if (!success) {
                CloudRuntimeException ex = new CloudRuntimeException("Failed to remove certificate from load balancer rule id " + lbRuleId);
                ex.addProxyObject(loadBalancer.getUuid(), "loadBalancerId");
                throw ex;
            }
        }
        return success;
    }

};