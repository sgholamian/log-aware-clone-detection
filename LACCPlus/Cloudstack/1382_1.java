//,temp,GlobalLoadBalancingRulesServiceImplTest.java,934,977,temp,GlobalLoadBalancingRulesServiceImplTest.java,888,932
//,3
public class xxx {
    void runDeleteGlobalLoadBalancerRuleTestWithLbRules() throws Exception {

        TransactionLegacy txn = TransactionLegacy.open("runDeleteGlobalLoadBalancerRuleTestWithLbRules");

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

        DeleteGlobalLoadBalancerRuleCmd deleteCmd = new DeleteGlobalLoadBalancerRuleCmdExtn();
        Class<?> _class = deleteCmd.getClass().getSuperclass();

        Account account = new AccountVO("testaccount", 1, "networkdomain", (short)0, UUID.randomUUID().toString());
        when(gslbServiceImpl._accountMgr.getAccount(anyLong())).thenReturn(account);

        Field gslbRuleId = _class.getDeclaredField("id");
        gslbRuleId.setAccessible(true);
        gslbRuleId.set(deleteCmd, new Long(1));

        GlobalLoadBalancerRuleVO gslbRule =
            new GlobalLoadBalancerRuleVO("test-gslb-rule", "test-gslb-rule", "test-domain", "roundrobin", "sourceip", "tcp", 1, 1, 1, GlobalLoadBalancerRule.State.Active);
        when(gslbServiceImpl._gslbRuleDao.findById(new Long(1))).thenReturn(gslbRule);

        GlobalLoadBalancerLbRuleMapVO gslbLmMap = new GlobalLoadBalancerLbRuleMapVO(1, 1, 1);
        List<GlobalLoadBalancerLbRuleMapVO> gslbLbMapVos = new ArrayList<GlobalLoadBalancerLbRuleMapVO>();
        gslbLbMapVos.add(gslbLmMap);
        when(gslbServiceImpl._gslbLbMapDao.listByGslbRuleId(new Long(1))).thenReturn(gslbLbMapVos);

        try {
            gslbServiceImpl.deleteGlobalLoadBalancerRule(deleteCmd);
            Assert.assertTrue(gslbRule.getState() == GlobalLoadBalancerRule.State.Revoke);
            Assert.assertTrue(gslbLmMap.isRevoke() == true);
        } catch (Exception e) {
            s_logger.info("exception in testing runDeleteGlobalLoadBalancerRuleTestWithLbRules. " + e.toString());
        }
    }

};