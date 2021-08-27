//,temp,sample_7147.java,2,17,temp,sample_176.java,2,17
//,3
public class xxx {
public void dummy_method(){
argNames.addAll(FacebookPropertiesHelper.getEndpointPropertyNames(endpoint.getConfiguration()));
argNames.add(READING_PROPERTY);
final String[] argNamesArray = argNames.toArray(new String[argNames.size()]);
List<FacebookMethodsType> filteredMethods = filterMethods( endpoint.getCandidates(), MatchType.SUPER_SET, argNamesArray);
if (filteredMethods.isEmpty()) {
throw new IllegalArgumentException( String.format("Missing properties for %s, need one or more from %s", endpoint.getMethod(), getMissingProperties(endpoint.getMethod(), endpoint.getNameStyle(), argNames)));
} else if (filteredMethods.size() == 1) {
result = filteredMethods.get(0);
} else {
result = getHighestPriorityMethod(filteredMethods);


log.info("using highest priority method from methods");
}
}

};