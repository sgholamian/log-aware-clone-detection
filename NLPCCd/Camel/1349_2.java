//,temp,sample_1019.java,2,14,temp,sample_1512.java,2,16
//,3
public class xxx {
public void dummy_method(){
AbstractXmlApplicationContext app = new ClassPathXmlApplicationContext("org/apache/camel/component/quartz2/SpringQuartzConsumerClusteredAppOne.xml");
app.start();
AbstractXmlApplicationContext app2 = new ClassPathXmlApplicationContext("org/apache/camel/component/quartz2/SpringQuartzConsumerClusteredAppTwo.xml");
app2.start();
CamelContext camel = app.getBean("camelContext", CamelContext.class);
MockEndpoint mock = camel.getEndpoint("mock:result", MockEndpoint.class);
mock.expectedMinimumMessageCount(3);
mock.expectedMessagesMatches(new ClusteringPredicate(true));
Thread.sleep(5000);
mock.assertIsSatisfied();


log.info("the first app is going to crash now");
}

};