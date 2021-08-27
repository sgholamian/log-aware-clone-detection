//,temp,sample_691.java,2,20,temp,sample_690.java,2,20
//,3
public class xxx {
public void dummy_method(){
int bigTablePosition = -1;
Statistics bigInputStat = null;
long totalSize = 0;
int pos = 0;
boolean bigTableFound = false;
boolean useTsStats = context.getConf().getBoolean(HiveConf.ConfVars.SPARK_USE_TS_STATS_FOR_MAPJOIN.varname, false);
if (useTsStats) {
for (Operator<? extends OperatorDesc> parentOp : joinOp.getParentOperators()) {
if (isBigTableBranch(parentOp)) {
if (bigTablePosition < 0 && bigTableCandidateSet.contains(pos) && !containUnionWithoutRS(parentOp.getParentOperators().get(0))) {


log.info("found a big table branch with parent operator and position");
}
}
}
}
}

};