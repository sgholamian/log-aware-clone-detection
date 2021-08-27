//,temp,sample_2054.java,2,17,temp,sample_2055.java,2,17
//,3
public class xxx {
public void dummy_method(){
ListBucketingCtx lbCtx = fsOp.getConf().getLbCtx();
if (lbCtx != null && !lbCtx.getSkewedColNames().isEmpty() && !lbCtx.getSkewedColValues().isEmpty()) {
return null;
}
Table destTable = fsOp.getConf().getTable();
if (destTable == null) {
return null;
}
Operator<? extends OperatorDesc> fsParent = fsOp.getParentOperators().get(0);
if (allStaticPartitions(fsParent, fsOp.getConf().getDynPartCtx())) {


log.info("bailing out of sorted dynamic partition optimizer as all dynamic partition columns got constant folded static partitioning");
}
}

};