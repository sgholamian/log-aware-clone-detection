//,temp,sample_2235.java,2,9,temp,sample_2236.java,2,11
//,3
public class xxx {
private static void annotateRuntimeStats(Operator<? extends OperatorDesc> op, ParseContext pctx) {
Long runTimeNumRows = pctx.getContext().getExplainConfig().getOpIdToRuntimeNumRows() .get(op.getOperatorId());
if (op.getConf() != null && op.getConf().getStatistics() != null && runTimeNumRows != null) {


log.info("annotateruntimestats for");
}
}

};