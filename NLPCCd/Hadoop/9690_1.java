//,temp,sample_1212.java,2,13,temp,sample_1214.java,2,17
//,3
public class xxx {
public void setupJob(JobContext context) throws IOException {
if (hasOutputPath()) {
Path jobAttemptPath = getJobAttemptPath(context);
FileSystem fs = jobAttemptPath.getFileSystem( context.getConfiguration());
if (!fs.mkdirs(jobAttemptPath)) {
}
} else {


log.info("output path is null in setupjob");
}
}

};