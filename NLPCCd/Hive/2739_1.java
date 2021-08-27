//,temp,sample_2054.java,2,17,temp,sample_2055.java,2,17
//,3
public class xxx {
public void dummy_method(){
FileSinkOperator fsOp = (FileSinkOperator) nd;
if (fsOp.getConf().getDynPartCtx() == null) {
return null;
}
ListBucketingCtx lbCtx = fsOp.getConf().getLbCtx();
if (lbCtx != null && !lbCtx.getSkewedColNames().isEmpty() && !lbCtx.getSkewedColValues().isEmpty()) {
return null;
}
Table destTable = fsOp.getConf().getTable();
if (destTable == null) {


log.info("bailing out of sort dynamic partition optimization as destination table is null");
}
}

};