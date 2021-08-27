//,temp,sample_5343.java,2,12,temp,sample_849.java,2,10
//,3
public class xxx {
private PartitionDesc changePartitionToMetadataOnly(PartitionDesc desc, Path path) {
if (desc == null) return null;
boolean isEmpty = false;
try {
isEmpty = Utilities.isEmptyPath(physicalContext.getConf(), path);
} catch (IOException e) {


log.info("cannot determine if the table is empty");
}
}

};