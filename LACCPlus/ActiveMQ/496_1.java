//,temp,ServerSessionImplTest.java,242,435,temp,ServerSessionImplTest.java,117,240
//,3
public class xxx {
    @Test
    public void testGetWhenClosed() throws Exception {

        final int maxMessages = 2000;
        final AtomicReference<CountDownLatch> messageCountRef = new AtomicReference<CountDownLatch>();

        ExecutorService executorService = Executors.newCachedThreadPool();


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
                will(returnValue(10));
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
                        LOG.info("Wok manager invocation: " + invocation);

                        if (invocation.getParameter(0) instanceof ServerSessionImpl) {
                            final ServerSessionImpl serverSession1 = (ServerSessionImpl)invocation.getParameter(0);
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        serverSession1.run();
                                    } catch (Exception e) {
                                        LOG.error("Error on Work run: {}", serverSession1, e);
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
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
                        messageCountRef.get().countDown();
                        return null;
                    }

                    @Override
                    public void describeTo(Description description) {
                        description.appendText("Keep message count");
                    }
                });
                allowing(messageEndpoint).afterDelivery();
                will(new Action() {
                    @Override
                    public void describeTo(Description description) {
                        description.appendText("do sync work on broker");
                    }

                    @Override
                    public Object invoke(Invocation invocation) throws Throwable {
                        TransactionInfo transactionInfo = new TransactionInfo();
                        transactionInfo.setType(TransactionInfo.END);
                        LOG.info("AfterDelivery on: " + messageCountRef.get().getCount());
                        return null;
                    }
                });
                allowing(messageEndpoint).release();

            }
        });

        endpointWorker = new ActiveMQEndpointWorker(resourceAdapter, key);
        endpointWorker.setConnection(con);


        for (int i=0; i<40; i++) {
            final int iteration  = i;
            LOG.info("ITERATION: " +  iteration);
            pool = new ServerSessionPoolImpl(endpointWorker, 2);
            endpointWorker.start();

            messageCountRef.set(new CountDownLatch(maxMessages));

            final CountDownLatch senderDone = new CountDownLatch(1);
            final CountDownLatch messageSent = new CountDownLatch(maxMessages);
            final AtomicBoolean foundClosedSession = new AtomicBoolean(false);
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        // preload the session dispatch queue to keep the session active

                        for (int i = 0; i < maxMessages; i++) {
                            MessageDispatch messageDispatch = new MessageDispatch();
                            ActiveMQMessage message = new ActiveMQTextMessage();
                            message.setMessageId(new MessageId("0:0:0:" + i));
                            message.getMessageId().setBrokerSequenceId(i);
                            messageDispatch.setMessage(message);
                            messageDispatch.setConsumerId(new ConsumerId("0:0:0"));
                            ServerSessionImpl serverSession1 = null;
                            try {
                                serverSession1 = (ServerSessionImpl) pool.getServerSession();
                                ActiveMQSession session1 = (ActiveMQSession) serverSession1.getSession();
                                if (session1.isClosed()) {
                                    // closed flag is not volatile - ok to give a whirl with it closed
                                    foundClosedSession.set(true);
                                }
                                session1.dispatch(messageDispatch);
                                messageSent.countDown();
                                serverSession1.start();
                            } catch (JMSException okOnClose) {
                                LOG.info("Exception on dispatch to {}", serverSession1, okOnClose);
                            }
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                    } finally {
                        senderDone.countDown();
                    }
                }
            });

            assertTrue("[" + iteration + "] Some messages dispatched", Wait.waitFor(new Wait.Condition() {
                @Override
                public boolean isSatisified() throws Exception {
                    LOG.info("[" + iteration + "] Wait before close work MessageSent: " + messageSent.getCount() + ", messages got: "+ messageCountRef.get().getCount());
                    return messageSent.getCount() < maxMessages - 20 && messageCountRef.get().getCount() < maxMessages - 5;
                }
            }, 5000, 10));

            assertTrue("some messages consumed", messageCountRef.get().getCount() < maxMessages);

            final CountDownLatch closeDone = new CountDownLatch(1);
            final CountDownLatch closeSuccess = new CountDownLatch(1);

            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    LOG.info("[" + iteration + "] Closing pool on delivered {} and dispatched {}", messageSent.getCount(), messageCountRef.get().getCount());
                    try {
                        pool.close();
                        closeSuccess.countDown();
                    } catch (InvalidMessageEndpointException error) {
                        LOG.error("Ex on pool close", error);
                        //error.printStackTrace();
                    } finally {
                        closeDone.countDown();
                    }
                }
            });

            assertTrue("[" + iteration + "] Pool close does not block", closeDone.await(10, TimeUnit.SECONDS));
            assertTrue("[" + iteration + "] Pool close ok", closeSuccess.await(10, TimeUnit.MILLISECONDS));

            assertTrue("[" + iteration + "] sender complete", senderDone.await(30, TimeUnit.SECONDS));
        }
    }

};