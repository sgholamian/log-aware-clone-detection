//,temp,VirtualMachineManagerImpl.java,3804,3966,temp,VirtualMachineManagerImpl.java,2274,2437
//,3
public class xxx {
    protected void migrate(final VMInstanceVO vm, final long srcHostId, final DeployDestination dest) throws ResourceUnavailableException, ConcurrentOperationException {
        s_logger.info("Migrating " + vm + " to " + dest);

        final long dstHostId = dest.getHost().getId();
        final Host fromHost = _hostDao.findById(srcHostId);
        if (fromHost == null) {
            s_logger.info("Unable to find the host to migrate from: " + srcHostId);
            throw new CloudRuntimeException("Unable to find the host to migrate from: " + srcHostId);
        }

        if (fromHost.getClusterId().longValue() != dest.getCluster().getId()) {
            final List<VolumeVO> volumes = _volsDao.findCreatedByInstance(vm.getId());
            for (final VolumeVO volume : volumes) {
                if (!_storagePoolDao.findById(volume.getPoolId()).getScope().equals(ScopeType.ZONE)) {
                    s_logger.info("Source and destination host are not in same cluster and all volumes are not on zone wide primary store, unable to migrate to host: "
                            + dest.getHost().getId());
                    throw new CloudRuntimeException(
                            "Source and destination host are not in same cluster and all volumes are not on zone wide primary store, unable to migrate to host: "
                                    + dest.getHost().getId());
                }
            }
        }

        final VirtualMachineGuru vmGuru = getVmGuru(vm);

        if (vm.getState() != State.Running) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("VM is not Running, unable to migrate the vm " + vm);
            }
            throw new CloudRuntimeException("VM is not Running, unable to migrate the vm currently " + vm + " , current state: " + vm.getState().toString());
        }

        AlertManager.AlertType alertType = AlertManager.AlertType.ALERT_TYPE_USERVM_MIGRATE;
        if (VirtualMachine.Type.DomainRouter.equals(vm.getType())) {
            alertType = AlertManager.AlertType.ALERT_TYPE_DOMAIN_ROUTER_MIGRATE;
        } else if (VirtualMachine.Type.ConsoleProxy.equals(vm.getType())) {
            alertType = AlertManager.AlertType.ALERT_TYPE_CONSOLE_PROXY_MIGRATE;
        }

        final VirtualMachineProfile vmSrc = new VirtualMachineProfileImpl(vm);
        for (final NicProfile nic : _networkMgr.getNicProfiles(vm)) {
            vmSrc.addNic(nic);
        }

        final VirtualMachineProfile profile = new VirtualMachineProfileImpl(vm, null, _offeringDao.findById(vm.getId(), vm.getServiceOfferingId()), null, null);
        _networkMgr.prepareNicForMigration(profile, dest);
        volumeMgr.prepareForMigration(profile, dest);
        profile.setConfigDriveLabel(VmConfigDriveLabel.value());

        final VirtualMachineTO to = toVmTO(profile);
        final PrepareForMigrationCommand pfmc = new PrepareForMigrationCommand(to);

        ItWorkVO work = new ItWorkVO(UUID.randomUUID().toString(), _nodeId, State.Migrating, vm.getType(), vm.getId());
        work.setStep(Step.Prepare);
        work.setResourceType(ItWorkVO.ResourceType.Host);
        work.setResourceId(dstHostId);
        work = _workDao.persist(work);

        Answer pfma = null;
        try {
            pfma = _agentMgr.send(dstHostId, pfmc);
            if (pfma == null || !pfma.getResult()) {
                final String details = pfma != null ? pfma.getDetails() : "null answer returned";
                final String msg = "Unable to prepare for migration due to " + details;
                pfma = null;
                throw new AgentUnavailableException(msg, dstHostId);
            }
        } catch (final OperationTimedoutException e1) {
            throw new AgentUnavailableException("Operation timed out", dstHostId);
        } finally {
            if (pfma == null) {
                _networkMgr.rollbackNicForMigration(vmSrc, profile);
                work.setStep(Step.Done);
                _workDao.update(work.getId(), work);
            }
        }

        vm.setLastHostId(srcHostId);
        try {
            if (vm == null || vm.getHostId() == null || vm.getHostId() != srcHostId || !changeState(vm, Event.MigrationRequested, dstHostId, work, Step.Migrating)) {
                _networkMgr.rollbackNicForMigration(vmSrc, profile);
                s_logger.info("Migration cancelled because state has changed: " + vm);
                throw new ConcurrentOperationException("Migration cancelled because state has changed: " + vm);
            }
        } catch (final NoTransitionException e1) {
            _networkMgr.rollbackNicForMigration(vmSrc, profile);
            s_logger.info("Migration cancelled because " + e1.getMessage());
            throw new ConcurrentOperationException("Migration cancelled because " + e1.getMessage());
        }

        boolean migrated = false;
        try {
            final boolean isWindows = _guestOsCategoryDao.findById(_guestOsDao.findById(vm.getGuestOSId()).getCategoryId()).getName().equalsIgnoreCase("Windows");
            final MigrateCommand mc = new MigrateCommand(vm.getInstanceName(), dest.getHost().getPrivateIpAddress(), isWindows, to, getExecuteInSequence(vm.getHypervisorType()));

            boolean kvmAutoConvergence = StorageManager.KvmAutoConvergence.value();
            mc.setAutoConvergence(kvmAutoConvergence);
            mc.setHostGuid(dest.getHost().getGuid());

            try {
                final Answer ma = _agentMgr.send(vm.getLastHostId(), mc);
                if (ma == null || !ma.getResult()) {
                    final String details = ma != null ? ma.getDetails() : "null answer returned";
                    throw new CloudRuntimeException(details);
                }
            } catch (final OperationTimedoutException e) {
                if (e.isActive()) {
                    s_logger.warn("Active migration command so scheduling a restart for " + vm);
                    _haMgr.scheduleRestart(vm, true);
                }
                throw new AgentUnavailableException("Operation timed out on migrating " + vm, dstHostId);
            }

            try {
                if (!changeState(vm, VirtualMachine.Event.OperationSucceeded, dstHostId, work, Step.Started)) {
                    throw new ConcurrentOperationException("Unable to change the state for " + vm);
                }
            } catch (final NoTransitionException e1) {
                throw new ConcurrentOperationException("Unable to change state due to " + e1.getMessage());
            }

            try {
                if (!checkVmOnHost(vm, dstHostId)) {
                    s_logger.error("Unable to complete migration for " + vm);
                    try {
                        _agentMgr.send(srcHostId, new Commands(cleanup(vm)), null);
                    } catch (final AgentUnavailableException e) {
                        s_logger.error("AgentUnavailableException while cleanup on source host: " + srcHostId);
                    }
                    cleanup(vmGuru, new VirtualMachineProfileImpl(vm), work, Event.AgentReportStopped, true);
                    throw new CloudRuntimeException("Unable to complete migration for " + vm);
                }
            } catch (final OperationTimedoutException e) {
                s_logger.debug("Error while checking the vm " + vm + " on host " + dstHostId, e);
            }

            migrated = true;
        } finally {
            if (!migrated) {
                s_logger.info("Migration was unsuccessful.  Cleaning up: " + vm);
                _networkMgr.rollbackNicForMigration(vmSrc, profile);

                _alertMgr.sendAlert(alertType, fromHost.getDataCenterId(), fromHost.getPodId(),
                        "Unable to migrate vm " + vm.getInstanceName() + " from host " + fromHost.getName() + " in zone " + dest.getDataCenter().getName() + " and pod " +
                                dest.getPod().getName(), "Migrate Command failed.  Please check logs.");
                try {
                    _agentMgr.send(dstHostId, new Commands(cleanup(vm)), null);
                } catch (final AgentUnavailableException ae) {
                    s_logger.info("Looks like the destination Host is unavailable for cleanup");
                }

                try {
                    stateTransitTo(vm, Event.OperationFailed, srcHostId);
                } catch (final NoTransitionException e) {
                    s_logger.warn(e.getMessage());
                }
            } else {
                _networkMgr.commitNicForMigration(vmSrc, profile);
            }

            work.setStep(Step.Done);
            _workDao.update(work.getId(), work);
        }
    }

};