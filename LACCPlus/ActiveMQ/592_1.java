//,temp,TransactionNotStartedErrorTest.java,214,234,temp,MissingDataFileTest.java,252,272
//,2
public class xxx {
    protected void waitForMessagesToBeDelivered() {
        // let's give the listeners enough time to read all messages
        long maxWaitTime = counter * 3000;
        long waitTime = maxWaitTime;
        long start = (maxWaitTime <= 0) ? 0 : System.currentTimeMillis();

        synchronized (lock) {
            boolean hasMessages = true;
            while (hasMessages && waitTime >= 0) {
                try {
                    lock.wait(200);
                } catch (InterruptedException e) {
                    LOG.error(e.toString());
                }
                // check if all messages have been received
                hasMessages = hectorToHaloCtr < counter || xenaToHaloCtr < counter || troyToHaloCtr < counter || haloToHectorCtr < counter || haloToXenaCtr < counter
                              || haloToTroyCtr < counter;
                waitTime = maxWaitTime - (System.currentTimeMillis() - start);
            }
        }
    }

};