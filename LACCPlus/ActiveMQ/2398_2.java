//,temp,VMTransportThreadSafeTest.java,479,553,temp,VMTransportThreadSafeTest.java,430,477
//,3
public class xxx {
    private void doTestBlockedOnewayEnqeueAandStopTransport(boolean async) throws Exception {

        final VMTransport local = new VMTransport(new URI(location1));
        final VMTransport remote = new VMTransport(new URI(location2));

        final AtomicInteger sequenceId = new AtomicInteger();

        remote.setAsync(async);
        remote.setAsyncQueueDepth(99);

        local.setPeer(remote);
        remote.setPeer(local);

        local.setTransportListener(new VMTestTransportListener(localReceived));
        remote.setTransportListener(new VMTestTransportListener(remoteReceived));

        local.start();

        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                for(int i = 0; i < 100; ++i) {
                    try {
                        local.oneway(new DummyCommand(sequenceId.incrementAndGet()));
                    } catch (Exception e) {
                    }
                }

            }
        });
        t.start();

        LOG.debug("Started async delivery, wait for remote's queue to fill up");

        assertTrue(Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return remote.getMessageQueue().remainingCapacity() == 0;
            }
        }));

        LOG.debug("Remote messageQ is full, start it and stop all");

        remote.start();
        local.stop();
        remote.stop();
    }

};