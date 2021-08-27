//,temp,VMTransportThreadSafeTest.java,852,932,temp,VMTransportThreadSafeTest.java,649,731
//,3
public class xxx {
    private long doTestTwoWayMessageThroughPut(boolean async) throws Exception {

        final VMTransport local = new VMTransport(new URI(location1));
        final VMTransport remote = new VMTransport(new URI(location2));

        final AtomicInteger sequenceId = new AtomicInteger();

        remote.setAsync(async);

        local.setPeer(remote);
        remote.setPeer(local);

        local.setTransportListener(new VMTestTransportListener(localReceived));
        remote.setTransportListener(new VMTestTransportListener(remoteReceived));

        final int messageCount = 200000;

        local.start();
        remote.start();

        long startTime = System.currentTimeMillis();

        Thread localSend = new Thread(new Runnable() {

            @Override
            public void run() {
                for(int i = 0; i < messageCount; ++i) {
                    try {
                        local.oneway(new DummyCommand(sequenceId.incrementAndGet()));
                    } catch (Exception e) {
                    }
                }

            }
        });

        Thread remoteSend = new Thread(new Runnable() {

            @Override
            public void run() {
                for(int i = 0; i < messageCount; ++i) {
                    try {
                        remote.oneway(new DummyCommand(sequenceId.incrementAndGet()));
                    } catch (Exception e) {
                    }
                }

            }
        });

        localSend.start();
        remoteSend.start();

        // Wait for both to finish and then check that each side go the correct amount
        localSend.join();
        remoteSend.join();

        long endTime = System.currentTimeMillis();

        assertTrue(Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return remoteReceived.size() == messageCount;
            }
        }));

        assertTrue(Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return localReceived.size() == messageCount;
            }
        }));

        LOG.debug("All messages sent,stop all");

        local.stop();
        remote.stop();

        localReceived.clear();
        remoteReceived.clear();

        return endTime - startTime;
    }

};