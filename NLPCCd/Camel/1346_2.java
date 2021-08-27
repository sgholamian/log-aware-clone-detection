//,temp,sample_8272.java,2,16,temp,sample_8271.java,2,16
//,3
public class xxx {
public void dummy_method(){
AbstractXmlApplicationContext app = new ClassPathXmlApplicationContext("org/apache/camel/component/quartz/SpringQuartzConsumerClusteredAppOne.xml");
app.start();
AbstractXmlApplicationContext app2 = new ClassPathXmlApplicationContext("org/apache/camel/component/quartz/SpringQuartzConsumerClusteredAppTwo.xml");
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