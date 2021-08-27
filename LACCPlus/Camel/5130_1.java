//,temp,NettyConcurrentTest.java,71,106,temp,NettyAsyncRequestReplyTest.java,64,98
//,3
public class xxx {
    private void doSendMessages(int files, int poolSize) throws Exception {
        StopWatch watch = new StopWatch();
        NotifyBuilder notify = new NotifyBuilder(context).whenDone(files).create();

        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        // we access the responses Map below only inside the main thread,
        // so no need for a thread-safe Map implementation
        Map<Integer, Future<String>> responses = new HashMap<>();
        for (int i = 0; i < files; i++) {
            final int index = i;
            Future<String> out = executor.submit(new Callable<String>() {
                public String call() throws Exception {
                    String reply = template.requestBody("netty:tcp://localhost:{{port}}?encoders=#encoder&decoders=#decoder",
                            index, String.class);
                    LOG.debug("Sent {} received {}", index, reply);
                    assertEquals("Bye " + index, reply);
                    return reply;
                }
            });
            responses.put(index, out);
        }

        notify.matches(60, TimeUnit.SECONDS);
        LOG.info("Took " + watch.taken() + " millis to process " + files + " messages using " + poolSize + " client threads.");
        assertEquals(files, responses.size());

        // get all responses
        Set<String> unique = new HashSet<>();
        for (Future<String> future : responses.values()) {
            unique.add(future.get());
        }

        // should be 'files' unique responses
        assertEquals(files, unique.size(), "Should be " + files + " unique responses");
        executor.shutdownNow();
    }

};