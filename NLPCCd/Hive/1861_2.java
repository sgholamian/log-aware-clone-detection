//,temp,sample_4081.java,2,13,temp,sample_4080.java,2,14
//,3
public class xxx {
private static Path createDummyFileForEmptyPartition(Path path, JobConf job, PartitionDesc partDesc, Path hiveScratchDir) throws Exception {
String strPath = path.toString();
if (partDesc.getTableDesc().isNonNative()) {
return path;
}
Properties props = SerDeUtils.createOverlayedProperties( partDesc.getTableDesc().getProperties(), partDesc.getProperties());
HiveOutputFormat outFileFormat = HiveFileFormatUtils.getHiveOutputFormat(job, partDesc);
boolean oneRow = partDesc.getInputFileFormatClass() == OneNullRowInputFormat.class;
Path newPath = createEmptyFile(hiveScratchDir, outFileFormat, job, props, oneRow);


log.info("changed input file to empty file");
}

};