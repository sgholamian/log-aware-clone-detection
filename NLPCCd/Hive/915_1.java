//,temp,sample_1797.java,2,17,temp,sample_1796.java,2,17
//,3
public class xxx {
public void dummy_method(){
getSparkJobInfo(sparkJobStatus, rc);
if (rc == 0) {
sparkStatistics = sparkJobStatus.getSparkStatistics();
if (LOG.isInfoEnabled() && sparkStatistics != null) {
LOG.info(String.format("=====Spark Job[%s] statistics=====", jobRef.getJobId()));
logSparkStatistic(sparkStatistics);
}
} else if (rc == 2) {
killJob();
} else if (rc == 4) {


log.info("the spark job or one stage of it has too many tasks cancelling spark job with application id");
}
}

};