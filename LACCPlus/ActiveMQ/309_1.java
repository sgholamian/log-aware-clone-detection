//,temp,VMTransportThreadSafeTest.java,759,818,temp,VMTransportThreadSafeTest.java,569,621
//,3
public class xxx {
    private long doTestOneWayMessageThroughPut(boolean async) throws Exception {

        final VMTransport local = new VMTransport(new URI(location1));
        final VMTransport remote = new VMTransport(new URI(location2));

        final AtomicInteger sequenceId = new AtomicInteger();

        remote.setAsync(async);

        local.setPeer(remote);
        remote.setPeer(local);

        local.setTransportListener(new VMTestTransportListener(localReceived));
        remote.setTransportListener(new VMTestTransportListener(remoteReceived));

        final int messageCount = 100000;

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

        localSend.start();

        // Wait for both to finish and then check that each side go the correct amount
        localSend.join();

        long endTime = System.currentTimeMillis();

        assertTrue(Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return remoteReceived.size() == messageCount;
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