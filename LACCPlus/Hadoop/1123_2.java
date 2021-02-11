//,temp,DataTransferTestUtil.java,264,272,temp,DataTransferTestUtil.java,243,251
//,2
public class xxx {
    @Override
    public void run(DatanodeID id) throws DiskOutOfSpaceException {
      final DataTransferTest test = getDataTransferTest();
      if (test.isNotSuccessAndLastPipelineContains(index, id)) {
        final String s = toString(id);
        FiTestUtil.LOG.info(s);
        throw new DiskOutOfSpaceException(s);
      }
    }

};