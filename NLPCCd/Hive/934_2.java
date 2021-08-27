//,temp,sample_4679.java,2,18,temp,sample_4687.java,2,19
//,3
public class xxx {
public void dummy_method(){
currentBaseWork.setVectorizationEnabled(true);
VectorTaskColumnInfo vectorTaskColumnInfo = new VectorTaskColumnInfo();
vectorTaskColumnInfo.assume();
reduceWork.setVectorizedVertexNum(++vectorizedVertexNum);
reduceWork.setVectorizedTestingReducerBatchSize(vectorizedTestingReducerBatchSize);
if (!validateAndVectorizeReduceWork(reduceWork, vectorTaskColumnInfo)) {
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