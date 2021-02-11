//,temp,sample_1069.java,2,11,temp,sample_1070.java,2,13
//,3
public class xxx {
protected void runJob(String jobName, Configuration c, List<Scan> scans) throws IOException, InterruptedException, ClassNotFoundException {
Job job = new Job(c, jobName);
initJob(scans, job);
job.setReducerClass(ScanReducer.class);
job.setNumReduceTasks(1);
FileOutputFormat.setOutputPath(job, new Path(job.getJobName()));


log.info("started");
}

};