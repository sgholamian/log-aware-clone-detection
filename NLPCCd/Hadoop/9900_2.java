//,temp,sample_8792.java,2,18,temp,sample_8793.java,2,17
//,3
public class xxx {
public void dummy_method(){
TimelineEntityConverterV2 converter = new TimelineEntityConverterV2();
Collection<JobFiles> jobs = helper.getJobFiles();
if (jobs.isEmpty()) {
} else {
}
for (JobFiles job: jobs) {
String jobIdStr = job.getJobId();
if (job.getJobConfFilePath() == null || job.getJobHistoryFilePath() == null) {
continue;
}


log.info("processing");
}
}

};