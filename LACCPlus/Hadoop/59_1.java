//,temp,TestBPOfferService.java,438,450,temp,TestBPOfferService.java,411,424
//,3
public class xxx {
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

};