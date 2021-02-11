//,temp,ConfigurationManagerTest.java,404,426,temp,ConfigurationManagerTest.java,279,304
//,3
public class xxx {
    void runDedicatePublicIpRangePostiveTest() throws Exception {
        TransactionLegacy txn = TransactionLegacy.open("runDedicatePublicIpRangePostiveTest");

        when(configurationMgr._vlanDao.findById(anyLong())).thenReturn(vlan);

        when(configurationMgr._accountVlanMapDao.listAccountVlanMapsByAccount(anyLong())).thenReturn(null);

        DataCenterVO dc =
            new DataCenterVO(UUID.randomUUID().toString(), "test", "8.8.8.8", null, "10.0.0.1", null, "10.0.0.1/24", null, null, NetworkType.Advanced, null, null, true,
                true, null, null);
        when(configurationMgr._zoneDao.findById(anyLong())).thenReturn(dc);

        List<IPAddressVO> ipAddressList = new ArrayList<IPAddressVO>();
        IPAddressVO ipAddress = new IPAddressVO(new Ip("75.75.75.75"), 1, 0xaabbccddeeffL, 10, false);
        ipAddressList.add(ipAddress);
        when(configurationMgr._publicIpAddressDao.listByVlanId(anyLong())).thenReturn(ipAddressList);

        try {
            Vlan result = configurationMgr.dedicatePublicIpRange(dedicatePublicIpRangesCmd);
            Assert.assertNotNull(result);
        } catch (Exception e) {
            s_logger.info("exception in testing runDedicatePublicIpRangePostiveTest message: " + e.toString());
        } finally {
            txn.close("runDedicatePublicIpRangePostiveTest");
        }
    }

};