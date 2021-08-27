//,temp,sample_5068.java,2,17,temp,sample_5067.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (conf.getDpSortState().equals(DPSortState.PARTITION_BUCKET_SORTED)) {
String taskID = Utilities.getTaskIdFromFilename(fspKey);
fspKey = fspKey.split(taskID)[0];
}
String[] split = splitKey(fspKey);
String dpSpec = split[0];
String prefix = conf.getTableInfo().getTableName().toLowerCase();
prefix = Utilities.join(prefix, spSpec, dpSpec);
prefix = prefix.endsWith(Path.SEPARATOR) ? prefix : prefix + Path.SEPARATOR;
if (Utilities.FILE_OP_LOGGER.isTraceEnabled()) {


log.info("prefix for stats from");
}
}

};