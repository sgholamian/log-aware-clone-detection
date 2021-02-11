//,temp,VirtualMachineManagerImpl.java,2685,2819,temp,VirtualMachineManagerImpl.java,2274,2437
//,3
public class xxx {
    private void orchestrateMigrateWithStorage(final String vmUuid, final long srcHostId, final long destHostId, final Map<Long, Long> volumeToPool) throws ResourceUnavailableException,
    ConcurrentOperationException {

        final VMInstanceVO vm = _vmDao.findByUuid(vmUuid);

        final HostVO srcHost = _hostDao.findById(srcHostId);
        final HostVO destHost = _hostDao.findById(destHostId);
        final VirtualMachineGuru vmGuru = getVmGuru(vm);

        final DataCenterVO dc = _dcDao.findById(destHost.getDataCenterId());
        final HostPodVO pod = _podDao.findById(destHost.getPodId());
        final Cluster cluster = _clusterDao.findById(destHost.getClusterId());
        final DeployDestination destination = new DeployDestination(dc, pod, cluster, destHost);

        // Create a map of which volume should go in which storage pool.
        final VirtualMachineProfile profile = new VirtualMachineProfileImpl(vm);
        final Map<Volume, StoragePool> volumeToPoolMap = createMappingVolumeAndStoragePool(profile, destHost, volumeToPool);

        // If none of the volumes have to be migrated, fail the call. Administrator needs to make a call for migrating
        // a vm and not migrating a vm with storage.
        if (volumeToPoolMap == null || volumeToPoolMap.isEmpty()) {
            throw new InvalidParameterValueException("Migration of the vm " + vm + "from host " + srcHost + " to destination host " + destHost +
                    " doesn't involve migrating the volumes.");
        }

        AlertManager.AlertType alertType = AlertManager.AlertType.ALERT_TYPE_USERVM_MIGRATE;
        if (VirtualMachine.Type.DomainRouter.equals(vm.getType())) {
            alertType = AlertManager.AlertType.ALERT_TYPE_DOMAIN_ROUTER_MIGRATE;
        } else if (VirtualMachine.Type.ConsoleProxy.equals(vm.getType())) {
            alertType = AlertManager.AlertType.ALERT_TYPE_CONSOLE_PROXY_MIGRATE;
        }

        _networkMgr.prepareNicForMigration(profile, destination);
        volumeMgr.prepareForMigration(profile, destination);
        final HypervisorGuru hvGuru = _hvGuruMgr.getGuru(vm.getHypervisorType());
        final VirtualMachineTO to = hvGuru.implement(profile);

        ItWorkVO work = new ItWorkVO(UUID.randomUUID().toString(), _nodeId, State.Migrating, vm.getType(), vm.getId());
        work.setStep(Step.Prepare);
        work.setResourceType(ItWorkVO.ResourceType.Host);
        work.setResourceId(destHostId);
        work = _workDao.persist(work);


        // Put the vm in migrating state.
        vm.setLastHostId(srcHostId);
        vm.setPodIdToDeployIn(destHost.getPodId());
        moveVmToMigratingState(vm, destHostId, work);

        boolean migrated = false;
        try {

            // config drive: Detach the config drive at source host
            // After migration successful attach the config drive in destination host
            // On migration failure VM will be stopped, So configIso will be deleted

            Nic defaultNic = _networkModel.getDefaultNic(vm.getId());

            List<String[]> vmData = null;
            if (defaultNic != null) {
                UserVmVO userVm = _userVmDao.findById(vm.getId());
                Map<String, String> details = userVmDetailsDao.listDetailsKeyPairs(vm.getId());
                userVm.setDetails(details);

                Network network = _networkModel.getNetwork(defaultNic.getNetworkId());
                if (_networkModel.isSharedNetworkWithoutServices(network.getId())) {
                    final String serviceOffering = _serviceOfferingDao.findByIdIncludingRemoved(vm.getId(), vm.getServiceOfferingId()).getDisplayText();
                    boolean isWindows = _guestOSCategoryDao.findById(_guestOSDao.findById(vm.getGuestOSId()).getCategoryId()).getName().equalsIgnoreCase("Windows");

                    vmData = _networkModel.generateVmData(userVm.getUserData(), serviceOffering, vm.getDataCenterId(), vm.getInstanceName(), vm.getHostName(), vm.getId(),
                            vm.getUuid(), defaultNic.getMacAddress(), userVm.getDetail("SSH.PublicKey"), (String) profile.getParameter(VirtualMachineProfile.Param.VmPassword), isWindows);
                    String vmName = vm.getInstanceName();
                    String configDriveIsoRootFolder = "/tmp";
                    String isoFile = configDriveIsoRootFolder + "/" + vmName + "/configDrive/" + vmName + ".iso";
                    profile.setVmData(vmData);
                    profile.setConfigDriveLabel(VmConfigDriveLabel.value());
                    profile.setConfigDriveIsoRootFolder(configDriveIsoRootFolder);
                    profile.setConfigDriveIsoFile(isoFile);

                    // At source host detach the config drive iso.
                    AttachOrDettachConfigDriveCommand dettachCommand = new AttachOrDettachConfigDriveCommand(vm.getInstanceName(), vmData, VmConfigDriveLabel.value(), false);
                    try {
                        _agentMgr.send(srcHost.getId(), dettachCommand);
                        s_logger.debug("Deleted config drive ISO for  vm " + vm.getInstanceName() + " In host " + srcHost);
                    } catch (OperationTimedoutException e) {
                        s_logger.debug("TIme out occured while exeuting command AttachOrDettachConfigDrive " + e.getMessage());

                    }

                }
            }

            // Migrate the vm and its volume.
            volumeMgr.migrateVolumes(vm, to, srcHost, destHost, volumeToPoolMap);

            // Put the vm back to running state.
            moveVmOutofMigratingStateOnSuccess(vm, destHost.getId(), work);

            try {
                if (!checkVmOnHost(vm, destHostId)) {
                    s_logger.error("Vm not found on destination host. Unable to complete migration for " + vm);
                    try {
                        _agentMgr.send(srcHostId, new Commands(cleanup(vm.getInstanceName())), null);
                    } catch (final AgentUnavailableException e) {
                        s_logger.error("AgentUnavailableException while cleanup on source host: " + srcHostId);
                    }
                    cleanup(vmGuru, new VirtualMachineProfileImpl(vm), work, Event.AgentReportStopped, true);
                    throw new CloudRuntimeException("VM not found on desintation host. Unable to complete migration for " + vm);
                }
            } catch (final OperationTimedoutException e) {
                s_logger.warn("Error while checking the vm " + vm + " is on host " + destHost, e);
            }

            migrated = true;
        } finally {
            if (!migrated) {
                s_logger.info("Migration was unsuccessful.  Cleaning up: " + vm);
                _alertMgr.sendAlert(alertType, srcHost.getDataCenterId(), srcHost.getPodId(),
                        "Unable to migrate vm " + vm.getInstanceName() + " from host " + srcHost.getName() + " in zone " + dc.getName() + " and pod " + dc.getName(),
                        "Migrate Command failed.  Please check logs.");
                try {
                    _agentMgr.send(destHostId, new Commands(cleanup(vm.getInstanceName())), null);
                    vm.setPodIdToDeployIn(srcHost.getPodId());
                    stateTransitTo(vm, Event.OperationFailed, srcHostId);
                } catch (final AgentUnavailableException e) {
                    s_logger.warn("Looks like the destination Host is unavailable for cleanup.", e);
                } catch (final NoTransitionException e) {
                    s_logger.error("Error while transitioning vm from migrating to running state.", e);
                }
            }

            work.setStep(Step.Done);
            _workDao.update(work.getId(), work);
        }
    }

};