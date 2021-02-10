//,temp,TestNetworkPacketTaggingHandlerImpl.java,90,104,temp,TestTrafficControlBandwidthHandlerImpl.java,94,113
//,3
public class xxx {
  @Test
  public void testBootstrap() {
    TrafficControlBandwidthHandlerImpl handlerImpl = new
        TrafficControlBandwidthHandlerImpl(privilegedOperationExecutorMock,
        cGroupsHandlerMock, trafficControllerMock);

    try {
      handlerImpl.bootstrap(conf);
      verify(cGroupsHandlerMock).initializeCGroupController(
          eq(CGroupsHandler.CGroupController.NET_CLS));
      verifyNoMoreInteractions(cGroupsHandlerMock);
      verify(trafficControllerMock).bootstrap(eq(device),
          eq(ROOT_BANDWIDTH_MBIT),
          eq(YARN_BANDWIDTH_MBIT));
      verifyNoMoreInteractions(trafficControllerMock);
    } catch (ResourceHandlerException e) {
      LOG.error("Unexpected exception: " + e);
      Assert.fail("Caught unexpected ResourceHandlerException!");
    }
  }

};