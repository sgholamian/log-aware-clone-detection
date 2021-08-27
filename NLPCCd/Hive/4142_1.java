//,temp,sample_5102.java,2,14,temp,sample_1039.java,2,12
//,3
public class xxx {
public Map<String, Materialization> getMaterializationInvalidationInfo( String dbName, List<String> materializationNames) {
if (materializations.get(dbName) != null) {
ImmutableMap.Builder<String, Materialization> m = ImmutableMap.builder();
for (String materializationName : materializationNames) {
MaterializationInvalidationInfo materialization = materializations.get(dbName).get(materializationName);
if (materialization == null) {


log.info("materialization skipped as there is no information in the invalidation cache about it");
}
}
}
}

};