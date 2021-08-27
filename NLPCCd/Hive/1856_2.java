//,temp,sample_4694.java,2,17,temp,sample_4693.java,2,14
//,3
public class xxx {
private boolean isBigTableOnlyResults(MapJoinDesc desc) {
Byte[] order = desc.getTagOrder();
byte posBigTable = (byte) desc.getPosBigTable();
Byte posSingleVectorMapJoinSmallTable = (order[0] == posBigTable ? order[1] : order[0]);
int[] smallTableIndices;
int smallTableIndicesSize;
if (desc.getValueIndices() != null && desc.getValueIndices().get(posSingleVectorMapJoinSmallTable) != null) {
smallTableIndices = desc.getValueIndices().get(posSingleVectorMapJoinSmallTable);


log.info("vectorizer isbigtableonlyresults smalltableindices");
}
}

};