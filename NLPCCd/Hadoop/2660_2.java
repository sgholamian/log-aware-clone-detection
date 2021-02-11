//,temp,sample_7647.java,2,14,temp,sample_7648.java,2,14
//,2
public class xxx {
public int run(String[] args) throws Exception {
int exitCode = 1;
handleOpts(args);
FileSystem fs = null;
Path remoteRootLogDir = new Path(conf.get( YarnConfiguration.NM_REMOTE_APP_LOG_DIR, YarnConfiguration.DEFAULT_NM_REMOTE_APP_LOG_DIR));
String suffix = LogAggregationUtils.getRemoteNodeLogDirSuffix(conf);
Path workingDir = new Path(remoteRootLogDir, "archive-logs-work");
if (verbose) {


log.info("working dir");
}
}

};