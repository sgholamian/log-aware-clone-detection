//,temp,sample_3387.java,2,15,temp,sample_3386.java,2,11
//,3
public class xxx {
private PartitionDesc extractSinglePartSpec(CombineHiveInputSplit hsplit) throws IOException {
PartitionDesc part = null;
Map<Map<Path,PartitionDesc>, Map<Path,PartitionDesc>> cache = new HashMap<>();
for (Path path : hsplit.getPaths()) {
PartitionDesc otherPart = HiveFileFormatUtils.getFromPathRecursively( pathToPartInfo, path, cache);


log.info("found spec for from");
}
}

};