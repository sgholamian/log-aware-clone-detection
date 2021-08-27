//,temp,sample_6826.java,2,16,temp,sample_4176.java,2,16
//,3
public class xxx {
public void dummy_method(){
AbstractXmlApplicationContext app2 = new ClassPathXmlApplicationContext("org/apache/camel/routepolicy/quartz2/SpringQuartzClusteredAppTwo.xml");
CamelContext camel = app.getBean("camelContext", CamelContext.class);
MockEndpoint mock = camel.getEndpoint("mock:result", MockEndpoint.class);
mock.expectedMessageCount(1);
mock.expectedBodiesReceived("clustering PINGS!");
Thread.sleep(5000);
app.getBean("template", ProducerTemplate.class).sendBody("direct:start", "clustering");
mock.assertIsSatisfied();
app.getBean(Scheduler.class).shutdown();
IOHelper.close(app);


log.info("crashed");
}

};