//,temp,PListTestSupport.java,200,286,temp,PListTest.java,188,275
//,3
public class xxx {
    @Test
    public void testConcurrentAddRemove() throws Exception {
        File directory = store.getDirectory();
        store.stop();
        IOHelper.mkdirs(directory);
        IOHelper.deleteChildren(directory);
        store = createConcurrentAddRemovePListStore();
        store.setDirectory(directory);
        store.start();

        final ByteSequence payload = new ByteSequence(new byte[1024*2]);


        final Vector<Throwable> exceptions = new Vector<Throwable>();
        final int iterations = 1000;
        final int numLists = 10;

        final PList[] lists = new PList[numLists];
        String threadName = Thread.currentThread().getName();
        for (int i=0; i<numLists; i++) {
            Thread.currentThread().setName("C:"+String.valueOf(i));
            lists[i] = store.getPList(String.valueOf(i));
        }
        Thread.currentThread().setName(threadName);

        executor = Executors.newFixedThreadPool(100);
        class A implements Runnable {
            @Override
            public void run() {
                final String threadName = Thread.currentThread().getName();
                try {
                    for (int i=0; i<iterations; i++) {
                        PList candidate = lists[i%numLists];
                        Thread.currentThread().setName("ALRF:"+candidate.getName());
                        synchronized (plistLocks(candidate)) {
                            Object last = candidate.addLast(String.valueOf(i), payload);
                            getFirst(candidate);
                            assertTrue(candidate.remove(last));
                        }
                    }
                } catch (Exception error) {
                    LOG.error("Unexpcted ex", error);
                    error.printStackTrace();
                    exceptions.add(error);
                }  finally {
                    Thread.currentThread().setName(threadName);
                }
            }
        };

        class B implements  Runnable {
            @Override
            public void run() {
                final String threadName = Thread.currentThread().getName();
                try {
                    for (int i=0; i<iterations; i++) {
                        PList candidate = lists[i%numLists];
                        Thread.currentThread().setName("ALRF:"+candidate.getName());
                         synchronized (plistLocks(candidate)) {
                             Object last = candidate.addLast(String.valueOf(i), payload);
                             getFirst(candidate);
                             assertTrue(candidate.remove(last));
                         }
                    }
                } catch (Exception error) {
                    error.printStackTrace();
                    exceptions.add(error);
                }  finally {
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
        boolean finishedInTime = executor.awaitTermination(10, TimeUnit.MINUTES);
        LOG.info("Tested completion finished in time? -> {}", finishedInTime ? "YES" : "NO");

        assertTrue("no exceptions", exceptions.isEmpty());
        assertTrue("finished ok", finishedInTime);
    }

};