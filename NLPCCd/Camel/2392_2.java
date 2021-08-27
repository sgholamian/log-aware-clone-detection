//,temp,sample_2651.java,2,13,temp,sample_2652.java,2,13
//,2
public class xxx {
public void testProducerInOut() throws Exception {
CamelContext context = new DefaultCamelContext();
context.addRoutes(createProducerInOutRouteBuilder());
try {
context.start();
} catch (Throwable t) {
Assert.assertEquals(IllegalArgumentException.class, t.getClass());


log.info("expected exception was thrown");
}
}

};