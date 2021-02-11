//,temp,DataTransferTestUtil.java,243,251,temp,DataTransferTestUtil.java,203,211
//,3
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