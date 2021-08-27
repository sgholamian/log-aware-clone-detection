//,temp,sample_2428.java,2,14,temp,sample_2429.java,2,17
//,3
public class xxx {
public void dummy_method(){
Configuration conf = ContextUtil.getConfiguration(job);
if (blockSize != null && !blockSize.isEmpty()) {
conf.setInt(ParquetOutputFormat.BLOCK_SIZE, Integer.parseInt(blockSize));
}
String enableDictionaryPage = tableProperties.getProperty(ParquetOutputFormat.ENABLE_DICTIONARY);
if (enableDictionaryPage != null && !enableDictionaryPage.isEmpty()) {
conf.setBoolean(ParquetOutputFormat.ENABLE_DICTIONARY, Boolean.parseBoolean(enableDictionaryPage));
}
String compressionName = tableProperties.getProperty(ParquetOutputFormat.COMPRESSION);
if (compressionName != null && !compressionName.isEmpty()) {


log.info("get override compression properties via tblproperties");
}
}

};