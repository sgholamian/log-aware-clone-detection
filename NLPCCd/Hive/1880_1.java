//,temp,sample_3365.java,2,18,temp,sample_3420.java,2,18
//,3
public class xxx {
public void dummy_method(){
int bigTablePosition = -1;
long bigInputCumulativeCardinality = -1L;
Statistics bigInputStat = null;
boolean foundInputNotFittingInMemory = false;
long totalSize = 0;
boolean convertDPHJ = false;
for (int pos = 0; pos < joinOp.getParentOperators().size(); pos++) {
Operator<? extends OperatorDesc> parentOp = joinOp.getParentOperators().get(pos);
Statistics currInputStat = parentOp.getStatistics();
if (currInputStat == null) {


log.info("couldn t get statistics from");
}
}
}

};