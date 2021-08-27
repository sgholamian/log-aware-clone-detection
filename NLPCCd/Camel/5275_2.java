//,temp,sample_5779.java,2,17,temp,sample_8206.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (isClustered) {
JobDetail existingJobDetail = getScheduler().getJobDetail(jobDetail.getName(), jobDetail.getGroup());
if (jobDetail.equals(existingJobDetail)) {
if (LOG.isInfoEnabled()) {
}
return;
}
}
getScheduler().scheduleJob(jobDetail, trigger);
if (LOG.isInfoEnabled()) {


log.info("scheduled trigger for action on route");
}
}

};