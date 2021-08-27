//,temp,sample_1912.java,2,11,temp,sample_1910.java,2,11
//,3
public class xxx {
private SparkStageInfo getSparkStageInfo(int stageId) {
Future<SparkStageInfo> getStageInfo = sparkClient.run(new GetStageInfoJob(stageId));
try {
return getStageInfo.get(sparkClientTimeoutInSeconds, TimeUnit.SECONDS);
} catch (Throwable t) {


log.info("error getting stage info");
}
}

};