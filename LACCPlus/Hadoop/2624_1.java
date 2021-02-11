//,temp,TestNetworkPacketTaggingHandlerImpl.java,90,104,temp,TestTrafficControlBandwidthHandlerImpl.java,94,113
//,3
public class xxx {
  @Test
  public void testBootstrap() {
    NetworkPacketTaggingHandlerImpl handlerImpl =
        createNetworkPacketTaggingHandlerImpl();

    try {
      handlerImpl.bootstrap(conf);
      verify(cGroupsHandlerMock).initializeCGroupController(
          eq(CGroupsHandler.CGroupController.NET_CLS));
      verifyNoMoreInteractions(cGroupsHandlerMock);
    } catch (ResourceHandlerException e) {
      LOG.error("Unexpected exception: " + e);
      Assert.fail("Caught unexpected ResourceHandlerException!");
    }
  }

};