//,temp,sample_4679.java,2,18,temp,sample_4687.java,2,19
//,3
public class xxx {
private void convertMapWork(MapWork mapWork, boolean isTezOrSpark) throws SemanticException {
mapWork.setVectorizationExamined(true);
currentBaseWork = mapWork;
VectorTaskColumnInfo vectorTaskColumnInfo = new VectorTaskColumnInfo();
vectorTaskColumnInfo.assume();
mapWork.setVectorizedVertexNum(++vectorizedVertexNum);
if (!validateAndVectorizeMapWork(mapWork, vectorTaskColumnInfo, isTezOrSpark)) {
if (currentBaseWork.getVectorizationEnabled()) {
VectorizerReason notVectorizedReason  = currentBaseWork.getNotVectorizedReason();
if (notVectorizedReason == null) {


log.info("cannot vectorize unknown");
}
}
}
}

};