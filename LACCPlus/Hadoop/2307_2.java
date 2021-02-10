//,temp,TestBPOfferService.java,510,522,temp,TestBPOfferService.java,480,498
//,3
public class xxx {
  private void waitForBlockReport(final DatanodeProtocolClientSideTranslatorPB mockNN)
      throws Exception {
    GenericTestUtils.waitFor(new Supplier<Boolean>() {
      @Override
      public Boolean get() {
        try {
          Mockito.verify(mockNN).blockReport(
              Mockito.<DatanodeRegistration>anyObject(),  
              Mockito.eq(FAKE_BPID),
              Mockito.<StorageBlockReport[]>anyObject(),
              Mockito.<BlockReportContext>anyObject());
          return true;
        } catch (Throwable t) {
          LOG.info("waiting on block report: " + t.getMessage());
          return false;
        }
      }
    }, 500, 10000);
  }

};