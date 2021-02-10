//,temp,TestSaveNamespace.java,122,134,temp,TestDatanodeProtocolRetryPolicy.java,170,185
//,3
public class xxx {
      @Override
      public DatanodeRegistration answer(InvocationOnMock invocation)
          throws Throwable {
        i++;
        if ( i > 1 && i < 5) {
          LOG.info("mockito exception " + i);
          throw new EOFException("TestDatanodeProtocolRetryPolicy");
        } else {
          DatanodeRegistration dr =
              (DatanodeRegistration) invocation.getArguments()[0];
          datanodeRegistration =
              new DatanodeRegistration(dr.getDatanodeUuid(), dr);
          LOG.info("mockito succeeded " + datanodeRegistration);
          return datanodeRegistration;
        }
      }

};