//,temp,sample_1380.java,2,12,temp,sample_3038.java,2,8
//,3
public class xxx {
public void perform() throws Exception {
HBaseTestingUtility util = context.getHBaseIntegrationTestingUtility();
String snapshotName = tableName + "-it-" + System.currentTimeMillis();
Admin admin = util.getAdmin();
if (context.isStopping()) {
return;
}


log.info("performing action snapshot table");
}

};