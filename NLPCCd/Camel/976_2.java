//,temp,sample_8274.java,2,16,temp,sample_5455.java,2,14
//,3
public class xxx {
public void testQuartzPersistentStoreRestart() throws Exception {
AbstractXmlApplicationContext app = new ClassPathXmlApplicationContext("org/apache/camel/component/quartz/SpringQuartzPersistentStoreTest.xml");
app.start();
CamelContext camel = app.getBean("camelContext", CamelContext.class);
assertNotNull(camel);
MockEndpoint mock = camel.getEndpoint("mock:result", MockEndpoint.class);
mock.expectedMinimumMessageCount(2);
mock.assertIsSatisfied();
app.stop();


log.info("restarting");
}

};