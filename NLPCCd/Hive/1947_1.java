//,temp,sample_3340.java,2,18,temp,sample_3341.java,2,17
//,3
public class xxx {
public void dummy_method(){
for (int i = 0; i < length; i++) {
PartitionDesc part = HiveFileFormatUtils.getFromPathRecursively( pathToPartitionInfo, paths[i + start], IOPrepareCache.get().allocatePartitionDescMap());
Class<? extends InputFormat> inputFormatClass = part.getInputFileFormatClass();
InputFormat<WritableComparable, Writable> inputFormat = getInputFormatFromCache(inputFormatClass, conf);
boolean isAvoidSplitCombine = inputFormat instanceof AvoidSplitCombination && ((AvoidSplitCombination) inputFormat).shouldSkipCombine(paths[i + start], conf);
TableDesc tbl = part.getTableDesc();
boolean isMmNonMerge = false;
if (tbl != null) {
isMmNonMerge = !isMerge && AcidUtils.isInsertOnlyTable(tbl.getProperties());
} else {


log.info("assuming not insert only no table in partition spec");
}
}
}

};