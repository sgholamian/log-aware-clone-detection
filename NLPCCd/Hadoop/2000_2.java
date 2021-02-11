//,temp,sample_2335.java,2,19,temp,sample_3487.java,2,15
//,3
public class xxx {
public Set<String> getAccessibleNodeLabels(String queue) {
String accessibleLabelStr = get(getQueuePrefix(queue) + ACCESSIBLE_NODE_LABELS);
if (accessibleLabelStr == null) {
if (!queue.equals(ROOT)) {
return null;
}
} else {
if (queue.equals(ROOT)) {


log.info("accessible node labels for root queue will be ignored it will be automatically set to");
}
}
}

};