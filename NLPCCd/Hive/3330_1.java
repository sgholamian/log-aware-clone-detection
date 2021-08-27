//,temp,sample_4244.java,2,14,temp,sample_4751.java,2,14
//,2
public class xxx {
private void evaluateWork(BaseWork w) throws SemanticException {
if (w instanceof MapWork) {
evaluateMapWork((MapWork) w);
} else if (w instanceof ReduceWork) {
evaluateReduceWork((ReduceWork) w);
} else if (w instanceof MergeJoinWork) {
evaluateMergeWork((MergeJoinWork) w);
} else {


log.info("we are not going to evaluate this work type");
}
}

};