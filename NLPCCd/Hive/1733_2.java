//,temp,sample_618.java,2,17,temp,sample_617.java,2,16
//,3
public class xxx {
protected Partition resolvePartition(CompactionInfo ci) throws Exception {
if (ci.partName != null) {
List<Partition> parts;
try {
parts = rs.getPartitionsByNames(ci.dbname, ci.tableName, Collections.singletonList(ci.partName));
if (parts == null || parts.size() == 0) {
return null;
}
} catch (Exception e) {


log.info("unable to find partition");
}
}
}

};