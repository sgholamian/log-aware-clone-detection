//,temp,ThriftConsumerConcurrentTest.java,104,127,temp,ThriftConsumerConcurrentTest.java,73,93
//,3
public class xxx {
            @Override
            public void run() throws TTransportException, IOException, InterruptedException {
                final CountDownLatch latch = new CountDownLatch(1);

                TNonblockingTransport transport = new TNonblockingSocket("localhost", THRIFT_ASYNC_REQUEST_TEST_PORT);
                Calculator.AsyncClient client
                        = (new Calculator.AsyncClient.Factory(new TAsyncClientManager(), new TBinaryProtocol.Factory()))
                                .getAsyncClient(transport);

                int instanceId = createId();
                CalculateAsyncMethodCallback calculateCallback = new CalculateAsyncMethodCallback(latch);
                try {
                    client.calculate(1, new Work(instanceId, THRIFT_TEST_NUM1, Operation.MULTIPLY), calculateCallback);
                } catch (TException e) {
                    LOG.info("Exception", e);
                }
                latch.await(5, TimeUnit.SECONDS);

                int calculateResponse = calculateCallback.getCalculateResponse();
                LOG.debug("instanceId = {}", instanceId);
                assertEquals(instanceId * THRIFT_TEST_NUM1, calculateResponse);

                transport.close();
            }

};