//,temp,FollowerResyncConcurrencyTest.java,388,538,temp,FollowerResyncConcurrencyTest.java,194,362
//,3
public class xxx {
    public void followerResyncCrashTest(boolean useTxnLogResync)
            throws IOException, InterruptedException, KeeperException,  Throwable
    {
        final Semaphore sem = new Semaphore(0);

        QuorumUtil qu = new QuorumUtil(1);
        qu.startAll();
        CountdownWatcher watcher1 = new CountdownWatcher();
        CountdownWatcher watcher2 = new CountdownWatcher();
        CountdownWatcher watcher3 = new CountdownWatcher();

        int index = 1;
        while(qu.getPeer(index).peer.leader == null) {
            index++;
        }

        Leader leader = qu.getPeer(index).peer.leader;
        assertNotNull(leader);
        
        if (useTxnLogResync) {
            // Set the factor to high value so that this test case always
            // resync using txnlog
            qu.getPeer(index).peer.getActiveServer().getZKDatabase()
                    .setSnapshotSizeFactor(1000);
        } else {
            // Disable sending DIFF using txnlog, so that this test still
            // testing the ZOOKEEPER-962 bug
            qu.getPeer(index).peer.getActiveServer().getZKDatabase()
            .setSnapshotSizeFactor(-1);
        }

        /* Reusing the index variable to select a follower to connect to */
        index = (index == 1) ? 2 : 1;
        LOG.info("Connecting to follower: {}", index);

        qu.shutdown(index);

        final ZooKeeper zk3 =
            createClient(qu.getPeer(3).peer.getClientPort(), watcher3);
        LOG.info("zk3 has session id 0x{}", Long.toHexString(zk3.getSessionId()));

        zk3.create("/mybar", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        qu.restart(index);

        final ZooKeeper zk1 =
            createClient(qu.getPeer(index).peer.getClientPort(), watcher1);
        LOG.info("zk1 has session id 0x{}", Long.toHexString(zk1.getSessionId()));

        final ZooKeeper zk2 =
            createClient(qu.getPeer(index).peer.getClientPort(), watcher2);
        LOG.info("zk2 has session id 0x{}", Long.toHexString(zk2.getSessionId()));

        zk1.create("/first", new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        
        // Prepare a thread that will create znodes.
        Thread mytestfooThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 3000; i++) {
                    // Here we create 3000 znodes
                    zk3.create("/mytestfoo", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, new AsyncCallback.StringCallback() {

                        @Override
                        public void processResult(int rc, String path, Object ctx, String name) {
                            pending.decrementAndGet();
                            counter.incrementAndGet();
                            if (rc != 0) {
                                errors.incrementAndGet();
                            }
                            if(counter.get() == 16200){
                                sem.release();
                            }
                        }
                    }, null);
                    pending.incrementAndGet();
                    if(i%10==0){
                        try {
                            Thread.sleep(100);
                        } catch (Exception e) {

                        }
                    }
                }

            }
        });

        // Here we start populating the server and shutdown the follower after
        // initial data is written.
        for(int i = 0; i < 13000; i++) {
            // Here we create 13000 znodes
            zk3.create("/mybar", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, new AsyncCallback.StringCallback() {

                @Override
                public void processResult(int rc, String path, Object ctx, String name) {
                    pending.decrementAndGet();
                    counter.incrementAndGet();
                    if (rc != 0) {
                        errors.incrementAndGet();
                    }
                    if(counter.get() == 16200){
                        sem.release();
                    }
                }
            }, null);
            pending.incrementAndGet();

            if(i == 5000){
                qu.shutdown(index);
                LOG.info("Shutting down s1");
            }
            if(i == 12000){
                // Start the prepared thread so that it is writing znodes while
                // the follower is restarting. On the first restart, the follow
                // should use txnlog to catchup. For subsequent restart, the
                // follower should use a diff to catchup.
                mytestfooThread.start();
                LOG.info("Restarting follower: {}", index);
                qu.restart(index);
                Thread.sleep(300);
                LOG.info("Shutdown follower: {}", index);
                qu.shutdown(index);
                Thread.sleep(300);
                LOG.info("Restarting follower: {}", index);
                qu.restart(index);
                LOG.info("Setting up server: {}", index);
            }
            if((i % 1000) == 0){
                Thread.sleep(1000);
            }

            if(i%50 == 0) {
                zk2.create("/newbaz", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, new AsyncCallback.StringCallback() {
                    @Override
                    public void processResult(int rc, String path, Object ctx, String name) {
                        pending.decrementAndGet();
                        counter.incrementAndGet();
                        if (rc != 0) {
                            errors.incrementAndGet();
                        }
                        if(counter.get() == 16200){
                            sem.release();
                        }
                    }
                }, null);
                pending.incrementAndGet();
            }
        }

        // Wait until all updates return
        if(!sem.tryAcquire(ClientBase.CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)) {
            LOG.warn("Did not aquire semaphore fast enough");
        }
        mytestfooThread.join(ClientBase.CONNECTION_TIMEOUT);
        if (mytestfooThread.isAlive()) {
            LOG.error("mytestfooThread is still alive");
        }
        assertTrue(waitForPendingRequests(60));
        assertTrue(waitForSync(qu, index, 10));

        verifyState(qu, index, leader);
        
        zk1.close();
        zk2.close();
        zk3.close();
        
        qu.shutdownAll();
    }

};