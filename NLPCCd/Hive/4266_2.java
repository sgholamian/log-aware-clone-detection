//,temp,sample_5459.java,2,18,temp,sample_5458.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (removedOps.contains(discardableTsOp)) {
continue;
}
Collection<TableScanOperator> prevTsOps = existingOps.get(tableName);
for (TableScanOperator retainableTsOp : prevTsOps) {
if (removedOps.contains(retainableTsOp)) {
continue;
}
boolean mergeable = areMergeable(pctx, optimizerCache, retainableTsOp, discardableTsOp);
if (!mergeable) {


log.info("and cannot be merged");
}
}
}

};