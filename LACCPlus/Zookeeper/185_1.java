//,temp,CommitProcessorTest.java,507,515,temp,FollowerRequestProcessor.java,145,151
//,3
public class xxx {
        @Override
        public void shutdown() {
            LOG.info("shutdown validateReadRequestVariant");
            cxidMap.clear();
            expectedZxid = new AtomicLong(1);
            if (nextProcessor!=null){
                nextProcessor.shutdown();
            }
        }

};