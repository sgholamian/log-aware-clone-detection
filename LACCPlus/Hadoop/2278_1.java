//,temp,TestSaveNamespace.java,122,134,temp,TestDatanodeProtocolRetryPolicy.java,170,185
//,3
public class xxx {
    @Override
    public Void answer(InvocationOnMock invocation) throws Throwable {
      Object[] args = invocation.getArguments();
      StorageDirectory sd = (StorageDirectory)args[0];

      if (faultType == Fault.WRITE_STORAGE_ALL ||
          (faultType==Fault.WRITE_STORAGE_ONE && count++==1)) {
        LOG.info("Injecting fault for sd: " + sd);
        throw new IOException("Injected fault: writeProperties second time");
      }
      LOG.info("Not injecting fault for sd: " + sd);
      return (Void)invocation.callRealMethod();
    }

};