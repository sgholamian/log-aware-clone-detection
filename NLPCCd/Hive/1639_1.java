//,temp,sample_691.java,2,20,temp,sample_690.java,2,20
//,3
public class xxx {
public void dummy_method(){
boolean bigTableFound = false;
boolean useTsStats = context.getConf().getBoolean(HiveConf.ConfVars.SPARK_USE_TS_STATS_FOR_MAPJOIN.varname, false);
if (useTsStats) {
for (Operator<? extends OperatorDesc> parentOp : joinOp.getParentOperators()) {
if (isBigTableBranch(parentOp)) {
if (bigTablePosition < 0 && bigTableCandidateSet.contains(pos) && !containUnionWithoutRS(parentOp.getParentOperators().get(0))) {
bigTablePosition = pos;
bigTableFound = true;
bigInputStat = new Statistics(0, Long.MAX_VALUE);
} else {


log.info("cannot enable map join optimization for operator");
}
}
}
}
}

};