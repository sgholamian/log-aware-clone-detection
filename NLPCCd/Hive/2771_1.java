//,temp,sample_340.java,2,16,temp,sample_339.java,2,16
//,3
public class xxx {
public void dummy_method(){
if (maxParts >= 0) {
query.setRange(0, maxParts);
}
String parameterDeclaration = makeParameterDeclarationStringObj(params);
query.declareParameters(parameterDeclaration);
query.setOrdering("partitionName ascending");
query.setResult("partitionName");
Collection<String> names = (Collection<String>) query.executeWithMap(params);
partNames = new ArrayList<>(names);
success = commitTransaction();


log.info("done retrieving all objects for listmpartitionnamesbyfilter");
}

};