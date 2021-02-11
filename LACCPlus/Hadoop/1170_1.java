//,temp,DataTransferTestUtil.java,290,299,temp,DataTransferTestUtil.java,224,233
//,3
public class xxx {
    @Override
    public void run(DatanodeID id) throws DiskOutOfSpaceException {
      final DataTransferTest test = getDataTransferTest();
      if (test.isNotSuccessAndLastPipelineContains(index, id)
          && countdown.isSatisfied()) {
        final String s = toString(id);
        FiTestUtil.LOG.info(s);
        throw new DiskOutOfSpaceException(s);
      }
    }

};