//,temp,sample_5363.java,2,10,temp,sample_5364.java,2,12
//,3
public class xxx {
public static RowResolver getCombinedRR(RowResolver leftRR, RowResolver rightRR) throws SemanticException {
RowResolver combinedRR = new RowResolver();
IntRef outputColPos = new IntRef();
if (!add(combinedRR, leftRR, outputColPos)) {


log.info("duplicates detected when adding columns to rr see previous message");
}
}

};