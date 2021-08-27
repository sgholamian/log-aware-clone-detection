//,temp,NettyConcurrentTest.java,82,88,temp,NettyAsyncRequestReplyTest.java,76,82
//,3
public class xxx {
                public String call() throws Exception {
                    String reply = template.requestBody("netty:tcp://localhost:" + port + "?textline=true&sync=true", index,
                            String.class);
                    LOG.info("Sent {} received {}", index, reply);
                    assertEquals("Bye " + index, reply);
                    return reply;
                }

};