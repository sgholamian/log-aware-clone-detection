//,temp,sample_5071.java,2,13,temp,sample_5070.java,2,13
//,2
public class xxx {
public static void printAssignmentPlan(FavoredNodesPlan plan) {
if (plan == null) return;
LOG.info("========== Start to print the assignment plan ================");
Map<String, List<ServerName>> assignmentMap = new TreeMap<>(plan.getAssignmentMap());
for (Map.Entry<String, List<ServerName>> entry : assignmentMap.entrySet()) {
String serverList = FavoredNodeAssignmentHelper.getFavoredNodesAsString(entry.getValue());
String regionName = entry.getKey();


log.info("its favored nodes");
}
}

};