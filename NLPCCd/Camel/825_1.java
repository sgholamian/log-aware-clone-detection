//,temp,sample_966.java,2,17,temp,sample_1095.java,2,13
//,3
public class xxx {
public synchronized ExpressionFactory getExpressionFactory(CamelContext context) {
if (expressionFactory == null && context != null) {
try {
FactoryFinder finder = context.getFactoryFinder("META-INF/services/org/apache/camel/language/");
Class<?> clazz = finder.findClass("el", "impl.", ExpressionFactory.class);
if (clazz != null) {
expressionFactory = (ExpressionFactory)clazz.newInstance();
}
} catch (ClassNotFoundException e) {
} catch (IOException e) {


log.info("no impl class for juel expressionfactory defined in meta inf services org apache camel language el");
}
}
}

};