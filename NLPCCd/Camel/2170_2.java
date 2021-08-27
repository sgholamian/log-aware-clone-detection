//,temp,sample_3563.java,2,14,temp,sample_3560.java,2,14
//,2
public class xxx {
public void testThreadPoolTaskExecutor() throws Exception {
context.startRoute("threadPool");
Long beforeThreadCount = currentThreadCount();
getMockEndpoint("mock:result.threadPool").expectedMessageCount(1000);
doSendMessages("foo.threadPool", 500, 5, DefaultTaskExecutorType.ThreadPool);
Thread.sleep(100);
doSendMessages("foo.threadPool", 500, 5, DefaultTaskExecutorType.ThreadPool);
assertMockEndpointsSatisfied();
Long numberThreadsCreated = currentThreadCount() - beforeThreadCount;


log.info("number of threads created testthreadpooltaskexecutor");
}

};