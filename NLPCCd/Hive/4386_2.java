//,temp,sample_2090.java,2,16,temp,sample_2061.java,2,16
//,3
public class xxx {
public void dummy_method(){
SelectDesc selConf = new SelectDesc(descs, colNames);
SelectOperator selOp = (SelectOperator) OperatorFactory.getAndMakeChild( selConf, selRS, rsOp);
fsOp.getParentOperators().clear();
fsOp.getParentOperators().add(selOp);
selOp.getChildOperators().add(fsOp);
fsOp.getConf().setDpSortState(FileSinkDesc.DPSortState.PARTITION_SORTED);
if (!bucketColumns.isEmpty()) {
fsOp.getConf().setDpSortState(FileSinkDesc.DPSortState.PARTITION_BUCKET_SORTED);
}
fsOp.getConf().setPartitionCols( rsOp.getConf().getPartitionCols());


log.info("inserted and as parent of and child of");
}

};