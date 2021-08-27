//,temp,sample_154.java,2,10,temp,sample_3901.java,2,10
//,2
public class xxx {
public void handle(Context withinContext) throws Exception {
AddPartitionMessage apm = deserializer.getAddPartitionMessage(event.getMessage());
org.apache.hadoop.hive.metastore.api.Table tobj = apm.getTableObj();
if (tobj == null) {


log.info("event was a add ptn event with no table listed");
}
}

};