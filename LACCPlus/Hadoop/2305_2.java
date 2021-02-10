//,temp,TestBPOfferService.java,483,496,temp,TestDatanodeProtocolRetryPolicy.java,133,151
//,3
public class xxx {
  private void waitForBlockReport(
      final DatanodeProtocolClientSideTranslatorPB mockNN) throws Exception {
    GenericTestUtils.waitFor(new Supplier<Boolean>() {
      @Override
      public Boolean get() {
        try {
          Mockito.verify(mockNN).blockReport(
              Mockito.eq(datanodeRegistration),
              Mockito.eq(POOL_ID),
              Mockito.<StorageBlockReport[]>anyObject(),
              Mockito.<BlockReportContext>anyObject());
          return true;
        } catch (Throwable t) {
          LOG.info("waiting on block report: " + t.getMessage());
          return false;
        }
      }
    }, 500, 100000);
  }

};