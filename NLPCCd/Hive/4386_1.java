//,temp,sample_2090.java,2,16,temp,sample_2061.java,2,16
//,3
public class xxx {
public void dummy_method(){
RowSchema selRS = new RowSchema(granularitySelOp.getSchema());
SelectDesc selConf = new SelectDesc(descs, colNames);
SelectOperator backtrackSelOp = (SelectOperator) OperatorFactory.getAndMakeChild( selConf, selRS, rsOp);
fsOp.getParentOperators().clear();
fsOp.getParentOperators().add(backtrackSelOp);
backtrackSelOp.getChildOperators().add(fsOp);
fsOp.getConf().setDpSortState(FileSinkDesc.DPSortState.PARTITION_SORTED);
fsOp.getConf().setPartitionCols(rsOp.getConf().getPartitionCols());
ColumnInfo ci = new ColumnInfo(granularitySelOp.getSchema().getSignature().get( granularitySelOp.getSchema().getSignature().size() - 1));
fsOp.getSchema().getSignature().add(ci);


log.info("inserted and as parent of and child of");
}

};