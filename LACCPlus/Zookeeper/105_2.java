//,temp,ClientHammerTest.java,154,186,temp,ClientHammerTest.java,123,147
//,3
public class xxx {
    public void runHammer(final int threadCount, final int childCount)
        throws Throwable
    {
        try {
            HammerThread[] threads = new HammerThread[threadCount];
            long start = Time.currentElapsedTime();
            for (int i = 0; i < threads.length; i++) {
                ZooKeeper zk = createClient();
                String prefix = "/test-" + i;
                zk.create(prefix, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                prefix += "/";
                HammerThread thread =
                    new BasicHammerThread("BasicHammerThread-" + i, zk, prefix,
                            childCount);
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