//,temp,sample_3378.java,2,15,temp,sample_3377.java,2,13
//,3
public class xxx {
private boolean checkShuffleSizeForLargeTable(JoinOperator joinOp, int position, OptimizeTezProcContext context) {
long max = HiveConf.getLongVar(context.parseContext.getConf(), HiveConf.ConfVars.HIVECONVERTJOINMAXSHUFFLESIZE);
if (max < 1) {
return false;
}
ReduceSinkOperator rsOp = (ReduceSinkOperator) joinOp.getParentOperators().get(position);
Statistics inputStats = rsOp.getStatistics();
long inputSize = inputStats.getDataSize();


log.info("estimated size for input max size for dphj conversion");
}

};