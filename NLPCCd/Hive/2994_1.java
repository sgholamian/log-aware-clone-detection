//,temp,sample_942.java,2,19,temp,sample_941.java,2,12
//,3
public class xxx {
public void dummy_method(){
long colProjSize = inputSplit.getLength();
if (inputSplit instanceof ColumnarSplit) {
colProjSize = ((ColumnarSplit) inputSplit).getColumnarProjectionSize();
if (LOG.isDebugEnabled()) {
}
} else if (inputSplit instanceof HiveInputFormat.HiveInputSplit) {
InputSplit innerSplit = ((HiveInputFormat.HiveInputSplit) inputSplit).getInputSplit();
if (innerSplit instanceof ColumnarSplit) {
colProjSize = ((ColumnarSplit) innerSplit).getColumnarProjectionSize();
if (LOG.isDebugEnabled()) {


log.info("estimated column projection size");
}
}
}
}

};