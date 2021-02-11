//,temp,FileChangeWatcherTest.java,155,189,temp,FileChangeWatcherTest.java,65,111
//,3
public class xxx {
    @Test
    public void testCallbackWorksOnFileAdded() throws IOException, InterruptedException {
        FileChangeWatcher watcher = null;
        try {
            final List<WatchEvent<?>> events = new ArrayList<>();
            watcher = new FileChangeWatcher(
                    tempDir.toPath(),
                    event -> {
                        LOG.info("Got an update: " + event.kind() + " " + event.context());
                        synchronized (events) {
                            events.add(event);
                            events.notifyAll();
                        }
                    });
            watcher.start();
            watcher.waitForState(FileChangeWatcher.State.RUNNING);
            Thread.sleep(1000L); // XXX hack
            File tempFile2 = File.createTempFile("zk_test_", "", tempDir);
            tempFile2.deleteOnExit();
            synchronized (events) {
                if (events.isEmpty()) {
                    events.wait(3000L);
                }
                assertFalse(events.isEmpty());
                WatchEvent<?> event = events.get(0);
                assertEquals(StandardWatchEventKinds.ENTRY_CREATE, event.kind());
                assertEquals(tempFile2.getName(), event.context().toString());
            }
        } finally {
            if (watcher != null) {
                watcher.stop();
                watcher.waitForState(FileChangeWatcher.State.STOPPED);
            }
        }
    }

};