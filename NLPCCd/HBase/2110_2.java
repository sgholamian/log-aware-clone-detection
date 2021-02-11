//,temp,sample_473.java,2,11,temp,sample_1954.java,2,12
//,3
public class xxx {
protected void forceBalancer() throws Exception {
Admin admin = this.context.getHBaseIntegrationTestingUtility().getAdmin();
boolean result = false;
try {
result = admin.balancer();
} catch (Exception e) {


log.info("got exception while doing balance");
}
}

};