//,temp,sample_2781.java,2,13,temp,sample_2771.java,2,13
//,2
public class xxx {
private void verifyDataNodeVolumeMetrics(final FileSystem fs, final MiniDFSCluster cluster, final Path fileName) throws IOException {
List<DataNode> datanodes = cluster.getDataNodes();
DataNode datanode = datanodes.get(0);
final ExtendedBlock block = DFSTestUtil.getFirstBlock(fs, fileName);
final FsVolumeSpi volume = datanode.getFSDataset().getVolume(block);
DataNodeVolumeMetrics metrics = volume.getMetrics();
MetricsRecordBuilder rb = getMetrics(volume.getMetrics().name());
assertCounter("TotalDataFileIos", metrics.getTotalDataFileIos(), rb);


log.info("writeiomean");
}

};