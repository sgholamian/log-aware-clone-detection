//,temp,VMTransportThreadSafeTest.java,479,553,temp,VMTransportThreadSafeTest.java,430,477
//,3
public class xxx {
    @Test(timeout=60000)
    public void testBlockedOnewayEnqeueWhileStartedDetectsStop() throws Exception {
        final VMTransport local = new VMTransport(new URI(location1));
        final VMTransport remote = new VMTransport(new URI(location2));

        final AtomicInteger sequenceId = new AtomicInteger();

        remote.setAsync(true);
        remote.setAsyncQueueDepth(2);

        local.setPeer(remote);
        remote.setPeer(local);

        local.setTransportListener(new VMTestTransportListener(localReceived));
        remote.setTransportListener(new GatedVMTestTransportListener(remoteReceived));

        local.start();
        remote.start();

        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                for(int i = 0; i < 3; ++i) {
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

        LOG.debug("Starting async gate open.");
        Thread gateman = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                }
                ((GatedVMTestTransportListener) remote.getTransportListener()).gate.countDown();
            }
        });

        assertTrue(Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return remoteReceived.size() == 1;
            }
        }));

        gateman.start();

        remote.stop();
        local.stop();

        assertTrue(Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return remoteReceived.size() == 1;
            }
        }));

        assertMessageAreOrdered(remoteReceived);
    }

};