//,temp,sample_9523.java,2,17,temp,sample_5832.java,2,17
//,3
public class xxx {
public void dummy_method(){
sc_volume.addAnd("path", SearchCriteria.Op.EQ, vmDiskStat.getPath());
List<VolumeVO> volumes = _volsDao.search(sc_volume, null);
if ((volumes == null) || (volumes.size() == 0)) break;
VolumeVO volume = volumes.get(0);
VmDiskStatisticsVO previousVmDiskStats = _vmDiskStatsDao.findBy(userVm.getAccountId(), userVm.getDataCenterId(), userVm.getId(), volume.getId());
VmDiskStatisticsVO vmDiskStat_lock = _vmDiskStatsDao.lock(userVm.getAccountId(), userVm.getDataCenterId(), userVm.getId(), volume.getId());
if ((vmDiskStat.getIORead() == 0) && (vmDiskStat.getIOWrite() == 0) && (vmDiskStat.getBytesRead() == 0) && (vmDiskStat.getBytesWrite() == 0)) {
continue;
}
if (vmDiskStat_lock == null) {


log.info("unable to find vm disk stats from host for account with vmid and volumeid");
}
}

};