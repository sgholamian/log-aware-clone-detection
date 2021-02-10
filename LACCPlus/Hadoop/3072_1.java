//,temp,TestBPOfferService.java,500,524,temp,TestBPOfferService.java,480,498
//,3
public class xxx {
  private void waitForBlockReport(
      final DatanodeProtocolClientSideTranslatorPB mockNN1,
      final DatanodeProtocolClientSideTranslatorPB mockNN2)
          throws Exception {
    GenericTestUtils.waitFor(new Supplier<Boolean>() {
      @Override
      public Boolean get() {
        return get(mockNN1) || get(mockNN2);
      }

      private Boolean get(DatanodeProtocolClientSideTranslatorPB mockNN) {
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