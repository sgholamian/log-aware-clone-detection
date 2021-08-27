//,temp,PListTestSupport.java,227,247,temp,PListTest.java,217,237
//,2
public class xxx {
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