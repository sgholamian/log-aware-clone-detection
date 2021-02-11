//,temp,GlobalLoadBalancingRulesServiceImplTest.java,934,977,temp,GlobalLoadBalancingRulesServiceImplTest.java,503,564
//,3
public class xxx {
    void runAssignToGlobalLoadBalancerRuleTest() throws Exception {

        TransactionLegacy txn = TransactionLegacy.open("runAssignToGlobalLoadBalancerRuleTest");

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

        Account account = new AccountVO("testaccount", 1, "networkdomain", (short)0, UUID.randomUUID().toString());
        when(gslbServiceImpl._accountMgr.getAccount(anyLong())).thenReturn(account);

        Field gslbRuleId = _class.getDeclaredField("id");
        gslbRuleId.setAccessible(true);
        gslbRuleId.set(assignCmd, new Long(1));

        GlobalLoadBalancerRuleVO gslbRule =
            new GlobalLoadBalancerRuleVO("test-gslb-rule", "test-gslb-rule", "test-domain", "roundrobin", "sourceip", "tcp", 1, 1, 1, GlobalLoadBalancerRule.State.Active);
        when(gslbServiceImpl._gslbRuleDao.findById(new Long(1))).thenReturn(gslbRule);

        LoadBalancerVO lbRule = new LoadBalancerVO();
        lbRule.setState(FirewallRule.State.Active);
        Field networkIdField = LoadBalancerVO.class.getSuperclass().getDeclaredField("networkId");
        networkIdField.setAccessible(true);
        networkIdField.set(lbRule, new Long(1));
        Field sourceIpAddressId = LoadBalancerVO.class.getSuperclass().getDeclaredField("sourceIpAddressId");
        sourceIpAddressId.setAccessible(true);
        sourceIpAddressId.set(lbRule, new Long(1));

        when(gslbServiceImpl._lbDao.findById(new Long(1))).thenReturn(lbRule);
        Field lbRules = _class.getDeclaredField("loadBalancerRulesIds");
        lbRules.setAccessible(true);
        List<Long> lbRuleIds = new ArrayList<Long>();
        lbRuleIds.add(new Long(1));
        lbRules.set(assignCmd, lbRuleIds);

        NetworkVO networkVo = new NetworkVO();
        Field dcID = NetworkVO.class.getDeclaredField("dataCenterId");
        dcID.setAccessible(true);
        dcID.set(networkVo, new Long(1));
        when(gslbServiceImpl._networkDao.findById(new Long(1))).thenReturn(networkVo);

        IPAddressVO ip = new IPAddressVO(new Ip("10.1.1.1"), 1, 1, 1, true);
        when(gslbServiceImpl._ipAddressDao.findById(new Long(1))).thenReturn(ip);

        try {
            gslbServiceImpl.assignToGlobalLoadBalancerRule(assignCmd);
        } catch (Exception e) {
            s_logger.info("exception in testing runAssignToGlobalLoadBalancerRuleTest message: " + e.toString());
        }
    }

};