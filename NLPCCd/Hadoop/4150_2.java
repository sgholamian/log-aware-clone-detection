//,temp,sample_2843.java,2,8,temp,sample_2847.java,2,9
//,3
public class xxx {
public void testBecomingActiveFails() throws Exception {
cluster.start();
DummyHAService svc1 = cluster.getService(1);
cluster.setFailToBecomeActive(1, true);


log.info("faking unhealthy should not successfully failover to");
}

};