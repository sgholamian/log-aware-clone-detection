//,temp,sample_2775.java,2,8,temp,sample_2777.java,2,8
//,3
public class xxx {
public ReduceWork createReduceWork(GenSparkProcContext context, Operator<?> root, SparkWork sparkWork) throws SemanticException {
Preconditions.checkArgument(!root.getParentOperators().isEmpty(), "AssertionError: expected root.getParentOperators() to be non-empty");
ReduceWork reduceWork = new ReduceWork("Reducer " + (++sequenceNumber));


log.info("adding reduce work for");
}

};