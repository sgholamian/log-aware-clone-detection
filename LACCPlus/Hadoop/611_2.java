//,temp,DataTransferTestUtil.java,264,272,temp,DataTransferTestUtil.java,224,233
//,3
public class xxx {
    @Override
    public void run(DatanodeID id) {
      final DataTransferTest test = getDataTransferTest();
      if (test.isNotSuccessAndLastPipelineContains(index, id)
          && countdown.isSatisfied()) {
        final String s = toString(id);
        FiTestUtil.LOG.info(s);
        throw new OutOfMemoryError(s);
      }
    }

};