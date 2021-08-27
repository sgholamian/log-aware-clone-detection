//,temp,sample_7562.java,2,10,temp,sample_4934.java,2,10
//,3
public class xxx {
public void testGuice() throws Exception {
Injector injector = Guice.createInjector(new CamelModuleWithMatchingRoutes());
Cheese cheese = injector.getInstance(Cheese.class);
assertNotNull("Should have cheese", cheese);
assertNotNull("Should have camelContext", cheese.getCamelContext());


log.info("got");
}

};