//,temp,sample_3397.java,2,15,temp,sample_3399.java,2,15
//,2
public class xxx {
private long createBlockFile() throws IOException {
long id = getFreeBlockId();
try (FsDatasetSpi.FsVolumeReferences volumes = fds.getFsVolumeReferences()) {
int numVolumes = volumes.size();
int index = rand.nextInt(numVolumes - 1);
File finalizedDir = volumes.get(index).getFinalizedDir(bpid);
File file = new File(finalizedDir, getBlockFile(id));
if (file.createNewFile()) {


log.info("created block file");
}
}
}

};