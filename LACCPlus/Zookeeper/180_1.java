//,temp,FollowerResyncConcurrencyTest.java,388,538,temp,FollowerResyncConcurrencyTest.java,194,362
//,3
public class xxx {
    @Test
    public void testResyncByDiffAfterFollowerCrashes()
        throws IOException, InterruptedException, KeeperException, Throwable
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

        /* Reusing the index variable to select a follower to connect to */
        index = (index == 1) ? 2 : 1;
        LOG.info("Connecting to follower: {}", index);

        final ZooKeeper zk1 =
            createClient(qu.getPeer(index).peer.getClientPort(), watcher1);
        LOG.info("zk1 has session id 0x{}", Long.toHexString(zk1.getSessionId()));

        final ZooKeeper zk2 =
            createClient(qu.getPeer(index).peer.getClientPort(), watcher2);
        LOG.info("zk2 has session id 0x{}", Long.toHexString(zk2.getSessionId()));

        final ZooKeeper zk3 =
            createClient(qu.getPeer(3).peer.getClientPort(), watcher3);
        LOG.info("zk3 has session id 0x{}", Long.toHexString(zk3.getSessionId()));

        zk1.create("/first", new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk2.create("/mybar", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        final AtomicBoolean runNow = new AtomicBoolean(false);
        Thread mytestfooThread = new Thread(new Runnable() {

            @Override
            public void run() {
                int inSyncCounter = 0;
                while(inSyncCounter < 400) {
                    if(runNow.get()) {
                        zk3.create("/mytestfoo", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, new AsyncCallback.StringCallback() {

                            @Override
                            public void processResult(int rc, String path, Object ctx, String name) {
                                pending.decrementAndGet();
                                counter.incrementAndGet();
                                if (rc != 0) {
                                    errors.incrementAndGet();;
                                }
                                if(counter.get() > 7300){
                                    sem.release();
                                }
                            }
                        }, null);
                        pending.incrementAndGet();
                        try {
                            Thread.sleep(10);
                        } catch (Exception e) {
                        }
                        inSyncCounter++;
                    } else {
                        Thread.yield();
                    }
                }

            }
        });

        mytestfooThread.start();
        for(int i = 0; i < 5000; i++) {
            zk2.create("/mybar", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, new AsyncCallback.StringCallback() {

                @Override
                public void processResult(int rc, String path, Object ctx, String name) {
                    pending.decrementAndGet();
                    counter.incrementAndGet();
                    if (rc != 0) {
                        errors.incrementAndGet();;
                    }
                    if(counter.get() > 7300){
                        sem.release();
                    }
                }
            }, null);
            pending.incrementAndGet();
            if(i == 1000){
                qu.shutdown(index);
                Thread.sleep(1100);
                LOG.info("Shutting down s1");
            }
            if(i == 1100 || i == 1150 || i == 1200) {
                Thread.sleep(1000);
            }

            if(i == 1200){
                qu.startThenShutdown(index);
                runNow.set(true);
                qu.restart(index);
                LOG.info("Setting up server: {}", index);
            }

            if(i>=1000 &&  i%2== 0) {
                zk3.create("/newbaz", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, new AsyncCallback.StringCallback() {

                    @Override
                    public void processResult(int rc, String path, Object ctx, String name) {
                        pending.decrementAndGet();
                        counter.incrementAndGet();
                        if (rc != 0) {
                            errors.incrementAndGet();
                        }
                        if(counter.get() > 7300){
                            sem.release();
                        }
                    }
                }, null);
                pending.incrementAndGet();
            }
            if(i == 1050 || i == 1100 || i == 1150) {
                Thread.sleep(1000);
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
        // Verify that server is following and has the same epoch as the leader

        verifyState(qu, index, leader);

        zk1.close();
        zk2.close();
        zk3.close();
        
        qu.shutdownAll();
    }

};