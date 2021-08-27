//,temp,sample_1287.java,2,16,temp,sample_2278.java,2,16
//,2
public class xxx {
public void dummy_method(){
NumDistinctValueEstimator oldEst = aggregateData.getNdvEstimator();
NumDistinctValueEstimator newEst = newData.getNdvEstimator();
long ndv = -1;
if (oldEst.canMerge(newEst)) {
oldEst.mergeEstimators(newEst);
ndv = oldEst.estimateNumDistinctValues();
aggregateData.setNdvEstimator(oldEst);
} else {
ndv = Math.max(aggregateData.getNumDVs(), newData.getNumDVs());
}


log.info("use bitvector to merge column s ndvs of and to be");
}

};