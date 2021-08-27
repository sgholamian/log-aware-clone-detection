//,temp,sample_4704.java,2,15,temp,sample_4706.java,2,15
//,2
public class xxx {
public void debugDisplayVertexInfo(BaseWork work) {
VectorizedRowBatchCtx vectorizedRowBatchCtx = work.getVectorizedRowBatchCtx();
String[] allColumnNames = vectorizedRowBatchCtx.getRowColumnNames();
TypeInfo[] columnTypeInfos = vectorizedRowBatchCtx.getRowColumnTypeInfos();
DataTypePhysicalVariation[] dataTypePhysicalVariations = vectorizedRowBatchCtx.getRowdataTypePhysicalVariations();
int partitionColumnCount = vectorizedRowBatchCtx.getPartitionColumnCount();
int virtualColumnCount = vectorizedRowBatchCtx.getVirtualColumnCount();
String[] scratchColumnTypeNames =vectorizedRowBatchCtx.getScratchColumnTypeNames();
DataTypePhysicalVariation[] scratchdataTypePhysicalVariations = vectorizedRowBatchCtx.getScratchDataTypePhysicalVariations();
LOG.debug("debugDisplayVertexInfo rowDataTypePhysicalVariations " + (dataTypePhysicalVariations == null ? "NULL" : Arrays.toString(dataTypePhysicalVariations)));


log.info("debugdisplayvertexinfo partitioncolumncount");
}

};