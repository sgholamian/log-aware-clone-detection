//,temp,sample_3482.java,2,15,temp,sample_3477.java,2,15
//,2
public class xxx {
public void oneMasterOneSlaveScenarioContolledByPolicy() throws Exception {
final String path = "oneMasterOneSlaveScenarioContolledByPolicy";
final String firstDestination = "first" + System.currentTimeMillis();
final String secondDestination = "second" + System.currentTimeMillis();
final CountDownLatch waitForSecondRouteCompletedLatch = new CountDownLatch(1);
final int activeNodesDesired = 1;
MultiMasterZookeeperPolicyEnforcedContext first = createEnforcedContext(firstDestination, activeNodesDesired, path);
DefaultCamelContext controlledContext = (DefaultCamelContext) first.controlledContext;
CuratorMultiMasterLeaderRoutePolicy routePolicy = (CuratorMultiMasterLeaderRoutePolicy) controlledContext.getRouteDefinition(firstDestination).getRoutePolicies().get(0);
assertWeHaveMasters(routePolicy);


log.info("starting first camelcontext");
}

};