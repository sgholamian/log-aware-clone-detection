//,temp,sample_5467.java,2,20,temp,sample_5457.java,2,20
//,3
public class xxx {
public void dummy_method(){
Multimap<Operator<?>, ReduceSinkOperator> existingRsOps = ArrayListMultimap.create();
for (Entry<Operator<?>, Long> rsGroupInfo : sortedRSGroups) {
Operator<?> rsParent = rsGroupInfo.getKey();
for (ReduceSinkOperator discardableRsOp : parentToRsOps.get(rsParent)) {
if (removedOps.contains(discardableRsOp)) {
continue;
}
Collection<ReduceSinkOperator> otherRsOps = existingRsOps.get(rsParent);
for (ReduceSinkOperator retainableRsOp : otherRsOps) {
if (removedOps.contains(retainableRsOp)) {


log.info("skip as it has already been removed");
}
}
}
}
}

};