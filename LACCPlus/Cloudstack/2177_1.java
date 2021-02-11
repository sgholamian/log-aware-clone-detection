//,temp,GlobalLoadBalancingRulesServiceImplTest.java,566,649,temp,GlobalLoadBalancingRulesServiceImplTest.java,503,564
//,3
public class xxx {
    void runAssignToGlobalLoadBalancerRuleTestSameZoneLb() throws Exception {

        TransactionLegacy txn = TransactionLegacy.open("runAssignToGlobalLoadBalancerRuleTestSameZoneLb");

        GlobalLoadBalancingRulesServiceImpl gslbServiceImpl = new GlobalLoadBalancingRulesServiceImpl();

        gslbServiceImpl._accountMgr = Mockito.mock(AccountManager.class);
        gslbServiceImpl._gslbRuleDao = Mockito.mock(GlobalLoadBalancerRuleDao.class);
        gslbServiceImpl._gslbLbMapDao = Mockito.mock(GlobalLoadBalancerLbRuleMapDao.class);
        gslbServiceImpl._regionDao = Mockito.mock(RegionDao.class);
        gslbServiceImpl._rulesMgr = Mockito.mock(RulesManager.class);
        gslbServiceImpl._lbDao = Mockito.mock(LoadBalancerDao.class);
        gslbServiceImpl._networkDao = Mockito.mock(NetworkDao.class);
        gslbServiceImpl._globalConfigDao = Mockito.mock(ConfigurationDao.class);
        gslbServiceImpl._ipAddressDao = Mockito.mock(IPAddressDao.class);
        gslbServiceImpl._agentMgr = Mockito.mock(AgentManager.class);

        AssignToGlobalLoadBalancerRuleCmd assignCmd = new AssignToGlobalLoadBalancerRuleCmdExtn();
        Class<?> _class = assignCmd.getClass().getSuperclass();

        Account account = new AccountVO("testaccount", 3, "networkdomain", (short)0, UUID.randomUUID().toString());
        when(gslbServiceImpl._accountMgr.getAccount(anyLong())).thenReturn(account);

        Field gslbRuleId = _class.getDeclaredField("id");
        gslbRuleId.setAccessible(true);
        gslbRuleId.set(assignCmd, new Long(1));

        GlobalLoadBalancerRuleVO gslbRule =
            new GlobalLoadBalancerRuleVO("test-gslb-rule", "test-gslb-rule", "test-domain", "roundrobin", "sourceip", "tcp", 1, 3, 1, GlobalLoadBalancerRule.State.Active);
        when(gslbServiceImpl._gslbRuleDao.findById(new Long(1))).thenReturn(gslbRule);

        LoadBalancerVO lbRule1 = new LoadBalancerVO();
        lbRule1.setState(FirewallRule.State.Active);
        Field networkIdField1 = LoadBalancerVO.class.getSuperclass().getDeclaredField("networkId");
        Field accountIdField1 = LoadBalancerVO.class.getSuperclass().getDeclaredField("accountId");
        Field domainIdField1 = LoadBalancerVO.class.getSuperclass().getDeclaredField("domainId");
        networkIdField1.setAccessible(true);
        accountIdField1.setAccessible(true);
        domainIdField1.setAccessible(true);
        networkIdField1.set(lbRule1, new Long(1));
        accountIdField1.set(lbRule1, new Long(3));
        domainIdField1.set(lbRule1, new Long(1));
        Field idField1 = LoadBalancerVO.class.getSuperclass().getDeclaredField("id");
        idField1.setAccessible(true);
        idField1.set(lbRule1, new Long(1));

        LoadBalancerVO lbRule2 = new LoadBalancerVO();
        lbRule2.setState(FirewallRule.State.Active);
        Field networkIdField2 = LoadBalancerVO.class.getSuperclass().getDeclaredField("networkId");
        Field accountIdField2 = LoadBalancerVO.class.getSuperclass().getDeclaredField("accountId");
        Field domainIdField2 = LoadBalancerVO.class.getSuperclass().getDeclaredField("domainId");
        networkIdField2.setAccessible(true);
        accountIdField2.setAccessible(true);
        domainIdField2.setAccessible(true);
        networkIdField2.set(lbRule2, new Long(1));
        accountIdField2.set(lbRule2, new Long(3));
        domainIdField2.set(lbRule2, new Long(1));
        Field idField2 = LoadBalancerVO.class.getSuperclass().getDeclaredField("id");
        idField2.setAccessible(true);
        idField2.set(lbRule2, new Long(2));

        when(gslbServiceImpl._lbDao.findById(new Long(1))).thenReturn(lbRule1);
        when(gslbServiceImpl._lbDao.findById(new Long(2))).thenReturn(lbRule2);

        Field lbRules = _class.getDeclaredField("loadBalancerRulesIds");
        lbRules.setAccessible(true);
        List<Long> lbRuleIds = new ArrayList<Long>();
        lbRuleIds.add(new Long(1));
        lbRuleIds.add(new Long(2));
        lbRules.set(assignCmd, lbRuleIds);

        NetworkVO networkVo = new NetworkVO();
        Field dcID = NetworkVO.class.getDeclaredField("dataCenterId");
        dcID.setAccessible(true);
        dcID.set(networkVo, new Long(1));
        when(gslbServiceImpl._networkDao.findById(new Long(1))).thenReturn(networkVo);

        try {
            gslbServiceImpl.assignToGlobalLoadBalancerRule(assignCmd);
        } catch (InvalidParameterValueException e) {
            s_logger.info(e.getMessage());
            Assert.assertTrue(e.getMessage().contains("Load balancer rule specified should be in unique zone"));
        }
    }

};