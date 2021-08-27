//,temp,AMQ3732Test.java,115,127,temp,AMQ3732Test.java,97,109
//,3
public class xxx {
            @Override
            public void run() {
                try {
                    while (totalConsumed.get() < NUM_MESSAGES) {
                        Message message = consumer2.receive(50);
                        if (message != null) {
                            workQueue.add(message);
                        }
                    }
                } catch(Exception e) {
                    LOG.error("Caught an unexpected error: ", e);
                }
            }

};