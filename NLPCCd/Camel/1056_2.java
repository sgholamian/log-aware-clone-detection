//,temp,sample_3562.java,2,14,temp,sample_3561.java,2,14
//,3
public class xxx {
public void testSimpleAsyncTaskExecutor() throws Exception {
context.startRoute("simpleAsync");
Long beforeThreadCount = currentThreadCount();
getMockEndpoint("mock:result.simpleAsync").expectedMessageCount(1000);
doSendMessages("foo.simpleAsync", 500, 5, DefaultTaskExecutorType.SimpleAsync);
Thread.sleep(100);
doSendMessages("foo.simpleAsync", 500, 5, DefaultTaskExecutorType.SimpleAsync);
assertMockEndpointsSatisfied();
Long numberThreadsCreated = currentThreadCount() - beforeThreadCount;


log.info("number of threads created testsimpleasynctaskexecutor");
}

};