//,temp,HdfsProducer.java,130,159,temp,HdfsConsumer.java,86,104
//,3
public class xxx {
    private synchronized HdfsOutputStream setupHdfs(boolean onStartup) throws IOException {
        if (oStream != null) {
            return oStream;
        }

        StringBuilder actualPath = new StringBuilder(hdfsPath);
        if (config.hasSplitStrategies()) {
            actualPath = newFileName();
        }

        String hdfsFsDescription = config.getFileSystemLabel(actualPath.toString());

        // if we are starting up then log at info level, and if runtime then log at debug level to not flood the log
        if (onStartup) {
            LOG.info("Connecting to hdfs file-system {} (may take a while if connection is not available)", hdfsFsDescription);
        } else {
            LOG.debug("Connecting to hdfs file-system {} (may take a while if connection is not available)", hdfsFsDescription);
        }

        HdfsInfoFactory hdfsInfoFactory = new HdfsInfoFactory(config);
        HdfsOutputStream answer = HdfsOutputStream.createOutputStream(actualPath.toString(), hdfsInfoFactory);

        if (onStartup) {
            LOG.info("Connected to hdfs file-system {}", hdfsFsDescription);
        } else {
            LOG.debug("Connected to hdfs file-system {}", hdfsFsDescription);
        }

        return answer;
    }

};