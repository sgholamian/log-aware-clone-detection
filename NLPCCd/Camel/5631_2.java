//,temp,sample_4576.java,2,18,temp,sample_5886.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (processInBody(exchange, properties)) {
final Set<String> argNames = properties.keySet();
final List<FacebookMethodsType> filteredMethods = filterMethods(candidates, MatchType.SUPER_SET, argNames.toArray(new String[argNames.size()]));
if (filteredMethods.isEmpty()) {
final Set<String> missing = getMissingProperties(endpoint.getMethod(), endpoint.getNameStyle(), argNames);
throw new RuntimeCamelException(String.format("Missing properties for %s, need one or more from %s", endpoint.getMethod(), missing));
} else if (filteredMethods.size() == 1) {
method = filteredMethods.get(0);
} else {
method = FacebookMethodsTypeHelper.getHighestPriorityMethod(filteredMethods);


log.info("calling highest priority method from methods");
}
}
}

};