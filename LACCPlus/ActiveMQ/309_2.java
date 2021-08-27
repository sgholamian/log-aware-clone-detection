//,temp,VMTransportThreadSafeTest.java,759,818,temp,VMTransportThreadSafeTest.java,569,621
//,3
public class xxx {
    private void doTestStopWhileStartingWithNoAsyncLimit(boolean async, final int expect) throws Exception {

        final VMTransport local = new VMTransport(new URI(location1));
        final VMTransport remote = new VMTransport(new URI(location2));

        remote.setAsync(async);

        local.setPeer(remote);
        remote.setPeer(local);

        local.setTransportListener(new VMTestTransportListener(localReceived));
        remote.setTransportListener(new SlowVMTestTransportListener(remoteReceived));

        local.start();

        for(int i = 0; i < 100; ++i) {
            local.oneway(new DummyCommand(i));
        }

        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    remote.stop();
                } catch (Exception e) {
                }
            }
        });

        remote.start();

        t.start();

        assertTrue("Remote should receive: " + expect + ", commands but got: " + remoteReceived.size(), Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return remoteReceived.size() >= expect;
            }
        }));

        LOG.debug("Remote listener received " + remoteReceived.size() + " messages");

        local.stop();

        assertTrue("Remote transport never was disposed.", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return remote.isDisposed();
            }
        }));
    }

};