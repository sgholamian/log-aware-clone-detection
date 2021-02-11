//,temp,ClientHammerTest.java,154,186,temp,ClientHammerTest.java,123,147
//,3
public class xxx {
    @Test
    public void testHammerSuper() throws Throwable {
        try {
            final int threadCount = 5;
            final int childCount = 10;

            HammerThread[] threads = new HammerThread[threadCount];
            long start = Time.currentElapsedTime();
            for (int i = 0; i < threads.length; i++) {
                String prefix = "/test-" + i;
                {
                    ZooKeeper zk = createClient();
                    try {
                        zk.create(prefix, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                    } finally {
                        zk.close();
                    }
                }
                prefix += "/";
                HammerThread thread =
                    new SuperHammerThread("SuperHammerThread-" + i, this,
                            prefix, childCount);
                thread.start();

                threads[i] = thread;
            }

            verifyHammer(start, threads, childCount);
        } catch (Throwable t) {
            LOG.error("test Assert.failed", t);
            throw t;
        }
    }

};