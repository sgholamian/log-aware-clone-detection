//,temp,CapacityManagerImpl.java,263,355,temp,CapacityManagerImpl.java,166,261
//,3
public class xxx {
    @DB
    @Override
    public void allocateVmCapacity(VirtualMachine vm, final boolean fromLastHost) {

        final long hostId = vm.getHostId();
        HostVO host = _hostDao.findById(hostId);
        final long clusterId = host.getClusterId();
        final float cpuOvercommitRatio = Float.parseFloat(_clusterDetailsDao.findDetail(clusterId, "cpuOvercommitRatio").getValue());
        final float memoryOvercommitRatio = Float.parseFloat(_clusterDetailsDao.findDetail(clusterId, "memoryOvercommitRatio").getValue());

        final ServiceOfferingVO svo = _offeringsDao.findById(vm.getId(), vm.getServiceOfferingId());

        CapacityVO capacityCpu = _capacityDao.findByHostIdType(hostId, Capacity.CAPACITY_TYPE_CPU);
        CapacityVO capacityMem = _capacityDao.findByHostIdType(hostId, Capacity.CAPACITY_TYPE_MEMORY);

        if (capacityCpu == null || capacityMem == null || svo == null) {
            return;
        }

        final int cpu = svo.getCpu() * svo.getSpeed();
        final long ram = svo.getRamSize() * 1024L * 1024L;

        try {
            final long capacityCpuId = capacityCpu.getId();
            final long capacityMemId = capacityMem.getId();

            Transaction.execute(new TransactionCallbackNoReturn() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    CapacityVO capacityCpu = _capacityDao.lockRow(capacityCpuId, true);
                    CapacityVO capacityMem = _capacityDao.lockRow(capacityMemId, true);

                    long usedCpu = capacityCpu.getUsedCapacity();
                    long usedMem = capacityMem.getUsedCapacity();
                    long reservedCpu = capacityCpu.getReservedCapacity();
                    long reservedMem = capacityMem.getReservedCapacity();
                    long actualTotalCpu = capacityCpu.getTotalCapacity();
                    long actualTotalMem = capacityMem.getTotalCapacity();
                    long totalCpu = (long)(actualTotalCpu * cpuOvercommitRatio);
                    long totalMem = (long)(actualTotalMem * memoryOvercommitRatio);
                    if (s_logger.isDebugEnabled()) {
                        s_logger.debug("Hosts's actual total CPU: " + actualTotalCpu + " and CPU after applying overprovisioning: " + totalCpu);
                    }

                    long freeCpu = totalCpu - (reservedCpu + usedCpu);
                    long freeMem = totalMem - (reservedMem + usedMem);

                    if (s_logger.isDebugEnabled()) {
                        s_logger.debug("We are allocating VM, increasing the used capacity of this host:" + hostId);
                        s_logger.debug("Current Used CPU: " + usedCpu + " , Free CPU:" + freeCpu + " ,Requested CPU: " + cpu);
                        s_logger.debug("Current Used RAM: " + usedMem + " , Free RAM:" + freeMem + " ,Requested RAM: " + ram);
                    }
                    capacityCpu.setUsedCapacity(usedCpu + cpu);
                    capacityMem.setUsedCapacity(usedMem + ram);

                    if (fromLastHost) {
                        /* alloc from reserved */
                        if (s_logger.isDebugEnabled()) {
                            s_logger.debug("We are allocating VM to the last host again, so adjusting the reserved capacity if it is not less than required");
                            s_logger.debug("Reserved CPU: " + reservedCpu + " , Requested CPU: " + cpu);
                            s_logger.debug("Reserved RAM: " + reservedMem + " , Requested RAM: " + ram);
                        }
                        if (reservedCpu >= cpu && reservedMem >= ram) {
                            capacityCpu.setReservedCapacity(reservedCpu - cpu);
                            capacityMem.setReservedCapacity(reservedMem - ram);
                        }
                    } else {
                        /* alloc from free resource */
                        if (!((reservedCpu + usedCpu + cpu <= totalCpu) && (reservedMem + usedMem + ram <= totalMem))) {
                            if (s_logger.isDebugEnabled()) {
                                s_logger.debug("Host doesnt seem to have enough free capacity, but increasing the used capacity anyways, " +
                                    "since the VM is already starting on this host ");
                            }
                        }
                    }

                    s_logger.debug("CPU STATS after allocation: for host: " + hostId + ", old used: " + usedCpu + ", old reserved: " + reservedCpu + ", actual total: " +
                        actualTotalCpu + ", total with overprovisioning: " + totalCpu + "; new used:" + capacityCpu.getUsedCapacity() + ", reserved:" +
                        capacityCpu.getReservedCapacity() + "; requested cpu:" + cpu + ",alloc_from_last:" + fromLastHost);

                    s_logger.debug("RAM STATS after allocation: for host: " + hostId + ", old used: " + usedMem + ", old reserved: " + reservedMem + ", total: " +
                        totalMem + "; new used: " + capacityMem.getUsedCapacity() + ", reserved: " + capacityMem.getReservedCapacity() + "; requested mem: " + ram +
                        ",alloc_from_last:" + fromLastHost);

                    _capacityDao.update(capacityCpu.getId(), capacityCpu);
                    _capacityDao.update(capacityMem.getId(), capacityMem);
                }
            });
        } catch (Exception e) {
            s_logger.error("Exception allocating VM capacity", e);
            return;
        }
    }

};