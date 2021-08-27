//,temp,PListTestSupport.java,200,286,temp,PListTest.java,188,275
//,3
public class xxx {
    @Test
    public void testConcurrentAddRemove() throws Exception {
        File directory = store.getDirectory();
        store.stop();
        IOHelper.mkdirs(directory);
        IOHelper.deleteChildren(directory);
        store = new PListStoreImpl();
        store.setCleanupInterval(400);
        store.setDirectory(directory);
        store.setJournalMaxFileLength(1024 * 5);
        store.setLazyInit(false);
        store.start();

        final ByteSequence payload = new ByteSequence(new byte[1024 * 2]);

        final Vector<Throwable> exceptions = new Vector<Throwable>();
        final int iterations = 1000;
        final int numLists = 10;

        final PList[] lists = new PList[numLists];
        String threadName = Thread.currentThread().getName();
        for (int i = 0; i < numLists; i++) {
            Thread.currentThread().setName("C:" + String.valueOf(i));
            lists[i] = store.getPList(String.valueOf(i));
        }
        Thread.currentThread().setName(threadName);

        executor = Executors.newFixedThreadPool(100);
        class A implements Runnable {
            @Override
            public void run() {
                final String threadName = Thread.currentThread().getName();
                try {
                    for (int i = 0; i < iterations; i++) {
                        PList candidate = lists[i % numLists];
                        Thread.currentThread().setName("ALRF:" + candidate.getName());
                        synchronized (plistLocks(candidate)) {
                            Object locator = candidate.addLast(String.valueOf(i), payload);
                            getFirst(candidate);
                            assertTrue(candidate.remove(locator));
                        }
                    }
                } catch (Exception error) {
                    LOG.error("Unexpcted ex", error);
                    error.printStackTrace();
                    exceptions.add(error);
                } finally {
                    Thread.currentThread().setName(threadName);
                }
            }
        };

        class B implements Runnable {
            @Override
            public void run() {
                final String threadName = Thread.currentThread().getName();
                try {
                    for (int i = 0; i < iterations; i++) {
                        PList candidate = lists[i % numLists];
                        Thread.currentThread().setName("ALRF:" + candidate.getName());
                        synchronized (plistLocks(candidate)) {
                            Object locator = candidate.addLast(String.valueOf(i), payload);
                            getFirst(candidate);
                            assertTrue(candidate.remove(locator));
                        }
                    }
                } catch (Exception error) {
                    error.printStackTrace();
                    exceptions.add(error);
                } finally {
                    Thread.currentThread().setName(threadName);
                }
            }
        };

        executor.execute(new A());
        executor.execute(new A());
        executor.execute(new A());
        executor.execute(new B());
        executor.execute(new B());
        executor.execute(new B());

        executor.shutdown();
        boolean finishedInTime = executor.awaitTermination(30, TimeUnit.SECONDS);

        assertTrue("no exceptions", exceptions.isEmpty());
        assertTrue("finished ok", finishedInTime);
    }

};