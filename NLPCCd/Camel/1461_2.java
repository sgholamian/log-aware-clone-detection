//,temp,sample_6825.java,2,16,temp,sample_4178.java,2,16
//,3
public class xxx {
public void dummy_method(){
app2.start();
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