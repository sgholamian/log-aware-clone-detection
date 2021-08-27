//,temp,JmsCronSchedulerTest.java,62,74,temp,AMQ3405Test.java,261,278
//,3
public class xxx {
        @Override
        public void onMessage(Message message) {
            try {
                int expectedMessageId = rollbacks.get() / deliveryCount;
                LOG.info("expecting messageId: " + expectedMessageId);
                rollbacks.incrementAndGet();
                session.rollback();
            } catch (Throwable e) {
                LOG.error("unexpected exception:" + e, e);
                // propagating assertError to execution task will cause a hang
                // at shutdown
                if (e instanceof Error) {
                    error[0] = (Error) e;
                } else {
                    fail("unexpected exception: " + e);
                }
            }
        }

};