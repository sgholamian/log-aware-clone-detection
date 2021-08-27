//,temp,sample_7147.java,2,17,temp,sample_176.java,2,17
//,3
public class xxx {
public void dummy_method(){
final Set<String> argNames = new HashSet<>(endpoint.getEndpointPropertyNames());
propertyNamesInterceptor.interceptPropertyNames(argNames);
List<ApiMethod> filteredMethods = endpoint.methodHelper.filterMethods( endpoint.getCandidates(), ApiMethodHelper.MatchType.SUPER_SET, argNames);
if (filteredMethods.isEmpty()) {
ApiMethodHelper<? extends ApiMethod> methodHelper = endpoint.getMethodHelper();
throw new IllegalArgumentException( String.format("Missing properties for %s/%s, need one or more from %s", endpoint.getApiName().getName(), endpoint.getMethodName(), methodHelper.getMissingProperties(endpoint.getMethodName(), argNames)));
} else if (filteredMethods.size() == 1) {
result = filteredMethods.get(0);
} else {
result = ApiMethodHelper.getHighestPriorityMethod(filteredMethods);


log.info("using highest priority operation s from operations s for endpoint s");
}
}

};