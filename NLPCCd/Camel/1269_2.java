//,temp,sample_965.java,2,16,temp,sample_967.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (expressionFactory == null && context != null) {
try {
FactoryFinder finder = context.getFactoryFinder("META-INF/services/org/apache/camel/language/");
Class<?> clazz = finder.findClass("el", "impl.", ExpressionFactory.class);
if (clazz != null) {
expressionFactory = (ExpressionFactory)clazz.newInstance();
}
} catch (ClassNotFoundException e) {
} catch (IOException e) {
} catch (InstantiationException e) {


log.info("failed to instantiate juel expressionfactory implementation class");
}
}
}

};