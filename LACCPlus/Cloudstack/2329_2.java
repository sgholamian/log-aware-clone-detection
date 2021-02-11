//,temp,ConsoleProxyManagerTest.java,104,116,temp,ConsoleProxyManagerTest.java,76,90
//,3
public class xxx {
    @Test
    public void testNewCPVMCreation() throws Exception {
        s_logger.info("Running test for new CPVM creation");

        // No existing CPVM
        Mockito.when(cpvmManager.assignProxyFromStoppedPool(Mockito.anyLong())).thenReturn(null);
        // Allocate a new one
        Mockito.when(globalLock.lock(Mockito.anyInt())).thenReturn(true);
        Mockito.when(globalLock.unlock()).thenReturn(true);
        Mockito.when(cpvmManager.startNew(Mockito.anyLong())).thenReturn(proxyVO);
        // Start CPVM
        Mockito.when(cpvmManager.startProxy(Mockito.anyLong(), Mockito.anyBoolean())).thenReturn(proxyVO);

        cpvmManager.expandPool(new Long(1), new Object());
    }

};