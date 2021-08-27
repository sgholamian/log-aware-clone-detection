//,temp,ThriftConsumerConcurrentTest.java,104,127,temp,ThriftConsumerConcurrentTest.java,73,93
//,3
public class xxx {
            @Override
            public void run() throws TTransportException {
                TTransport transport = new TSocket("localhost", THRIFT_SYNC_REQUEST_TEST_PORT);
                transport.open();
                TProtocol protocol = new TBinaryProtocol(new TFramedTransport(transport));
                Calculator.Client client = (new Calculator.Client.Factory()).getClient(protocol);

                int instanceId = createId();

                int calculateResponse = 0;
                try {
                    calculateResponse = client.calculate(1, new Work(instanceId, THRIFT_TEST_NUM1, Operation.MULTIPLY));
                } catch (TException e) {
                    LOG.info("Exception", e);
                }

                assertNotEquals(0, calculateResponse, "instanceId = " + instanceId);
                assertEquals(instanceId * THRIFT_TEST_NUM1, calculateResponse);

                transport.close();
            }

};