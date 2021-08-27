//,temp,DuplexAdvisoryRaceTest.java,146,150,temp,JMSClientTransactionTest.java,286,290
//,3
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.info("Read {} messages so far", counter.get());
                return counter.get() == MSG_COUNT * 2;
            }

};