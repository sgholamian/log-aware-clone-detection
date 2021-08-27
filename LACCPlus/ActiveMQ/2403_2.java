//,temp,PListTestSupport.java,529,634,temp,PListTest.java,521,623
//,3
public class xxx {
        @Override
        public void run() {
            final String threadName = Thread.currentThread().getName();
            try {
                PListImpl plist = null;
                switch (task) {
                    case CREATE:
                        Thread.currentThread().setName("C:" + id);
                        plist = store.getPList(String.valueOf(id));
                        LOG.info("Job-" + id + ", CREATE");
                        break;
                    case DELETE:
                        Thread.currentThread().setName("D:" + id);
                        store.removePList(String.valueOf(id));
                        break;
                    case ADD:
                        Thread.currentThread().setName("A:" + id);
                        plist = store.getPList(String.valueOf(id));

                        for (int j = 0; j < iterations; j++) {
                            synchronized (plistLocks(plist)) {
                                if (exceptions.isEmpty()) {
                                    plist.addLast("PL>" + id + idSeed + "-" + j, payload);
                                } else {
                                    break;
                                }
                            }
                        }

                        if (exceptions.isEmpty()) {
                            LOG.info("Job-" + id + ", Add, done: " + iterations);
                        }
                        break;
                    case REMOVE:
                        Thread.currentThread().setName("R:" + id);
                        plist = store.getPList(String.valueOf(id));
                        synchronized (plistLocks(plist)) {

                            for (int j = iterations - 1; j >= 0; j--) {
                                plist.remove("PL>" + id + idSeed + "-" + j);
                                if (j > 0 && j % (iterations / 2) == 0) {
                                    LOG.info("Job-" + id + " Done remove: " + j);
                                }
                            }
                        }
                        break;
                    case ITERATE:
                        Thread.currentThread().setName("I:" + id);
                        plist = store.getPList(String.valueOf(id));
                        int iterateCount = 0;
                        synchronized (plistLocks(plist)) {
                            if (exceptions.isEmpty()) {
                                Iterator<PListEntry> iterator = plist.iterator();
                                while (iterator.hasNext() && exceptions.isEmpty()) {
                                    iterator.next();
                                    iterateCount++;
                                }

                                // LOG.info("Job-" + id + " Done iterate: it=" +
                                // iterator + ", count:" + iterateCount +
                                // ", size:" + plist.size());
                                if (plist.size() != iterateCount) {
                                    System.err.println("Count Wrong: " + iterator);
                                }
                                assertEquals("iterate got all " + id + " iterator:" + iterator, plist.size(), iterateCount);
                            }
                        }
                        break;

                    case ITERATE_REMOVE:
                        Thread.currentThread().setName("IRM:" + id);
                        plist = store.getPList(String.valueOf(id));

                        int removeCount = 0;
                        synchronized (plistLocks(plist)) {

                            Iterator<PListEntry> removeIterator = plist.iterator();

                            while (removeIterator.hasNext()) {
                                removeIterator.next();
                                removeIterator.remove();
                                if (removeCount++ > iterations) {
                                    break;
                                }
                            }
                        }
                        LOG.info("Job-" + id + " Done remove: " + removeCount);
                        break;

                    default:
                }

            } catch (Exception e) {
                LOG.warn("Job[" + id + "] caught exception: " + e.getMessage());
                e.printStackTrace();
                exceptions.add(e);
                if (executor != null) {
                    executor.shutdownNow();
                }
            } finally {
                Thread.currentThread().setName(threadName);
            }
        }

};