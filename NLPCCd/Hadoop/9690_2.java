//,temp,sample_1212.java,2,13,temp,sample_1214.java,2,17
//,3
public class xxx {
protected void commitJobInternal(JobContext context) throws IOException {
if (hasOutputPath()) {
Path finalOutput = getOutputPath();
FileSystem fs = finalOutput.getFileSystem(context.getConfiguration());
if (algorithmVersion == 1) {
for (FileStatus stat: getAllCommittedTaskPaths(context)) {
mergePaths(fs, stat, finalOutput);
}
}
if (skipCleanup) {


log.info("skip cleanup the temporary folders under job s output directory in commitjob");
}
}
}

};