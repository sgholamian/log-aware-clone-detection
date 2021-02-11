//,temp,ConfigurationManagerTest.java,404,426,temp,ConfigurationManagerTest.java,279,304
//,3
public class xxx {
    void runReleasePublicIpRangePostiveTest1() throws Exception {
        TransactionLegacy txn = TransactionLegacy.open("runReleasePublicIpRangePostiveTest1");

        when(configurationMgr._vlanDao.findById(anyLong())).thenReturn(vlan);

        List<AccountVlanMapVO> accountVlanMaps = new ArrayList<AccountVlanMapVO>();
        AccountVlanMapVO accountVlanMap = new AccountVlanMapVO(1, 1);
        accountVlanMaps.add(accountVlanMap);
        when(configurationMgr._accountVlanMapDao.listAccountVlanMapsByVlan(anyLong())).thenReturn(accountVlanMaps);

        // no allocated ip's
        when(configurationMgr._publicIpAddressDao.countIPs(anyLong(), anyLong(), anyBoolean())).thenReturn(0);

        when(configurationMgr._accountVlanMapDao.remove(anyLong())).thenReturn(true);
        try {
            Boolean result = configurationMgr.releasePublicIpRange(releasePublicIpRangesCmd);
            Assert.assertTrue(result);
        } catch (Exception e) {
            s_logger.info("exception in testing runReleasePublicIpRangePostiveTest1 message: " + e.toString());
        } finally {
            txn.close("runReleasePublicIpRangePostiveTest1");
        }
    }

};