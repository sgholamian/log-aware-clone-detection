//,temp,TestTrafficController.java,170,220,temp,TestTrafficController.java,132,168
//,3
public class xxx {
  @Test
  public void testBootstrapRecoveryEnabled() {
    conf.setBoolean(YarnConfiguration.NM_RECOVERY_ENABLED, true);

    TrafficController trafficController = new TrafficController(conf,
        privilegedOperationExecutorMock);

    try {
      //Return a default tc state when attempting to read state
      when(privilegedOperationExecutorMock.executePrivilegedOperation(
          any(PrivilegedOperation.class), eq(true)))
          .thenReturn(DEFAULT_TC_STATE_EXAMPLE);

      trafficController
          .bootstrap(DEVICE, ROOT_BANDWIDTH_MBIT, YARN_BANDWIDTH_MBIT);

      ArgumentCaptor<PrivilegedOperation> readOpCaptor = ArgumentCaptor.forClass
          (PrivilegedOperation.class);

      //NM_RECOVERY_ENABLED - so we expect three privileged operation executions
      //1) read tc state 2) wipe tc state 3) init tc state
      //one for wiping tc state - a second for initializing state
      //First, verify read op
      verify(privilegedOperationExecutorMock, times(1))
          .executePrivilegedOperation(readOpCaptor.capture(), eq(true));
      List<PrivilegedOperation> readOps = readOpCaptor.getAllValues();
      verifyTrafficControlOperation(readOps.get(0),
          PrivilegedOperation.OperationType.TC_READ_STATE,
          Arrays.asList(READ_QDISC_CMD, READ_FILTER_CMD, READ_CLASS_CMD));

      ArgumentCaptor<PrivilegedOperation> writeOpCaptor = ArgumentCaptor
          .forClass(PrivilegedOperation.class);
      verify(privilegedOperationExecutorMock, times(2))
          .executePrivilegedOperation(writeOpCaptor.capture(), eq(false));
      //Now verify that the two write operations were correct
      List<PrivilegedOperation> writeOps = writeOpCaptor.getAllValues();
      verifyTrafficControlOperation(writeOps.get(0),
          PrivilegedOperation.OperationType.TC_MODIFY_STATE,
          Arrays.asList(WIPE_STATE_CMD));

      verifyTrafficControlOperation(writeOps.get(1),
          PrivilegedOperation.OperationType.TC_MODIFY_STATE,
          Arrays.asList(ADD_ROOT_QDISC_CMD, ADD_CGROUP_FILTER_CMD,
              ADD_ROOT_CLASS_CMD, ADD_DEFAULT_CLASS_CMD, ADD_YARN_CLASS_CMD));
    } catch (ResourceHandlerException | PrivilegedOperationException |
        IOException e) {
      LOG.error("Unexpected exception: " + e);
      Assert.fail("Caught unexpected exception: "
          + e.getClass().getSimpleName());
    }
  }

};