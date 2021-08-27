//,temp,sample_3563.java,2,14,temp,sample_3560.java,2,14
//,2
public class xxx {
public void testDefaultTaskExecutorThreadPoolAtComponentConfig() throws Exception {
context.startRoute("default");
Long beforeThreadCount = currentThreadCount();
getMockEndpoint("mock:result.default").expectedMessageCount(1000);
doSendMessages("foo.default", 500, 5, DefaultTaskExecutorType.ThreadPool);
Thread.sleep(100);
doSendMessages("foo.default", 500, 5, DefaultTaskExecutorType.ThreadPool);
assertMockEndpointsSatisfied();
Long numberThreadsCreated = currentThreadCount() - beforeThreadCount;


log.info("number of threads created testdefaulttaskexecutorthreadpoolatcomponentconfig");
}

};