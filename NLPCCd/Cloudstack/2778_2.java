//,temp,sample_462.java,2,16,temp,sample_793.java,2,16
//,3
public class xxx {
protected List<StoragePool> select(DiskProfile dskCh, VirtualMachineProfile vmProfile, DeploymentPlan plan, ExcludeList avoid, int returnUpTo) {
if (dskCh.useLocalStorage()) {
return null;
}
if (s_logger.isTraceEnabled()) {
List<StoragePoolVO> disabledPools = _storagePoolDao.findDisabledPoolsByScope(plan.getDataCenterId(), null, null, ScopeType.ZONE);
if (disabledPools != null && !disabledPools.isEmpty()) {
for (StoragePoolVO pool : disabledPools) {


log.info("ignoring pool as it is in disabled state");
}
}
}
}

};