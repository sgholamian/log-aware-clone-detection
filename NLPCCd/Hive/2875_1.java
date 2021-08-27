//,temp,sample_3693.java,2,17,temp,sample_3692.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (LOG.isDebugEnabled()) {
if (plist != null) {
for (Partition p : plist.getPartitions()) {
LOG.debug(p.getCompleteName());
}
}
}
if (plist == null || plist.getPartitions().size() != 0) {
generateEventOperatorPlan(ctx, parseContext, ts, column, columnType);
} else {


log.info("no partition pruning necessary");
}
}

};