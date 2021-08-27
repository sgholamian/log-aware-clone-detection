//,temp,sample_4175.java,2,16,temp,sample_4177.java,2,16
//,3
public class xxx {
public void dummy_method(){
app.start();
AbstractXmlApplicationContext app2 = new ClassPathXmlApplicationContext("org/apache/camel/routepolicy/quartz/SpringQuartzClusteredAppTwo.xml");
app2.start();
CamelContext camel = app.getBean("camelContext", CamelContext.class);
MockEndpoint mock = camel.getEndpoint("mock:result", MockEndpoint.class);
mock.expectedMessageCount(1);
mock.expectedBodiesReceived("clustering PINGS!");
Thread.sleep(5000);
app.getBean("template", ProducerTemplate.class).sendBody("direct:start", "clustering");
mock.assertIsSatisfied();


log.info("the first app is going to crash now");
}

};