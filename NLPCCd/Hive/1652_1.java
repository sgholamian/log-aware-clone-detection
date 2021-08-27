//,temp,sample_3387.java,2,15,temp,sample_3386.java,2,11
//,3
public class xxx {
private PartitionDesc extractSinglePartSpec(CombineHiveInputSplit hsplit) throws IOException {
PartitionDesc part = null;
Map<Map<Path,PartitionDesc>, Map<Path,PartitionDesc>> cache = new HashMap<>();
for (Path path : hsplit.getPaths()) {
PartitionDesc otherPart = HiveFileFormatUtils.getFromPathRecursively( pathToPartInfo, path, cache);
if (part == null) {
part = otherPart;
} else if (otherPart != part) {


log.info("multiple partitions found not going to pass a part spec to llap io and");
}
}
}

};