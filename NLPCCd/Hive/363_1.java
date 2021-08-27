//,temp,sample_2427.java,2,10,temp,sample_5549.java,2,13
//,3
public class xxx {
private void initializeSerProperties(JobContext job, Properties tableProperties) {
String blockSize = tableProperties.getProperty(ParquetOutputFormat.BLOCK_SIZE);
Configuration conf = ContextUtil.getConfiguration(job);
if (blockSize != null && !blockSize.isEmpty()) {


log.info("get override parquet block size property via tblproperties");
}
}

};