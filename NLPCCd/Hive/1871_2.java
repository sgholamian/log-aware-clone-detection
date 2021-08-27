//,temp,sample_4680.java,2,19,temp,sample_4686.java,2,19
//,3
public class xxx {
public void dummy_method(){
currentBaseWork = reduceWork;
currentBaseWork.setVectorizationEnabled(true);
VectorTaskColumnInfo vectorTaskColumnInfo = new VectorTaskColumnInfo();
vectorTaskColumnInfo.assume();
reduceWork.setVectorizedVertexNum(++vectorizedVertexNum);
reduceWork.setVectorizedTestingReducerBatchSize(vectorizedTestingReducerBatchSize);
if (!validateAndVectorizeReduceWork(reduceWork, vectorTaskColumnInfo)) {
if (currentBaseWork.getVectorizationEnabled()) {
VectorizerReason notVectorizedReason  = currentBaseWork.getNotVectorizedReason();
if (notVectorizedReason == null) {


log.info("cannot vectorize unknown");
}
}
}
}

};