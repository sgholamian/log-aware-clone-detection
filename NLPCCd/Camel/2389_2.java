//,temp,sample_2649.java,2,13,temp,sample_2650.java,2,13
//,2
public class xxx {
public void testConsumerInOut() throws Exception {
CamelContext context = new DefaultCamelContext();
context.addRoutes(createConsumerInOutRouteBuilder());
try {
context.start();
} catch (Throwable t) {
Assert.assertEquals(IllegalArgumentException.class, t.getClass());


log.info("expected exception was thrown");
}
}

};