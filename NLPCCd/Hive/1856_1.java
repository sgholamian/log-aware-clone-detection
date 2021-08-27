//,temp,sample_4694.java,2,17,temp,sample_4693.java,2,14
//,3
public class xxx {
public void dummy_method(){
Byte[] order = desc.getTagOrder();
byte posBigTable = (byte) desc.getPosBigTable();
Byte posSingleVectorMapJoinSmallTable = (order[0] == posBigTable ? order[1] : order[0]);
int[] smallTableIndices;
int smallTableIndicesSize;
if (desc.getValueIndices() != null && desc.getValueIndices().get(posSingleVectorMapJoinSmallTable) != null) {
smallTableIndices = desc.getValueIndices().get(posSingleVectorMapJoinSmallTable);
smallTableIndicesSize = smallTableIndices.length;
} else {
smallTableIndices = null;


log.info("vectorizer isbigtableonlyresults smalltableindices empty");
}
}

};