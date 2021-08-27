//,temp,HdfsProducer.java,130,159,temp,HdfsConsumer.java,86,104
//,3
public class xxx {
    private HdfsInfo setupHdfs(boolean onStartup) throws IOException {
        String hdfsFsDescription = endpointConfig.getFileSystemLabel(hdfsPath.toString());
        // if we are starting up then log at info level, and if runtime then log at debug level to not flood the log
        if (onStartup) {
            LOG.info("Connecting to hdfs file-system {} (may take a while if connection is not available)", hdfsFsDescription);
        } else {
            LOG.debug("Connecting to hdfs file-system {} (may take a while if connection is not available)", hdfsFsDescription);
        }

        // hadoop will cache the connection by default so its faster to get in the poll method
        HdfsInfo answer = hdfsInfoFactory.newHdfsInfo(this.hdfsPath.toString());

        if (onStartup) {
            LOG.info("Connected to hdfs file-system {}", hdfsFsDescription);
        } else {
            LOG.debug("Connected to hdfs file-system {}", hdfsFsDescription);
        }
        return answer;
    }

};