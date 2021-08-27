//,temp,sample_3393.java,2,16,temp,sample_4736.java,2,13
//,3
public class xxx {
public void dummy_method(){
Snapshots snapshots;
if (ObjectHelper.isNotEmpty(type)) {
if (type == DigitalOceanSnapshotTypes.droplet) {
snapshots = getEndpoint().getDigitalOceanClient().getAllDropletSnapshots(configuration.getPage(), configuration.getPerPage());
} else {
snapshots = getEndpoint().getDigitalOceanClient().getAllVolumeSnapshots(configuration.getPage(), configuration.getPerPage());
}
} else {
snapshots = getEndpoint().getDigitalOceanClient().getAvailableSnapshots(configuration.getPage(), configuration.getPerPage());
}


log.info("all snapshots page per page");
}

};