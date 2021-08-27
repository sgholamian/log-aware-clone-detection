//,temp,sample_4081.java,2,13,temp,sample_4080.java,2,14
//,3
public class xxx {
private static Path createDummyFileForEmptyTable(JobConf job, MapWork work, Path hiveScratchDir, String alias) throws Exception {
TableDesc tableDesc = work.getAliasToPartnInfo().get(alias).getTableDesc();
if (tableDesc.isNonNative()) {
return null;
}
Properties props = tableDesc.getProperties();
HiveOutputFormat outFileFormat = HiveFileFormatUtils.getHiveOutputFormat(job, tableDesc);
Path newPath = createEmptyFile(hiveScratchDir, outFileFormat, job, props, false);


log.info("changed input file for alias to newpath");
}

};