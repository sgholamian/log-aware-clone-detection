//,temp,StatsCollector.java,688,794,temp,UserVmManagerImpl.java,4641,4762
//,3
public class xxx {
    @Override
    public void collectVmDiskStatistics(final UserVm userVm) {
        // support KVM only util 2013.06.25
        if (!userVm.getHypervisorType().equals(HypervisorType.KVM)) {
            return;
        }
        s_logger.debug("Collect vm disk statistics from host before stopping Vm");
        long hostId = userVm.getHostId();
        List<String> vmNames = new ArrayList<String>();
        vmNames.add(userVm.getInstanceName());
        final HostVO host = _hostDao.findById(hostId);

        GetVmDiskStatsAnswer diskStatsAnswer = null;
        try {
            diskStatsAnswer = (GetVmDiskStatsAnswer)_agentMgr.easySend(hostId, new GetVmDiskStatsCommand(vmNames, host.getGuid(), host.getName()));
        } catch (Exception e) {
            s_logger.warn("Error while collecting disk stats for vm: " + userVm.getInstanceName() + " from host: " + host.getName(), e);
            return;
        }
        if (diskStatsAnswer != null) {
            if (!diskStatsAnswer.getResult()) {
                s_logger.warn("Error while collecting disk stats vm: " + userVm.getInstanceName() + " from host: " + host.getName() + "; details: " + diskStatsAnswer.getDetails());
                return;
            }
            try {
                final GetVmDiskStatsAnswer diskStatsAnswerFinal = diskStatsAnswer;
                Transaction.execute(new TransactionCallbackNoReturn() {
                    @Override
                    public void doInTransactionWithoutResult(TransactionStatus status) {
                        HashMap<String, List<VmDiskStatsEntry>> vmDiskStatsByName = diskStatsAnswerFinal.getVmDiskStatsMap();
                        if (vmDiskStatsByName == null) {
                            return;
                        }
                        List<VmDiskStatsEntry> vmDiskStats = vmDiskStatsByName.get(userVm.getInstanceName());
                        if (vmDiskStats == null) {
                            return;
                        }

                        for (VmDiskStatsEntry vmDiskStat : vmDiskStats) {
                            SearchCriteria<VolumeVO> sc_volume = _volsDao.createSearchCriteria();
                            sc_volume.addAnd("path", SearchCriteria.Op.EQ, vmDiskStat.getPath());
                            List<VolumeVO> volumes = _volsDao.search(sc_volume, null);
                            if ((volumes == null) || (volumes.size() == 0)) {
                                break;
                            }
                            VolumeVO volume = volumes.get(0);
                            VmDiskStatisticsVO previousVmDiskStats = _vmDiskStatsDao.findBy(userVm.getAccountId(), userVm.getDataCenterId(), userVm.getId(), volume.getId());
                            VmDiskStatisticsVO vmDiskStat_lock = _vmDiskStatsDao.lock(userVm.getAccountId(), userVm.getDataCenterId(), userVm.getId(), volume.getId());

                            if ((vmDiskStat.getIORead() == 0) && (vmDiskStat.getIOWrite() == 0) && (vmDiskStat.getBytesRead() == 0) && (vmDiskStat.getBytesWrite() == 0)) {
                                s_logger.debug("Read/Write of IO and Bytes are both 0. Not updating vm_disk_statistics");
                                continue;
                            }

                            if (vmDiskStat_lock == null) {
                                s_logger.warn("unable to find vm disk stats from host for account: " + userVm.getAccountId() + " with vmId: " + userVm.getId() + " and volumeId:"
                                        + volume.getId());
                                continue;
                            }

                            if (previousVmDiskStats != null
                                    && ((previousVmDiskStats.getCurrentIORead() != vmDiskStat_lock.getCurrentIORead()) || ((previousVmDiskStats.getCurrentIOWrite() != vmDiskStat_lock
                                    .getCurrentIOWrite())
                                            || (previousVmDiskStats.getCurrentBytesRead() != vmDiskStat_lock.getCurrentBytesRead()) || (previousVmDiskStats
                                                    .getCurrentBytesWrite() != vmDiskStat_lock.getCurrentBytesWrite())))) {
                                s_logger.debug("vm disk stats changed from the time GetVmDiskStatsCommand was sent. " + "Ignoring current answer. Host: " + host.getName()
                                + " . VM: " + vmDiskStat.getVmName() + " IO Read: " + vmDiskStat.getIORead() + " IO Write: " + vmDiskStat.getIOWrite() + " Bytes Read: "
                                + vmDiskStat.getBytesRead() + " Bytes Write: " + vmDiskStat.getBytesWrite());
                                continue;
                            }

                            if (vmDiskStat_lock.getCurrentIORead() > vmDiskStat.getIORead()) {
                                if (s_logger.isDebugEnabled()) {
                                    s_logger.debug("Read # of IO that's less than the last one.  " + "Assuming something went wrong and persisting it. Host: " + host.getName()
                                    + " . VM: " + vmDiskStat.getVmName() + " Reported: " + vmDiskStat.getIORead() + " Stored: " + vmDiskStat_lock.getCurrentIORead());
                                }
                                vmDiskStat_lock.setNetIORead(vmDiskStat_lock.getNetIORead() + vmDiskStat_lock.getCurrentIORead());
                            }
                            vmDiskStat_lock.setCurrentIORead(vmDiskStat.getIORead());
                            if (vmDiskStat_lock.getCurrentIOWrite() > vmDiskStat.getIOWrite()) {
                                if (s_logger.isDebugEnabled()) {
                                    s_logger.debug("Write # of IO that's less than the last one.  " + "Assuming something went wrong and persisting it. Host: " + host.getName()
                                    + " . VM: " + vmDiskStat.getVmName() + " Reported: " + vmDiskStat.getIOWrite() + " Stored: " + vmDiskStat_lock.getCurrentIOWrite());
                                }
                                vmDiskStat_lock.setNetIOWrite(vmDiskStat_lock.getNetIOWrite() + vmDiskStat_lock.getCurrentIOWrite());
                            }
                            vmDiskStat_lock.setCurrentIOWrite(vmDiskStat.getIOWrite());
                            if (vmDiskStat_lock.getCurrentBytesRead() > vmDiskStat.getBytesRead()) {
                                if (s_logger.isDebugEnabled()) {
                                    s_logger.debug("Read # of Bytes that's less than the last one.  " + "Assuming something went wrong and persisting it. Host: " + host.getName()
                                    + " . VM: " + vmDiskStat.getVmName() + " Reported: " + vmDiskStat.getBytesRead() + " Stored: " + vmDiskStat_lock.getCurrentBytesRead());
                                }
                                vmDiskStat_lock.setNetBytesRead(vmDiskStat_lock.getNetBytesRead() + vmDiskStat_lock.getCurrentBytesRead());
                            }
                            vmDiskStat_lock.setCurrentBytesRead(vmDiskStat.getBytesRead());
                            if (vmDiskStat_lock.getCurrentBytesWrite() > vmDiskStat.getBytesWrite()) {
                                if (s_logger.isDebugEnabled()) {
                                    s_logger.debug("Write # of Bytes that's less than the last one.  " + "Assuming something went wrong and persisting it. Host: " + host.getName()
                                    + " . VM: " + vmDiskStat.getVmName() + " Reported: " + vmDiskStat.getBytesWrite() + " Stored: "
                                    + vmDiskStat_lock.getCurrentBytesWrite());
                                }
                                vmDiskStat_lock.setNetBytesWrite(vmDiskStat_lock.getNetBytesWrite() + vmDiskStat_lock.getCurrentBytesWrite());
                            }
                            vmDiskStat_lock.setCurrentBytesWrite(vmDiskStat.getBytesWrite());

                            if (!_dailyOrHourly) {
                                //update agg bytes
                                vmDiskStat_lock.setAggIORead(vmDiskStat_lock.getNetIORead() + vmDiskStat_lock.getCurrentIORead());
                                vmDiskStat_lock.setAggIOWrite(vmDiskStat_lock.getNetIOWrite() + vmDiskStat_lock.getCurrentIOWrite());
                                vmDiskStat_lock.setAggBytesRead(vmDiskStat_lock.getNetBytesRead() + vmDiskStat_lock.getCurrentBytesRead());
                                vmDiskStat_lock.setAggBytesWrite(vmDiskStat_lock.getNetBytesWrite() + vmDiskStat_lock.getCurrentBytesWrite());
                            }

                            _vmDiskStatsDao.update(vmDiskStat_lock.getId(), vmDiskStat_lock);
                        }
                    }
                });
            } catch (Exception e) {
                s_logger.warn("Unable to update vm disk statistics for vm: " + userVm.getId() + " from host: " + hostId, e);
            }
        }
    }

};