//,temp,sample_7562.java,2,10,temp,sample_4934.java,2,10
//,3
public class xxx {
public void testGuice() throws Exception {
Injector injector = Guice.createInjector(new MyModule());
MyConfigurableRoute2 instance = injector.getInstance(Key.get(MyConfigurableRoute2.class, Names.named("foo")));
assertNotNull("should have found a key for 'foo'", instance);
Collection<RouteBuilder> list = Injectors.getInstancesOf(injector, RouteBuilder.class);


log.info("routebuilder list");
}

};