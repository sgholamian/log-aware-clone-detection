//,temp,ConsoleProxyManagerTest.java,104,116,temp,ConsoleProxyManagerTest.java,92,102
//,3
public class xxx {
    @Test
    public void testExisingCPVMStartFailure() throws Exception {
        s_logger.info("Running test for existing CPVM start failure");

        // CPVM already exists
        Mockito.when(cpvmManager.assignProxyFromStoppedPool(Mockito.anyLong())).thenReturn(proxyVO);
        // Start CPVM
        Mockito.when(cpvmManager.startProxy(Mockito.anyLong(), Mockito.anyBoolean())).thenReturn(null);
        // Destroy existing CPVM, so that a new one is created subsequently
        Mockito.when(cpvmManager.destroyProxy(Mockito.anyLong())).thenReturn(true);

        cpvmManager.expandPool(new Long(1), new Object());
    }

};