//,temp,sample_4680.java,2,19,temp,sample_4686.java,2,19
//,3
public class xxx {
public void dummy_method(){
mapWork.setVectorizationExamined(true);
currentBaseWork = mapWork;
VectorTaskColumnInfo vectorTaskColumnInfo = new VectorTaskColumnInfo();
vectorTaskColumnInfo.assume();
mapWork.setVectorizedVertexNum(++vectorizedVertexNum);
if (!validateAndVectorizeMapWork(mapWork, vectorTaskColumnInfo, isTezOrSpark)) {
if (currentBaseWork.getVectorizationEnabled()) {
VectorizerReason notVectorizedReason  = currentBaseWork.getNotVectorizedReason();
if (notVectorizedReason == null) {
} else {


log.info("cannot vectorize");
}
}
}
}

};