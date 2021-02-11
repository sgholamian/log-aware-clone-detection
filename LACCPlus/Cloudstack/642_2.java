//,temp,CapacityManagerImpl.java,263,355,temp,CapacityManagerImpl.java,166,261
//,3
public class xxx {
    @DB
    @Override
    public boolean releaseVmCapacity(VirtualMachine vm, final boolean moveFromReserved, final boolean moveToReservered, final Long hostId) {
        if (hostId == null) {
            return true;
        }

        final ServiceOfferingVO svo = _offeringsDao.findById(vm.getId(), vm.getServiceOfferingId());
        CapacityVO capacityCpu = _capacityDao.findByHostIdType(hostId, Capacity.CAPACITY_TYPE_CPU);
        CapacityVO capacityMemory = _capacityDao.findByHostIdType(hostId, Capacity.CAPACITY_TYPE_MEMORY);
        Long clusterId = null;
        if (hostId != null) {
            HostVO host = _hostDao.findById(hostId);
            if (host == null) {
                s_logger.warn("Host " + hostId + " no long exist anymore!");
                return true;
            }

            clusterId = host.getClusterId();
        }
        if (capacityCpu == null || capacityMemory == null || svo == null) {
            return false;
        }

        try {
            final Long clusterIdFinal = clusterId;
            final long capacityCpuId = capacityCpu.getId();
            final long capacityMemoryId = capacityMemory.getId();
            Transaction.execute(new TransactionCallbackNoReturn() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    CapacityVO capacityCpu = _capacityDao.lockRow(capacityCpuId, true);
                    CapacityVO capacityMemory = _capacityDao.lockRow(capacityMemoryId, true);

                    long usedCpu = capacityCpu.getUsedCapacity();
                    long usedMem = capacityMemory.getUsedCapacity();
                    long reservedCpu = capacityCpu.getReservedCapacity();
                    long reservedMem = capacityMemory.getReservedCapacity();
                    long actualTotalCpu = capacityCpu.getTotalCapacity();
                    float cpuOvercommitRatio = Float.parseFloat(_clusterDetailsDao.findDetail(clusterIdFinal, "cpuOvercommitRatio").getValue());
                    float memoryOvercommitRatio = Float.parseFloat(_clusterDetailsDao.findDetail(clusterIdFinal, "memoryOvercommitRatio").getValue());
                    int vmCPU = svo.getCpu() * svo.getSpeed();
                    long vmMem = svo.getRamSize() * 1024L * 1024L;
                    long actualTotalMem = capacityMemory.getTotalCapacity();
                    long totalMem = (long)(actualTotalMem * memoryOvercommitRatio);
                    long totalCpu = (long)(actualTotalCpu * cpuOvercommitRatio);
                    if (s_logger.isDebugEnabled()) {
                        s_logger.debug("Hosts's actual total CPU: " + actualTotalCpu + " and CPU after applying overprovisioning: " + totalCpu);
                        s_logger.debug("Hosts's actual total RAM: " + actualTotalMem + " and RAM after applying overprovisioning: " + totalMem);
                    }

                    if (!moveFromReserved) {
                        /* move resource from used */
                        if (usedCpu >= vmCPU) {
                            capacityCpu.setUsedCapacity(usedCpu - vmCPU);
                        }
                        if (usedMem >= vmMem) {
                            capacityMemory.setUsedCapacity(usedMem - vmMem);
                        }

                        if (moveToReservered) {
                            if (reservedCpu + vmCPU <= totalCpu) {
                                capacityCpu.setReservedCapacity(reservedCpu + vmCPU);
                            }
                            if (reservedMem + vmMem <= totalMem) {
                                capacityMemory.setReservedCapacity(reservedMem + vmMem);
                            }
                        }
                    } else {
                        if (reservedCpu >= vmCPU) {
                            capacityCpu.setReservedCapacity(reservedCpu - vmCPU);
                        }
                        if (reservedMem >= vmMem) {
                            capacityMemory.setReservedCapacity(reservedMem - vmMem);
                        }
                    }

                    s_logger.debug("release cpu from host: " + hostId + ", old used: " + usedCpu + ",reserved: " + reservedCpu + ", actual total: " + actualTotalCpu +
                        ", total with overprovisioning: " + totalCpu + "; new used: " + capacityCpu.getUsedCapacity() + ",reserved:" + capacityCpu.getReservedCapacity() +
                        "; movedfromreserved: " + moveFromReserved + ",moveToReservered" + moveToReservered);

                    s_logger.debug("release mem from host: " + hostId + ", old used: " + usedMem + ",reserved: " + reservedMem + ", total: " + totalMem + "; new used: " +
                        capacityMemory.getUsedCapacity() + ",reserved:" + capacityMemory.getReservedCapacity() + "; movedfromreserved: " + moveFromReserved +
                        ",moveToReservered" + moveToReservered);

                    _capacityDao.update(capacityCpu.getId(), capacityCpu);
                    _capacityDao.update(capacityMemory.getId(), capacityMemory);
                }
            });

            return true;
        } catch (Exception e) {
            s_logger.debug("Failed to transit vm's state, due to " + e.getMessage());
            return false;
        }
    }

};