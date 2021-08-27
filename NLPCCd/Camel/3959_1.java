//,temp,sample_724.java,2,18,temp,sample_3349.java,2,18
//,3
public class xxx {
public void dummy_method(){
context.getComponent("properties", PropertiesComponent.class);
postProcessTest();
if (isUseRouteBuilder()) {
RoutesBuilder[] builders = createRouteBuilders();
for (RoutesBuilder builder : builders) {
context.addRoutes(builder);
}
boolean skip = "true".equalsIgnoreCase(System.getProperty("skipStartingCamelContext"));
if (skip) {
} else if (isUseAdviceWith()) {


log.info("skipping starting camelcontext as isuseadvicewith is set to true");
}
}
}

};