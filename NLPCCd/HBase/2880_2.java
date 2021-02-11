//,temp,sample_1955.java,2,14,temp,sample_474.java,2,15
//,3
public class xxx {
public void perform() throws Exception {
HBaseTestingUtility util = context.getHBaseIntegrationTestingUtility();
Admin admin = util.getAdmin();
if (context.isStopping()) {
return;
}
try {
admin.flush(tableName);
} catch (Exception ex) {


log.info("flush failed might be caused by other chaos");
}
}

};