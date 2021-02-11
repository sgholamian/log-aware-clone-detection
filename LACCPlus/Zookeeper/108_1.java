//,temp,FileChangeWatcherTest.java,113,153,temp,FileChangeWatcherTest.java,65,111
//,3
public class xxx {
    @Test
    public void testCallbackWorksOnFileTouched() throws IOException, InterruptedException {
        FileChangeWatcher watcher = null;
        try {
            final List<WatchEvent<?>> events = new ArrayList<>();
            watcher = new FileChangeWatcher(
                    tempDir.toPath(),
                    event -> {
                        LOG.info("Got an update: " + event.kind() + " " + event.context());
                        // Filter out the extra ENTRY_CREATE events that are
                        // sometimes seen at the start. Even though we create the watcher
                        // after the file exists, sometimes we still get a create event.
                        if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
                            return;
                        }
                        synchronized (events) {
                            events.add(event);
                            events.notifyAll();
                        }
                    });
            watcher.start();
            watcher.waitForState(FileChangeWatcher.State.RUNNING);
            Thread.sleep(1000L); // XXX hack
            LOG.info("Touching file");
            FileUtils.touch(tempFile);
            synchronized (events) {
                if (events.isEmpty()) {
                    events.wait(3000L);
                }
                assertFalse(events.isEmpty());
                WatchEvent<?> event = events.get(0);
                assertEquals(StandardWatchEventKinds.ENTRY_MODIFY, event.kind());
                assertEquals(tempFile.getName(), event.context().toString());
            }
        } finally {
            if (watcher != null) {
                watcher.stop();
                watcher.waitForState(FileChangeWatcher.State.STOPPED);
            }
        }
    }

};