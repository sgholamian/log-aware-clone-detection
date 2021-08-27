//,temp,StompTest.java,2320,2324,temp,JDBCMessagePriorityTest.java,261,265
//,3
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.info("count: " + count.get());
                return TO_SEND * 2 == count.get();
            }

};