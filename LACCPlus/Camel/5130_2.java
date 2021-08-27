//,temp,NettyConcurrentTest.java,71,106,temp,NettyAsyncRequestReplyTest.java,64,98
//,3
public class xxx {
    @Disabled("TODO: investigate for Camel 3.0")
    @Test
    void testConcurrent() throws Exception {
        int size = 1000;

        ExecutorService executor = Executors.newFixedThreadPool(20);
        // we access the responses Map below only inside the main thread,
        // so no need for a thread-safe Map implementation
        Map<Integer, Future<String>> responses = new HashMap<>();
        for (int i = 0; i < size; i++) {
            final int index = i;
            Future<String> out = executor.submit(new Callable<String>() {
                public String call() throws Exception {
                    String reply = template.requestBody("netty:tcp://localhost:" + port + "?textline=true&sync=true", index,
                            String.class);
                    LOG.info("Sent {} received {}", index, reply);
                    assertEquals("Bye " + index, reply);
                    return reply;
                }
            });
            responses.put(index, out);
        }

        // get all responses
        Set<String> unique = new HashSet<>();
        for (Future<String> future : responses.values()) {
            String reply = future.get(120, TimeUnit.SECONDS);
            assertNotNull("Should get a reply", reply);
            unique.add(reply);
        }

        // should be 1000 unique responses
        assertEquals(size, unique.size(), "Should be " + size + " unique responses");
        executor.shutdownNow();
    }

};