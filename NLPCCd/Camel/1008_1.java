//,temp,sample_1514.java,2,16,temp,sample_1017.java,2,14
//,3
public class xxx {
public void dummy_method(){
app.start();
AbstractXmlApplicationContext app2 = new ClassPathXmlApplicationContext("org/apache/camel/component/quartz2/SpringQuartzConsumerClusteredAppTwo.xml");
app2.start();
CamelContext camel = app.getBean("camelContext", CamelContext.class);
MockEndpoint mock = camel.getEndpoint("mock:result", MockEndpoint.class);
mock.expectedMinimumMessageCount(3);
mock.expectedMessagesMatches(new ClusteringPredicate(true));
Thread.sleep(5000);
mock.assertIsSatisfied();
IOHelper.close(app);


log.info("crashed");
}

};