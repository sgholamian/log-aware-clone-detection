//,temp,sample_2775.java,2,8,temp,sample_2777.java,2,8
//,3
public class xxx {
public MapWork createMapWork(GenSparkProcContext context, Operator<?> root, SparkWork sparkWork, PrunedPartitionList partitions, boolean deferSetup) throws SemanticException {
Preconditions.checkArgument(root.getParentOperators().isEmpty(), "AssertionError: expected root.getParentOperators() to be empty");
MapWork mapWork = new MapWork("Map " + (++sequenceNumber));


log.info("adding map work for");
}

};