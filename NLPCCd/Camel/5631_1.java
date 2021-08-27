//,temp,sample_4576.java,2,18,temp,sample_5886.java,2,18
//,3
public class xxx {
public void dummy_method(){
final List<ApiMethod> candidates = endpoint.getCandidates();
if (processInBody(exchange, properties)) {
final Set<String> argNames = properties.keySet();
final List<ApiMethod> filteredMethods = methodHelper.filterMethods( candidates, ApiMethodHelper.MatchType.SUPER_SET, argNames);
if (filteredMethods.isEmpty()) {
throw new RuntimeCamelException(String.format("Missing properties for %s, need one or more from %s", endpoint.getMethodName(), methodHelper.getMissingProperties(endpoint.getMethodName(), argNames)) );
} else if (filteredMethods.size() == 1) {
method = filteredMethods.get(0);
} else {
method = ApiMethodHelper.getHighestPriorityMethod(filteredMethods);


log.info("calling highest priority operation from operations");
}
}
}

};