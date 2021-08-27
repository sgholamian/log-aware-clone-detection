//,temp,sample_316.java,2,16,temp,sample_315.java,2,16
//,3
public class xxx {
public void dummy_method(){
String parameterDeclaration = makeParameterDeclarationStringObj(params);
query.declareParameters(parameterDeclaration);
if (ascending) {
query.setOrdering("partitionName ascending");
} else {
query.setOrdering("partitionName descending");
}
query.setResult("partitionName");
Collection<String> names = (Collection<String>) query.executeWithMap(params);
partNames = new ArrayList<String>(names);


log.info("done executing query for getpartitionnamesbyfilter");
}

};