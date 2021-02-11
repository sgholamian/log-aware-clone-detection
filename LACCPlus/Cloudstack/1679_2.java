//,temp,ConsoleProxyManagerTest.java,104,116,temp,ConsoleProxyManagerTest.java,92,102
//,3
public class xxx {
    @Test
    public void testExistingCPVMStart() throws Exception {
        s_logger.info("Running test for existing CPVM start");

        // CPVM already exists
        Mockito.when(cpvmManager.assignProxyFromStoppedPool(Mockito.anyLong())).thenReturn(proxyVO);
        // Start CPVM
        Mockito.when(cpvmManager.startProxy(Mockito.anyLong(), Mockito.anyBoolean())).thenReturn(proxyVO);

        cpvmManager.expandPool(new Long(1), new Object());
    }

};