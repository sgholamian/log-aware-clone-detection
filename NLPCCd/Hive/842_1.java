//,temp,sample_81.java,2,15,temp,sample_83.java,2,17
//,3
public class xxx {
public static int getNumBitVectorsForNDVEstimation(Configuration conf) throws Exception {
int numBitVectors;
float percentageError = HiveConf.getFloatVar(conf, HiveConf.ConfVars.HIVE_STATS_NDV_ERROR);
if (percentageError < 0.0) {
throw new Exception("hive.stats.ndv.error can't be negative");
} else if (percentageError <= 2.4) {
numBitVectors = 1024;
} else if (percentageError <= 3.4 ) {
numBitVectors = 1024;


log.info("choosing bit vectors");
}
}

};