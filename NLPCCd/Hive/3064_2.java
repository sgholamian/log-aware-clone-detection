//,temp,sample_154.java,2,10,temp,sample_3901.java,2,10
//,2
public class xxx {
public void handle(Context withinContext) throws Exception {
CreateTableMessage ctm = deserializer.getCreateTableMessage(event.getMessage());
org.apache.hadoop.hive.metastore.api.Table tobj = ctm.getTableObj();
if (tobj == null) {


log.info("event was a create table event with no table listed");
}
}

};