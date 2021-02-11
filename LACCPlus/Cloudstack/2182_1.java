//,temp,StatsCollector.java,688,794,temp,UserVmManagerImpl.java,4668,4756
//,3
public class xxx {
                    @Override
                    public void doInTransactionWithoutResult(TransactionStatus status) {
                        s_logger.debug("VmDiskStatsTask is running...");

                        SearchCriteria<HostVO> sc = createSearchCriteriaForHostTypeRoutingStateUpAndNotInMaintenance();
                        sc.addAnd("hypervisorType", SearchCriteria.Op.EQ, HypervisorType.KVM); // support KVM only util 2013.06.25
                        List<HostVO> hosts = _hostDao.search(sc, null);

                        for (HostVO host : hosts) {
                            List<UserVmVO> vms = _userVmDao.listRunningByHostId(host.getId());
                            List<Long> vmIds = new ArrayList<Long>();

                            for (UserVmVO vm : vms) {
                                if (vm.getType() == VirtualMachine.Type.User) // user vm
                                    vmIds.add(vm.getId());
                            }

                            HashMap<Long, List<VmDiskStatsEntry>> vmDiskStatsById = _userVmMgr.getVmDiskStatistics(host.getId(), host.getName(), vmIds);
                            if (vmDiskStatsById == null)
                                continue;

                            Set<Long> vmIdSet = vmDiskStatsById.keySet();
                            for (Long vmId : vmIdSet) {
                                List<VmDiskStatsEntry> vmDiskStats = vmDiskStatsById.get(vmId);
                                if (vmDiskStats == null)
                                    continue;
                                UserVmVO userVm = _userVmDao.findById(vmId);
                                for (VmDiskStatsEntry vmDiskStat : vmDiskStats) {
                                    SearchCriteria<VolumeVO> sc_volume = _volsDao.createSearchCriteria();
                                    sc_volume.addAnd("path", SearchCriteria.Op.EQ, vmDiskStat.getPath());
                                    List<VolumeVO> volumes = _volsDao.search(sc_volume, null);

                                    if (CollectionUtils.isEmpty(volumes))
                                        break;

                                    VolumeVO volume = volumes.get(0);
                                    VmDiskStatisticsVO previousVmDiskStats = _vmDiskStatsDao.findBy(userVm.getAccountId(), userVm.getDataCenterId(), vmId, volume.getId());
                                    VmDiskStatisticsVO vmDiskStat_lock = _vmDiskStatsDao.lock(userVm.getAccountId(), userVm.getDataCenterId(), vmId, volume.getId());

                                    if (areAllDiskStatsZero(vmDiskStat)) {
                                        s_logger.debug("IO/bytes read and write are all 0. Not updating vm_disk_statistics");
                                        continue;
                                    }

                                    if (vmDiskStat_lock == null) {
                                        s_logger.warn("unable to find vm disk stats from host for account: " + userVm.getAccountId() + " with vmId: " + userVm.getId()
                                                + " and volumeId:" + volume.getId());
                                        continue;
                                    }

                                    if (isCurrentVmDiskStatsDifferentFromPrevious(previousVmDiskStats, vmDiskStat_lock)) {
                                        s_logger.debug("vm disk stats changed from the time GetVmDiskStatsCommand was sent. " + "Ignoring current answer. Host: " + host.getName()
                                                + " . VM: " + vmDiskStat.getVmName() + " Read(Bytes): " + vmDiskStat.getBytesRead() + " write(Bytes): " + vmDiskStat.getBytesWrite()
                                                + " Read(IO): " + vmDiskStat.getIORead() + " write(IO): " + vmDiskStat.getIOWrite());
                                        continue;
                                    }

                                    if (vmDiskStat_lock.getCurrentBytesRead() > vmDiskStat.getBytesRead()) {
                                        if (s_logger.isDebugEnabled()) {
                                            s_logger.debug("Read # of bytes that's less than the last one.  " + "Assuming something went wrong and persisting it. Host: "
                                                    + host.getName() + " . VM: " + vmDiskStat.getVmName() + " Reported: " + vmDiskStat.getBytesRead() + " Stored: "
                                                    + vmDiskStat_lock.getCurrentBytesRead());
                                        }
                                        vmDiskStat_lock.setNetBytesRead(vmDiskStat_lock.getNetBytesRead() + vmDiskStat_lock.getCurrentBytesRead());
                                    }
                                    vmDiskStat_lock.setCurrentBytesRead(vmDiskStat.getBytesRead());
                                    if (vmDiskStat_lock.getCurrentBytesWrite() > vmDiskStat.getBytesWrite()) {
                                        if (s_logger.isDebugEnabled()) {
                                            s_logger.debug("Write # of bytes that's less than the last one.  " + "Assuming something went wrong and persisting it. Host: "
                                                    + host.getName() + " . VM: " + vmDiskStat.getVmName() + " Reported: " + vmDiskStat.getBytesWrite() + " Stored: "
                                                    + vmDiskStat_lock.getCurrentBytesWrite());
                                        }
                                        vmDiskStat_lock.setNetBytesWrite(vmDiskStat_lock.getNetBytesWrite() + vmDiskStat_lock.getCurrentBytesWrite());
                                    }
                                    vmDiskStat_lock.setCurrentBytesWrite(vmDiskStat.getBytesWrite());
                                    if (vmDiskStat_lock.getCurrentIORead() > vmDiskStat.getIORead()) {
                                        if (s_logger.isDebugEnabled()) {
                                            s_logger.debug("Read # of IO that's less than the last one.  " + "Assuming something went wrong and persisting it. Host: "
                                                    + host.getName() + " . VM: " + vmDiskStat.getVmName() + " Reported: " + vmDiskStat.getIORead() + " Stored: "
                                                    + vmDiskStat_lock.getCurrentIORead());
                                        }
                                        vmDiskStat_lock.setNetIORead(vmDiskStat_lock.getNetIORead() + vmDiskStat_lock.getCurrentIORead());
                                    }
                                    vmDiskStat_lock.setCurrentIORead(vmDiskStat.getIORead());
                                    if (vmDiskStat_lock.getCurrentIOWrite() > vmDiskStat.getIOWrite()) {
                                        if (s_logger.isDebugEnabled()) {
                                            s_logger.debug("Write # of IO that's less than the last one.  " + "Assuming something went wrong and persisting it. Host: "
                                                    + host.getName() + " . VM: " + vmDiskStat.getVmName() + " Reported: " + vmDiskStat.getIOWrite() + " Stored: "
                                                    + vmDiskStat_lock.getCurrentIOWrite());
                                        }
                                        vmDiskStat_lock.setNetIOWrite(vmDiskStat_lock.getNetIOWrite() + vmDiskStat_lock.getCurrentIOWrite());
                                    }
                                    vmDiskStat_lock.setCurrentIOWrite(vmDiskStat.getIOWrite());

                                    if (!_dailyOrHourly) {
                                        //update agg bytes
                                        vmDiskStat_lock.setAggBytesWrite(vmDiskStat_lock.getNetBytesWrite() + vmDiskStat_lock.getCurrentBytesWrite());
                                        vmDiskStat_lock.setAggBytesRead(vmDiskStat_lock.getNetBytesRead() + vmDiskStat_lock.getCurrentBytesRead());
                                        vmDiskStat_lock.setAggIOWrite(vmDiskStat_lock.getNetIOWrite() + vmDiskStat_lock.getCurrentIOWrite());
                                        vmDiskStat_lock.setAggIORead(vmDiskStat_lock.getNetIORead() + vmDiskStat_lock.getCurrentIORead());
                                    }

                                    _vmDiskStatsDao.update(vmDiskStat_lock.getId(), vmDiskStat_lock);
                                }
                            }
                        }
                    }

};