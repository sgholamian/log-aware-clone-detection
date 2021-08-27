//,temp,sample_5459.java,2,18,temp,sample_5458.java,2,18
//,3
public class xxx {
public void dummy_method(){
for (TableScanOperator retainableTsOp : prevTsOps) {
if (removedOps.contains(retainableTsOp)) {
continue;
}
boolean mergeable = areMergeable(pctx, optimizerCache, retainableTsOp, discardableTsOp);
if (!mergeable) {
continue;
}
SharedResult sr = extractSharedOptimizationInfoForRoot( pctx, optimizerCache, retainableTsOp, discardableTsOp);
if (!validPreConditions(pctx, optimizerCache, sr)) {


log.info("and do not meet preconditions");
}
}
}

};