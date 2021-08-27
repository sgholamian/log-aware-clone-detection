//,temp,sample_5561.java,2,20,temp,sample_1019.java,2,20
//,2
public class xxx {
public void dummy_method(){
boolean someRowsFilteredOut =  false;
if (bigTableFilterExpressions.length > 0) {
for (VectorExpression ve : bigTableFilterExpressions) {
ve.evaluate(batch);
}
someRowsFilteredOut = (batch.size != inputLogicalSize);
if (LOG.isDebugEnabled()) {
if (batch.selectedInUse) {
if (inputSelectedInUse) {
} else {


log.info("inputlogicalsize filtered batch selected");
}
}
}
}
}

};