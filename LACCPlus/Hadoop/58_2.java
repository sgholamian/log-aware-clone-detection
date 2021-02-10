//,temp,TestBPOfferService.java,438,450,temp,TestDatanodeProtocolRetryPolicy.java,135,148
//,3
public class xxx {
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

};