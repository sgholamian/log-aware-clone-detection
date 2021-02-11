//,temp,sample_7554.java,2,17,temp,sample_7533.java,2,17
//,2
public class xxx {
public void dummy_method(){
List<Pair<VolumeTO, StorageFilerTO>> volumeToFilerto = new ArrayList<Pair<VolumeTO, StorageFilerTO>>();
for (Map.Entry<VolumeInfo, DataStore> entry : volumeToPool.entrySet()) {
VolumeInfo volume = entry.getKey();
VolumeTO volumeTo = new VolumeTO(volume, storagePoolDao.findById(volume.getPoolId()));
StorageFilerTO filerTo = new StorageFilerTO((StoragePool)entry.getValue());
volumeToFilerto.add(new Pair<VolumeTO, StorageFilerTO>(volumeTo, filerTo));
}
MigrateWithStorageCommand command = new MigrateWithStorageCommand(to, volumeToFilerto, destHost.getGuid());
MigrateWithStorageAnswer answer = (MigrateWithStorageAnswer)agentMgr.send(srcHost.getId(), command);
if (answer == null) {


log.info("migration with storage of vm failed");
}
}

};