//,temp,sample_1018.java,2,14,temp,sample_8273.java,2,16
//,3
public class xxx {
public void testQuartzPersistentStoreRestart() throws Exception {
AbstractXmlApplicationContext app = new ClassPathXmlApplicationContext("org/apache/camel/component/quartz2/SpringQuartzPersistentStoreTest.xml");
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