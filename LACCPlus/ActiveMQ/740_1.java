//,temp,StompTest.java,2320,2324,temp,JDBCMessagePriorityTest.java,261,265
//,3
public class xxx {
                @Override
                public boolean isSatisified() throws Exception {
                    LOG.info("ext: " + abstractSubscription.getPrefetchExtension().get());
                    return abstractSubscription.getPrefetchExtension().get() == 0;
                }

};