//,temp,JmsDefaultTaskExecutorTypeTest.java,90,107,temp,JmsDefaultTaskExecutorTypeTest.java,45,58
//,3
public class xxx {
    @Test
    public void testDefaultTaskExecutorThreadPoolAtComponentConfig() throws Exception {
        // change the config of the component
        context.getComponent("jms", JmsComponent.class).getConfiguration()
                .setDefaultTaskExecutorType(DefaultTaskExecutorType.ThreadPool);

        context.getRouteController().startRoute("default");
        Long beforeThreadCount = currentThreadCount();
        getMockEndpoint("mock:result.default").expectedMessageCount(1000);
        doSendMessages("foo.default", 500, 5, DefaultTaskExecutorType.ThreadPool);
        Thread.sleep(100);
        doSendMessages("foo.default", 500, 5, DefaultTaskExecutorType.ThreadPool);
        assertMockEndpointsSatisfied();
        Long numberThreadsCreated = currentThreadCount() - beforeThreadCount;
        LOG.info("Number of threads created, testDefaultTaskExecutorThreadPoolAtComponentConfig: " + numberThreadsCreated);
        assertTrue(numberThreadsCreated <= 100, "Number of threads created should be equal or lower than "
                                                + "100 with ThreadPoolTaskExecutor as a component default");
    }

};