//,temp,DataTransferTestUtil.java,290,299,temp,DataTransferTestUtil.java,264,272
//,3
public class xxx {
    @Override
    public void run(DatanodeID id) throws IOException {
      final DataTransferTest test = getDataTransferTest();
      if (test.isNotSuccessAndLastPipelineContains(index, id)) {
        final String s = toString(id);
        FiTestUtil.LOG.info(s);
        throw new IOException(s);
      }
    }

};