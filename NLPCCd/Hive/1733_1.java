//,temp,sample_618.java,2,17,temp,sample_617.java,2,16
//,3
public class xxx {
public void dummy_method(){
List<Partition> parts;
try {
parts = rs.getPartitionsByNames(ci.dbname, ci.tableName, Collections.singletonList(ci.partName));
if (parts == null || parts.size() == 0) {
return null;
}
} catch (Exception e) {
throw e;
}
if (parts.size() != 1) {


log.info("does not refer to a single partition");
}
}

};