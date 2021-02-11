//,temp,sample_473.java,2,11,temp,sample_1954.java,2,12
//,3
public class xxx {
public void perform() throws Exception {
HBaseTestingUtility util = context.getHBaseIntegrationTestingUtility();
Admin admin = util.getAdmin();
if (context.isStopping()) {
return;
}


log.info("performing action flush table");
}

};