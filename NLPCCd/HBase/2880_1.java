//,temp,sample_1955.java,2,14,temp,sample_474.java,2,15
//,3
public class xxx {
protected void forceBalancer() throws Exception {
Admin admin = this.context.getHBaseIntegrationTestingUtility().getAdmin();
boolean result = false;
try {
result = admin.balancer();
} catch (Exception e) {
}
if (!result) {


log.info("balancer didn t succeed");
}
}

};