//,temp,StatsCollector.java,803,918,temp,UserVmManagerImpl.java,3984,4058
//,3
public class xxx {
                    @Override
                    public void doInTransactionWithoutResult(TransactionStatus status) {
                        HashMap<String, List<VmNetworkStatsEntry>> vmNetworkStatsByName = networkStatsAnswerFinal.getVmNetworkStatsMap();
                        if (vmNetworkStatsByName == null) {
                            return;
                        }
                        List<VmNetworkStatsEntry> vmNetworkStats = vmNetworkStatsByName.get(userVm.getInstanceName());
                        if (vmNetworkStats == null) {
                            return;
                        }

                        for (VmNetworkStatsEntry vmNetworkStat:vmNetworkStats) {
                            SearchCriteria<NicVO> sc_nic = _nicDao.createSearchCriteria();
                            sc_nic.addAnd("macAddress", SearchCriteria.Op.EQ, vmNetworkStat.getMacAddress());
                            NicVO nic = _nicDao.search(sc_nic, null).get(0);
                            List<VlanVO> vlan = _vlanDao.listVlansByNetworkId(nic.getNetworkId());
                            if (vlan == null || vlan.size() == 0 || vlan.get(0).getVlanType() != VlanType.DirectAttached)
                            {
                                break; // only get network statistics for DirectAttached network (shared networks in Basic zone and Advanced zone with/without SG)
                            }
                            UserStatisticsVO previousvmNetworkStats = _userStatsDao.findBy(userVm.getAccountId(), userVm.getDataCenterId(), nic.getNetworkId(), nic.getIPv4Address(), userVm.getId(), "UserVm");
                            if (previousvmNetworkStats == null) {
                                previousvmNetworkStats = new UserStatisticsVO(userVm.getAccountId(), userVm.getDataCenterId(),nic.getIPv4Address(), userVm.getId(), "UserVm", nic.getNetworkId());
                                _userStatsDao.persist(previousvmNetworkStats);
                            }
                            UserStatisticsVO vmNetworkStat_lock = _userStatsDao.lock(userVm.getAccountId(), userVm.getDataCenterId(), nic.getNetworkId(), nic.getIPv4Address(), userVm.getId(), "UserVm");

                            if ((vmNetworkStat.getBytesSent() == 0) && (vmNetworkStat.getBytesReceived() == 0)) {
                                s_logger.debug("bytes sent and received are all 0. Not updating user_statistics");
                                continue;
                            }

                            if (vmNetworkStat_lock == null) {
                                s_logger.warn("unable to find vm network stats from host for account: " + userVm.getAccountId() + " with vmId: " + userVm.getId()+ " and nicId:" + nic.getId());
                                continue;
                            }

                            if (previousvmNetworkStats != null
                                    && ((previousvmNetworkStats.getCurrentBytesSent() != vmNetworkStat_lock.getCurrentBytesSent())
                                            || (previousvmNetworkStats.getCurrentBytesReceived() != vmNetworkStat_lock.getCurrentBytesReceived()))) {
                                s_logger.debug("vm network stats changed from the time GetNmNetworkStatsCommand was sent. " +
                                        "Ignoring current answer. Host: " + host.getName()  + " . VM: " + vmNetworkStat.getVmName() +
                                        " Sent(Bytes): " + vmNetworkStat.getBytesSent() + " Received(Bytes): " + vmNetworkStat.getBytesReceived());
                                continue;
                            }

                            if (vmNetworkStat_lock.getCurrentBytesSent() > vmNetworkStat.getBytesSent()) {
                                if (s_logger.isDebugEnabled()) {
                                    s_logger.debug("Sent # of bytes that's less than the last one.  " +
                                            "Assuming something went wrong and persisting it. Host: " + host.getName() + " . VM: " + vmNetworkStat.getVmName() +
                                            " Reported: " + vmNetworkStat.getBytesSent() + " Stored: " + vmNetworkStat_lock.getCurrentBytesSent());
                                }
                                vmNetworkStat_lock.setNetBytesSent(vmNetworkStat_lock.getNetBytesSent() + vmNetworkStat_lock.getCurrentBytesSent());
                            }
                            vmNetworkStat_lock.setCurrentBytesSent(vmNetworkStat.getBytesSent());

                            if (vmNetworkStat_lock.getCurrentBytesReceived() > vmNetworkStat.getBytesReceived()) {
                                if (s_logger.isDebugEnabled()) {
                                    s_logger.debug("Received # of bytes that's less than the last one.  " +
                                            "Assuming something went wrong and persisting it. Host: " + host.getName() + " . VM: " + vmNetworkStat.getVmName() +
                                            " Reported: " + vmNetworkStat.getBytesReceived() + " Stored: " + vmNetworkStat_lock.getCurrentBytesReceived());
                                }
                                vmNetworkStat_lock.setNetBytesReceived(vmNetworkStat_lock.getNetBytesReceived() + vmNetworkStat_lock.getCurrentBytesReceived());
                            }
                            vmNetworkStat_lock.setCurrentBytesReceived(vmNetworkStat.getBytesReceived());

                            if (! _dailyOrHourly) {
                                //update agg bytes
                                vmNetworkStat_lock.setAggBytesReceived(vmNetworkStat_lock.getNetBytesReceived() + vmNetworkStat_lock.getCurrentBytesReceived());
                                vmNetworkStat_lock.setAggBytesSent(vmNetworkStat_lock.getNetBytesSent() + vmNetworkStat_lock.getCurrentBytesSent());
                            }

                            _userStatsDao.update(vmNetworkStat_lock.getId(), vmNetworkStat_lock);
                        }
                    }

};