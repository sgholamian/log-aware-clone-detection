//,temp,sample_4705.java,2,15,temp,sample_4702.java,2,14
//,3
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


log.info("debugdisplayvertexinfo rowcolumnnames");
}

};