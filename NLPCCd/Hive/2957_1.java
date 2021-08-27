//,temp,sample_2428.java,2,14,temp,sample_2429.java,2,17
//,3
public class xxx {
private void initializeSerProperties(JobContext job, Properties tableProperties) {
String blockSize = tableProperties.getProperty(ParquetOutputFormat.BLOCK_SIZE);
Configuration conf = ContextUtil.getConfiguration(job);
if (blockSize != null && !blockSize.isEmpty()) {
conf.setInt(ParquetOutputFormat.BLOCK_SIZE, Integer.parseInt(blockSize));
}
String enableDictionaryPage = tableProperties.getProperty(ParquetOutputFormat.ENABLE_DICTIONARY);
if (enableDictionaryPage != null && !enableDictionaryPage.isEmpty()) {


log.info("get override parquet enable dictionary property via tblproperties");
}
}

};