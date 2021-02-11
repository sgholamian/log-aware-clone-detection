//,temp,FileChangeWatcherTest.java,232,276,temp,FileChangeWatcherTest.java,65,111
//,3
public class xxx {
    @Test
    public void testCallbackErrorDoesNotCrashWatcherThread() throws IOException, InterruptedException {
        FileChangeWatcher watcher = null;
        try {
            final AtomicInteger callCount = new AtomicInteger(0);
            watcher = new FileChangeWatcher(
                    tempDir.toPath(),
                    event -> {
                        LOG.info("Got an update: " + event.kind() + " " + event.context());
                        int oldValue;
                        synchronized (callCount) {
                            oldValue = callCount.getAndIncrement();
                            callCount.notifyAll();
                        }
                        if (oldValue == 0) {
                            throw new RuntimeException("This error should not crash the watcher thread");
                        }
                    });
            watcher.start();
            watcher.waitForState(FileChangeWatcher.State.RUNNING);
            Thread.sleep(1000L); // XXX hack
            LOG.info("Modifying file");
            FileUtils.writeStringToFile(tempFile, "Hello world\n", StandardCharsets.UTF_8, true);
            synchronized (callCount) {
                while (callCount.get() == 0) {
                    callCount.wait(3000L);
                }
            }
            LOG.info("Modifying file again");
            FileUtils.writeStringToFile(tempFile, "Hello world again\n", StandardCharsets.UTF_8, true);
            synchronized (callCount) {
                if (callCount.get() == 1) {
                    callCount.wait(3000L);
                }
            }
            // The value of callCount can exceed 1 only if the callback thread
            // survives the exception thrown by the first callback.
            assertTrue(callCount.get() > 1);
        } finally {
            if (watcher != null) {
                watcher.stop();
                watcher.waitForState(FileChangeWatcher.State.STOPPED);
            }
        }
    }

};