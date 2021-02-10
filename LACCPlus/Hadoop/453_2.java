//,temp,TestTrafficController.java,170,220,temp,TestTrafficController.java,132,168
//,3
public class xxx {
  @Test
  public void testBootstrapRecoveryDisabled() {
    conf.setBoolean(YarnConfiguration.NM_RECOVERY_ENABLED, false);

    TrafficController trafficController = new TrafficController(conf,
        privilegedOperationExecutorMock);

    try {
      trafficController
          .bootstrap(DEVICE, ROOT_BANDWIDTH_MBIT, YARN_BANDWIDTH_MBIT);

      ArgumentCaptor<PrivilegedOperation> opCaptor = ArgumentCaptor.forClass
          (PrivilegedOperation.class);

      //NM_RECOVERY_DISABLED - so we expect two privileged operation executions
      //one for wiping tc state - a second for initializing state
      verify(privilegedOperationExecutorMock, times(2))
          .executePrivilegedOperation(opCaptor.capture(), eq(false));

      //Now verify that the two operations were correct
      List<PrivilegedOperation> ops = opCaptor.getAllValues();

      verifyTrafficControlOperation(ops.get(0),
          PrivilegedOperation.OperationType.TC_MODIFY_STATE,
          Arrays.asList(WIPE_STATE_CMD));

      verifyTrafficControlOperation(ops.get(1),
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