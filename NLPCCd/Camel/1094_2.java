//,temp,sample_1497.java,2,10,temp,sample_1094.java,2,12
//,3
public class xxx {
protected GenericFileProcessStrategy<T> createGenericFileStrategy() {
Class<?> factory = null;
try {
FactoryFinder finder = getCamelContext().getFactoryFinder("META-INF/services/org/apache/camel/component/");
factory = finder.findClass(getScheme(), "strategy.factory.", CamelContext.class);
} catch (ClassNotFoundException e) {


log.info("strategy factory class not found");
}
}

};