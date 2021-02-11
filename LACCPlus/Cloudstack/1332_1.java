//,temp,GlobalLoadBalancingRulesServiceImpl.java,304,416,temp,GlobalLoadBalancingRulesServiceImpl.java,167,302
//,3
public class xxx {
    @Override
    @DB
    @ActionEvent(eventType = EventTypes.EVENT_REMOVE_FROM_GLOBAL_LOAD_BALANCER_RULE,
                 eventDescription = "Removing a load balancer rule to be part of global load balancer rule")
    public boolean removeFromGlobalLoadBalancerRule(RemoveFromGlobalLoadBalancerRuleCmd removeFromGslbCmd) {

        CallContext ctx = CallContext.current();
        Account caller = ctx.getCallingAccount();

        final long gslbRuleId = removeFromGslbCmd.getGlobalLoadBalancerRuleId();
        final GlobalLoadBalancerRuleVO gslbRule = _gslbRuleDao.findById(gslbRuleId);
        if (gslbRule == null) {
            throw new InvalidParameterValueException("Invalid global load balancer rule id: " + gslbRuleId);
        }

        _accountMgr.checkAccess(caller, SecurityChecker.AccessType.OperateEntry, true, gslbRule);

        if (gslbRule.getState() == GlobalLoadBalancerRule.State.Revoke) {
            throw new InvalidParameterValueException("global load balancer rule id: " + gslbRuleId + " is already in revoked state");
        }

        final List<Long> lbRuleIdsToremove = removeFromGslbCmd.getLoadBalancerRulesIds();
        if (lbRuleIdsToremove == null || lbRuleIdsToremove.isEmpty()) {
            throw new InvalidParameterValueException("empty list of load balancer rule Ids specified to be un-assigned" + " to global load balancer rule");
        }

        // get the active list of LB rule id's that are assigned currently to GSLB rule and corresponding zone id's
        List<Long> oldLbRuleIds = new ArrayList<Long>();
        List<Long> oldZones = new ArrayList<Long>();

        List<GlobalLoadBalancerLbRuleMapVO> gslbLbMapVos = _gslbLbMapDao.listByGslbRuleId(gslbRuleId);
        if (gslbLbMapVos == null) {
            throw new InvalidParameterValueException(" There are no load balancer rules that are assigned to global " + " load balancer rule id: " + gslbRule.getUuid() +
                " that are available for deletion");
        }

        for (Long lbRuleId : lbRuleIdsToremove) {
            LoadBalancerVO loadBalancer = _lbDao.findById(lbRuleId);
            if (loadBalancer == null) {
                throw new InvalidParameterValueException("Specified load balancer rule ID does not exist.");
            }

            _accountMgr.checkAccess(caller, null, true, loadBalancer);
        }

        for (GlobalLoadBalancerLbRuleMapVO gslbLbMapVo : gslbLbMapVos) {
            LoadBalancerVO loadBalancer = _lbDao.findById(gslbLbMapVo.getLoadBalancerId());
            Network network = _networkDao.findById(loadBalancer.getNetworkId());
            oldLbRuleIds.add(gslbLbMapVo.getLoadBalancerId());
            oldZones.add(network.getDataCenterId());
        }

        for (Long lbRuleId : lbRuleIdsToremove) {
            LoadBalancerVO loadBalancer = _lbDao.findById(lbRuleId);
            if (oldLbRuleIds != null && !oldLbRuleIds.contains(loadBalancer.getId())) {
                throw new InvalidParameterValueException("Load balancer ID " + loadBalancer.getUuid() + " is not assigned" + " to global load balancer rule: " +
                    gslbRule.getUuid());
            }
        }

        Transaction.execute(new TransactionCallbackNoReturn() {
            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                // update the mapping of gslb rule to Lb rule, to revoke state
                for (Long lbRuleId : lbRuleIdsToremove) {
                    GlobalLoadBalancerLbRuleMapVO removeGslbLbMap = _gslbLbMapDao.findByGslbRuleIdAndLbRuleId(gslbRuleId, lbRuleId);
                    removeGslbLbMap.setRevoke(true);
                    _gslbLbMapDao.update(removeGslbLbMap.getId(), removeGslbLbMap);
                }

                // mark the gslb rule state as add
                if (gslbRule.getState() == GlobalLoadBalancerRule.State.Staged) {
                    gslbRule.setState(GlobalLoadBalancerRule.State.Add);
                    _gslbRuleDao.update(gslbRule.getId(), gslbRule);
                }

            }
        });

        boolean success = false;
        try {
            s_logger.debug("Attempting to configure global load balancer rule configuration on the gslb service providers ");

            // apply the gslb rule on to the back end gslb service providers
            if (!applyGlobalLoadBalancerRuleConfig(gslbRuleId, false)) {
                s_logger.warn("Failed to remove load balancer rules " + lbRuleIdsToremove + " from global load balancer rule id " + gslbRuleId);
                CloudRuntimeException ex = new CloudRuntimeException("Failed to remove load balancer rule ids from GSLB rule ");
                throw ex;
            }

            Transaction.execute(new TransactionCallbackNoReturn() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    // remove the mappings of gslb rule to Lb rule that are in revoked state
                    for (Long lbRuleId : lbRuleIdsToremove) {
                        GlobalLoadBalancerLbRuleMapVO removeGslbLbMap = _gslbLbMapDao.findByGslbRuleIdAndLbRuleId(gslbRuleId, lbRuleId);
                        _gslbLbMapDao.remove(removeGslbLbMap.getId());
                    }

                    // on success set state back to Active
                    gslbRule.setState(GlobalLoadBalancerRule.State.Active);
                    _gslbRuleDao.update(gslbRule.getId(), gslbRule);

                }
            });

            success = true;
        } catch (ResourceUnavailableException e) {
            throw new CloudRuntimeException("Failed to update removed load balancer details from gloabal load balancer");
        }

        return success;
    }

};