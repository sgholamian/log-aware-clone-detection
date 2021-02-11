//,temp,GlobalLoadBalancingRulesServiceImplTest.java,503,564,temp,GlobalLoadBalancingRulesServiceImplTest.java,182,244
//,3
public class xxx {
    void runCreateGlobalLoadBalancerRulePostiveTest() throws Exception {

        TransactionLegacy txn = TransactionLegacy.open("runCreateGlobalLoadBalancerRulePostiveTest");

        GlobalLoadBalancingRulesServiceImpl gslbServiceImpl = new GlobalLoadBalancingRulesServiceImpl();

        gslbServiceImpl._accountMgr = Mockito.mock(AccountManager.class);
        Account account = new AccountVO("testaccount", 1, "networkdomain", (short)0, UUID.randomUUID().toString());
        when(gslbServiceImpl._accountMgr.getAccount(anyLong())).thenReturn(account);

        gslbServiceImpl._gslbRuleDao = Mockito.mock(GlobalLoadBalancerRuleDao.class);
        when(gslbServiceImpl._gslbRuleDao.persist(any(GlobalLoadBalancerRuleVO.class))).thenReturn(new GlobalLoadBalancerRuleVO());
        gslbServiceImpl._gslbLbMapDao = Mockito.mock(GlobalLoadBalancerLbRuleMapDao.class);

        gslbServiceImpl._regionDao = Mockito.mock(RegionDao.class);
        RegionVO region = new RegionVO();
        region.setGslbEnabled(true);
        when(gslbServiceImpl._regionDao.findById(anyInt())).thenReturn(region);

        gslbServiceImpl._rulesMgr = Mockito.mock(RulesManager.class);
        gslbServiceImpl._lbDao = Mockito.mock(LoadBalancerDao.class);
        gslbServiceImpl._networkDao = Mockito.mock(NetworkDao.class);
        gslbServiceImpl._globalConfigDao = Mockito.mock(ConfigurationDao.class);
        gslbServiceImpl._ipAddressDao = Mockito.mock(IPAddressDao.class);
        gslbServiceImpl._agentMgr = Mockito.mock(AgentManager.class);

        CreateGlobalLoadBalancerRuleCmd createCmd = new CreateGlobalLoadBalancerRuleCmdExtn();
        Class<?> _class = createCmd.getClass().getSuperclass();

        Field regionIdField = _class.getDeclaredField("regionId");
        regionIdField.setAccessible(true);
        regionIdField.set(createCmd, new Integer(1));

        Field algoField = _class.getDeclaredField("algorithm");
        algoField.setAccessible(true);
        algoField.set(createCmd, "roundrobin");

        Field stickyField = _class.getDeclaredField("stickyMethod");
        stickyField.setAccessible(true);
        stickyField.set(createCmd, "sourceip");

        Field nameField = _class.getDeclaredField("globalLoadBalancerRuleName");
        nameField.setAccessible(true);
        nameField.set(createCmd, "gslb-rule");

        Field descriptionField = _class.getDeclaredField("description");
        descriptionField.setAccessible(true);
        descriptionField.set(createCmd, "testing create gslb-rule");

        Field serviceDomainField = _class.getDeclaredField("serviceDomainName");
        serviceDomainField.setAccessible(true);
        serviceDomainField.set(createCmd, "gslb-rule-domain");

        Field serviceTypeField = _class.getDeclaredField("serviceType");
        serviceTypeField.setAccessible(true);
        serviceTypeField.set(createCmd, "tcp");

        try {
            gslbServiceImpl.createGlobalLoadBalancerRule(createCmd);
        } catch (Exception e) {
            s_logger.info("exception in testing runCreateGlobalLoadBalancerRulePostiveTest message: " + e.toString());
        }
    }

};