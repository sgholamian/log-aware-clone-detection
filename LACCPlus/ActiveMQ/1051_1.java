//,temp,VMTransportThreadSafeTest.java,852,932,temp,VMTransportThreadSafeTest.java,649,731
//,3
public class xxx {
    public void doTestTwoWayTrafficWithMutexTransport(boolean localAsync, boolean remoteAsync) throws Exception {

        final VMTransport vmlocal = new VMTransport(new URI(location1));
        final VMTransport vmremote = new VMTransport(new URI(location2));

        final MutexTransport local = new MutexTransport(vmlocal);
        final MutexTransport remote = new MutexTransport(vmremote);

        final AtomicInteger sequenceId = new AtomicInteger();

        vmlocal.setAsync(localAsync);
        vmremote.setAsync(remoteAsync);

        vmlocal.setPeer(vmremote);
        vmremote.setPeer(vmlocal);

        local.setTransportListener(new VMTestTransportListener(localReceived));
        remote.setTransportListener(new VMResponderTransportListener(remoteReceived, remote));

        final int messageCount = 200000;

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

        Thread.sleep(10);

        local.start();
        remote.start();

        // Wait for both to finish and then check that each side go the correct amount
        localSend.join();
        remoteSend.join();

        assertTrue("Remote should have received ("+messageCount+") but got ()" + remoteReceived.size(), Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return remoteReceived.size() == messageCount;
            }
        }));

        assertTrue("Local should have received ("+messageCount*2+") but got ()" + localReceived.size(), Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return localReceived.size() == messageCount*2;
            }
        }));

        LOG.debug("All messages sent,stop all");

        local.stop();
        remote.stop();

        localReceived.clear();
        remoteReceived.clear();
    }

};