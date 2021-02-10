//,temp,TestRMContainerAllocator.java,1848,1858,temp,TestRMContainerAllocator.java,1793,1803
//,2
public class xxx {
    public MyFifoScheduler(RMContext rmContext) {
      super();
      try {
        Configuration conf = new Configuration();
        init(conf);
        reinitialize(conf, rmContext);
      } catch (IOException ie) {
        LOG.info("add application failed with ", ie);
        assert (false);
      }
    }

};