//,temp,sample_3688.java,2,17,temp,sample_3689.java,2,17
//,2
public class xxx {
public void dummy_method(){
FilterOperator filter = (FilterOperator) nd;
FilterDesc desc = filter.getConf();
if (!parseContext.getConf().getBoolVar(ConfVars.TEZ_DYNAMIC_PARTITION_PRUNING) && !parseContext.getConf().isSparkDPPAny()) {
return null;
}
TableScanOperator ts = null;
if (filter.getParentOperators().size() == 1 && filter.getParentOperators().get(0) instanceof TableScanOperator) {
ts = (TableScanOperator) filter.getParentOperators().get(0);
}
if (LOG.isDebugEnabled()) {


log.info("tablescan");
}
}

};