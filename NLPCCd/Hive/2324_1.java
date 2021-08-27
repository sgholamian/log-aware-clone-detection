//,temp,sample_3372.java,2,9,temp,sample_3373.java,2,10
//,3
public class xxx {
private void fallbackToMergeJoin(JoinOperator joinOp, OptimizeTezProcContext context) throws SemanticException {
int pos = getMapJoinConversionPos(joinOp, context, estimateNumBuckets(joinOp, false), true, Long.MAX_VALUE, false);
if (pos < 0) {


log.info("could not get a valid join position defaulting to position");
}
}

};