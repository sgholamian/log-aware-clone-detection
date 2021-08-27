//,temp,sample_966.java,2,17,temp,sample_1095.java,2,13
//,3
public class xxx {
protected GenericFileProcessStrategy<T> createGenericFileStrategy() {
Class<?> factory = null;
try {
FactoryFinder finder = getCamelContext().getFactoryFinder("META-INF/services/org/apache/camel/component/");
factory = finder.findClass(getScheme(), "strategy.factory.", CamelContext.class);
} catch (ClassNotFoundException e) {
} catch (IOException e) {


log.info("no strategy factory defined in meta inf services org apache camel component");
}
}

};