//,temp,JmsDefaultTaskExecutorTypeTest.java,90,107,temp,JmsDefaultTaskExecutorTypeTest.java,45,58
//,3
public class xxx {
    @Test
    public void testThreadPoolTaskExecutor() throws Exception {
        context.getRouteController().startRoute("threadPool");
        Long beforeThreadCount = currentThreadCount();
        getMockEndpoint("mock:result.threadPool").expectedMessageCount(1000);
        doSendMessages("foo.threadPool", 500, 5, DefaultTaskExecutorType.ThreadPool);
        Thread.sleep(100);
        doSendMessages("foo.threadPool", 500, 5, DefaultTaskExecutorType.ThreadPool);
        assertMockEndpointsSatisfied();
        Long numberThreadsCreated = currentThreadCount() - beforeThreadCount;
        LOG.info("Number of threads created, testThreadPoolTaskExecutor: " + numberThreadsCreated);
        assertTrue(numberThreadsCreated <= 100, "Number of threads created should be equal or lower than "
                                                + "100 with ThreadPoolTaskExecutor");
    }

};