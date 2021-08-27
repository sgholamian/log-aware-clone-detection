//,temp,ServerSessionImplTest.java,242,435,temp,ServerSessionImplTest.java,117,240
//,3
public class xxx {
    @Test
    public void testCloseCanStopActiveSession() throws Exception {

        final int maxMessages = 4000;
        final CountDownLatch messageCount = new CountDownLatch(maxMessages);

        final MessageEndpointFactory messageEndpointFactory = context.mock(MessageEndpointFactory.class);
        final MessageResourceAdapter resourceAdapter = context.mock(MessageResourceAdapter.class);
        final ActiveMQEndpointActivationKey key = context.mock(ActiveMQEndpointActivationKey.class);
        messageEndpoint = context.mock(MessageEndpointProxy.class);
        workManager = context.mock(WorkManager.class);
        final MessageActivationSpec messageActivationSpec = context.mock(MessageActivationSpec.class);
        final BootstrapContext boostrapContext = context.mock(BootstrapContext.class);
        context.checking(new Expectations() {
            {
                allowing(boostrapContext).getWorkManager();
                will(returnValue(workManager));
                allowing(resourceAdapter).getBootstrapContext();
                will(returnValue(boostrapContext));
                allowing(messageEndpointFactory).isDeliveryTransacted(with(any(Method.class)));
                will(returnValue(Boolean.FALSE));
                allowing(key).getMessageEndpointFactory();
                will(returnValue(messageEndpointFactory));
                allowing(key).getActivationSpec();
                will(returnValue(messageActivationSpec));
                allowing(messageActivationSpec).isUseJndi();
                will(returnValue(Boolean.FALSE));
                allowing(messageActivationSpec).getDestinationType();
                will(returnValue("javax.jms.Queue"));
                allowing(messageActivationSpec).getDestination();
                will(returnValue("Queue"));
                allowing(messageActivationSpec).getAcknowledgeModeForSession();
                will(returnValue(1));
                allowing(messageActivationSpec).getMaxSessionsIntValue();
                will(returnValue(1));
                allowing(messageActivationSpec).getEnableBatchBooleanValue();
                will(returnValue(Boolean.FALSE));
                allowing(messageActivationSpec).isUseRAManagedTransactionEnabled();
                will(returnValue(Boolean.TRUE));
                allowing(messageEndpointFactory).createEndpoint(with(any(XAResource.class)));
                will(returnValue(messageEndpoint));

                allowing(workManager).scheduleWork((Work) with(Matchers.instanceOf(Work.class)), with(any(long.class)), with(any(ExecutionContext.class)),
                    with(any(WorkListener.class)));
                will(new Action() {
                    @Override
                    public Object invoke(Invocation invocation) throws Throwable {
                        return null;
                    }

                    @Override
                    public void describeTo(Description description) {
                    }
                });

                allowing(messageEndpoint).beforeDelivery((Method) with(Matchers.instanceOf(Method.class)));
                allowing(messageEndpoint).onMessage(with(any(javax.jms.Message.class)));
                will(new Action() {
                    @Override
                    public Object invoke(Invocation invocation) throws Throwable {
                        messageCount.countDown();
                        if (messageCount.getCount() < maxMessages - 11) {
                            TimeUnit.MILLISECONDS.sleep(200);
                        }
                        return null;
                    }

                    @Override
                    public void describeTo(Description description) {
                        description.appendText("Keep message count");
                    }
                });
                allowing(messageEndpoint).afterDelivery();
                allowing(messageEndpoint).release();

            }
        });

        endpointWorker = new ActiveMQEndpointWorker(resourceAdapter, key);
        endpointWorker.setConnection(con);
        pool = new ServerSessionPoolImpl(endpointWorker, 2);

        endpointWorker.start();
        final ServerSessionImpl serverSession1 = (ServerSessionImpl) pool.getServerSession();

        // preload the session dispatch queue to keep the session active
        ActiveMQSession session1 = (ActiveMQSession) serverSession1.getSession();
        for (int i = 0; i < maxMessages; i++) {
            MessageDispatch messageDispatch = new MessageDispatch();
            ActiveMQMessage message = new ActiveMQTextMessage();
            message.setMessageId(new MessageId("0:0:0:" + i));
            message.getMessageId().setBrokerSequenceId(i);
            messageDispatch.setMessage(message);
            messageDispatch.setConsumerId(new ConsumerId("0:0:0"));
            session1.dispatch(messageDispatch);
        }

        ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch runState = new CountDownLatch(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSession1.run();
                    runState.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return messageCount.getCount() < maxMessages - 10;
            }
        });
        assertTrue("some messages consumed", messageCount.getCount() < maxMessages);
        LOG.info("Closing pool on {}", messageCount.getCount());
        pool.close();

        assertTrue("run has completed", runState.await(20, TimeUnit.SECONDS));
        assertTrue("not all messages consumed", messageCount.getCount() > 0);
    }

};