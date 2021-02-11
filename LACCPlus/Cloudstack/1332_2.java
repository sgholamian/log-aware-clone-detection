//,temp,GlobalLoadBalancingRulesServiceImpl.java,304,416,temp,GlobalLoadBalancingRulesServiceImpl.java,167,302
//,3
public class xxx {
    @Override
    @DB
    @ActionEvent(eventType = EventTypes.EVENT_ASSIGN_TO_GLOBAL_LOAD_BALANCER_RULE,
                 eventDescription = "Assigning a load balancer rule to global load balancer rule",
                 async = true)
    public boolean assignToGlobalLoadBalancerRule(AssignToGlobalLoadBalancerRuleCmd assignToGslbCmd) {

        CallContext ctx = CallContext.current();
        Account caller = ctx.getCallingAccount();

        final long gslbRuleId = assignToGslbCmd.getGlobalLoadBalancerRuleId();
        final GlobalLoadBalancerRuleVO gslbRule = _gslbRuleDao.findById(gslbRuleId);
        if (gslbRule == null) {
            throw new InvalidParameterValueException("Invalid global load balancer rule id: " + gslbRuleId);
        }

        _accountMgr.checkAccess(caller, SecurityChecker.AccessType.OperateEntry, true, gslbRule);

        if (gslbRule.getState() == GlobalLoadBalancerRule.State.Revoke) {
            throw new InvalidParameterValueException("global load balancer rule id: " + gslbRule.getUuid() + " is in revoked state");
        }

        final List<Long> newLbRuleIds = assignToGslbCmd.getLoadBalancerRulesIds();
        if (newLbRuleIds == null || newLbRuleIds.isEmpty()) {
            throw new InvalidParameterValueException("empty list of load balancer rule Ids specified to be assigned" + " global load balancer rule");
        }

        List<Long> oldLbRuleIds = new ArrayList<Long>();
        List<Long> oldZones = new ArrayList<Long>();
        List<Long> newZones = new ArrayList<Long>(oldZones);
        List<Pair<Long, Long>> physcialNetworks = new ArrayList<Pair<Long, Long>>();

        // get the list of load balancer rules id's that are assigned currently to GSLB rule and corresponding zone id's
        List<GlobalLoadBalancerLbRuleMapVO> gslbLbMapVos = _gslbLbMapDao.listByGslbRuleId(gslbRuleId);
        if (gslbLbMapVos != null) {
            for (GlobalLoadBalancerLbRuleMapVO gslbLbMapVo : gslbLbMapVos) {
                LoadBalancerVO loadBalancer = _lbDao.findById(gslbLbMapVo.getLoadBalancerId());
                Network network = _networkDao.findById(loadBalancer.getNetworkId());
                oldZones.add(network.getDataCenterId());
                oldLbRuleIds.add(gslbLbMapVo.getLoadBalancerId());
            }
        }

        /* check each of the load balancer rule id passed in the 'AssignToGlobalLoadBalancerRuleCmd' command is
         *     valid ID
         *     caller has access to the rule
         *     check rule is not revoked
         *     no two rules are in same zone
         *     rule is not already assigned to gslb rule
         */
        for (Long lbRuleId : newLbRuleIds) {

            LoadBalancerVO loadBalancer = _lbDao.findById(lbRuleId);
            if (loadBalancer == null) {
                throw new InvalidParameterValueException("Specified load balancer rule ID does not exist.");
            }

            _accountMgr.checkAccess(caller, null, true, loadBalancer);

            if (gslbRule.getAccountId() != loadBalancer.getAccountId()) {
                throw new InvalidParameterValueException("GSLB rule and load balancer rule does not belong to same account");
            }

            if (loadBalancer.getState() == LoadBalancer.State.Revoke) {
                throw new InvalidParameterValueException("Load balancer ID " + loadBalancer.getUuid() + " is in revoke state");
            }

            if (oldLbRuleIds != null && oldLbRuleIds.contains(loadBalancer.getId())) {
                throw new InvalidParameterValueException("Load balancer ID " + loadBalancer.getUuid() + " is already assigned");
            }

            Network network = _networkDao.findById(loadBalancer.getNetworkId());

            if (oldZones != null && oldZones.contains(network.getDataCenterId()) || newZones != null && newZones.contains(network.getDataCenterId())) {
                throw new InvalidParameterValueException("Load balancer rule specified should be in unique zone");
            }

            newZones.add(network.getDataCenterId());
            physcialNetworks.add(new Pair<Long, Long>(network.getDataCenterId(), network.getPhysicalNetworkId()));
        }

        // for each of the physical network check if GSLB service provider configured
        for (Pair<Long, Long> physicalNetwork : physcialNetworks) {
            if (!checkGslbServiceEnabledInZone(physicalNetwork.first(), physicalNetwork.second())) {
                throw new InvalidParameterValueException("GSLB service is not enabled in the Zone:" + physicalNetwork.first() + " and physical network " +
                    physicalNetwork.second());
            }
        }

        final Map<Long, Long> lbRuleWeightMap = assignToGslbCmd.getLoadBalancerRuleWeightMap();

        Transaction.execute(new TransactionCallbackNoReturn() {
            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                // persist the mapping for the new Lb rule that needs to assigned to a gslb rule
                for (Long lbRuleId : newLbRuleIds) {
                    GlobalLoadBalancerLbRuleMapVO newGslbLbMap = new GlobalLoadBalancerLbRuleMapVO();
                    newGslbLbMap.setGslbLoadBalancerId(gslbRuleId);
                    newGslbLbMap.setLoadBalancerId(lbRuleId);
                    if (lbRuleWeightMap != null && lbRuleWeightMap.get(lbRuleId) != null) {
                        newGslbLbMap.setWeight(lbRuleWeightMap.get(lbRuleId));
                    }
                    _gslbLbMapDao.persist(newGslbLbMap);
                }

                // mark the gslb rule state as add
                if (gslbRule.getState() == GlobalLoadBalancerRule.State.Staged || gslbRule.getState() == GlobalLoadBalancerRule.State.Active) {
                    gslbRule.setState(GlobalLoadBalancerRule.State.Add);
                    _gslbRuleDao.update(gslbRule.getId(), gslbRule);
                }
            }
        });

        boolean success = false;
        try {
            s_logger.debug("Configuring gslb rule configuration on the gslb service providers in the participating zones");

            // apply the gslb rule on to the back end gslb service providers on zones participating in gslb
            if (!applyGlobalLoadBalancerRuleConfig(gslbRuleId, false)) {
                s_logger.warn("Failed to add load balancer rules " + newLbRuleIds + " to global load balancer rule id " + gslbRuleId);
                CloudRuntimeException ex = new CloudRuntimeException("Failed to add load balancer rules to GSLB rule ");
                throw ex;
            }

            // on success set state to Active
            gslbRule.setState(GlobalLoadBalancerRule.State.Active);
            _gslbRuleDao.update(gslbRule.getId(), gslbRule);

            success = true;

        } catch (ResourceUnavailableException e) {
            throw new CloudRuntimeException("Failed to apply new GSLB configuration while assigning new LB rules to GSLB rule.");
        }

        return success;
    }

};