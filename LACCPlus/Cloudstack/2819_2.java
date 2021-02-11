//,temp,StorageSystemDataMotionStrategy.java,1436,1567,temp,StorageSystemDataMotionStrategy.java,1302,1434
//,3
public class xxx {
    private void handleCreateVolumeFromTemplateBothOnStorageSystem(TemplateInfo templateInfo, VolumeInfo volumeInfo, AsyncCompletionCallback<CopyCommandResult> callback) {
        String errMsg = null;
        CopyCmdAnswer copyCmdAnswer = null;

        try {
            Preconditions.checkArgument(templateInfo != null, "Passing 'null' to templateInfo of " +
                            "handleCreateVolumeFromTemplateBothOnStorageSystem is not supported.");
            Preconditions.checkArgument(volumeInfo != null, "Passing 'null' to volumeInfo of " +
                            "handleCreateVolumeFromTemplateBothOnStorageSystem is not supported.");

            verifyFormat(templateInfo.getFormat());

            HostVO hostVO = null;

            final boolean computeClusterSupportsVolumeClone;

            // only XenServer, VMware, and KVM are currently supported
            // Leave host equal to null for KVM since we don't need to perform a resignature when using that hypervisor type.
            if (volumeInfo.getFormat() == ImageFormat.VHD) {
                hostVO = getHost(volumeInfo.getDataCenterId(), HypervisorType.XenServer, true);

                if (hostVO == null) {
                    throw new CloudRuntimeException("Unable to locate a host capable of resigning in the zone with the following ID: " +
                            volumeInfo.getDataCenterId());
                }

                computeClusterSupportsVolumeClone = clusterDao.getSupportsResigning(hostVO.getClusterId());

                if (!computeClusterSupportsVolumeClone) {
                    String noSupportForResignErrMsg = "Unable to locate an applicable host with which to perform a resignature operation : Cluster ID = " +
                            hostVO.getClusterId();

                    LOGGER.warn(noSupportForResignErrMsg);

                    throw new CloudRuntimeException(noSupportForResignErrMsg);
                }
            }
            else if (volumeInfo.getFormat() == ImageFormat.OVA) {
                // all VMware hosts support resigning
                hostVO = getHost(volumeInfo.getDataCenterId(), HypervisorType.VMware, false);

                if (hostVO == null) {
                    throw new CloudRuntimeException("Unable to locate a host capable of resigning in the zone with the following ID: " +
                            volumeInfo.getDataCenterId());
                }
            }

            VolumeDetailVO volumeDetail = new VolumeDetailVO(volumeInfo.getId(),
                    "cloneOfTemplate",
                    String.valueOf(templateInfo.getId()),
                    false);

            volumeDetail = volumeDetailsDao.persist(volumeDetail);

            AsyncCallFuture<VolumeApiResult> future = _volumeService.createVolumeAsync(volumeInfo, volumeInfo.getDataStore());

            int storagePoolMaxWaitSeconds = NumbersUtil.parseInt(_configDao.getValue(Config.StoragePoolMaxWaitSeconds.key()), 3600);

            VolumeApiResult result = future.get(storagePoolMaxWaitSeconds, TimeUnit.SECONDS);

            if (volumeDetail != null) {
                volumeDetailsDao.remove(volumeDetail.getId());
            }

            if (result.isFailed()) {
                LOGGER.warn("Failed to create a volume: " + result.getResult());

                throw new CloudRuntimeException(result.getResult());
            }

            volumeInfo = _volumeDataFactory.getVolume(volumeInfo.getId(), volumeInfo.getDataStore());
            volumeInfo.processEvent(Event.MigrationRequested);
            volumeInfo = _volumeDataFactory.getVolume(volumeInfo.getId(), volumeInfo.getDataStore());

            if (hostVO != null) {
                Map<String, String> extraDetails = null;

                if (HypervisorType.VMware.equals(templateInfo.getHypervisorType())) {
                    extraDetails = new HashMap<>();

                    String extraDetailsVmdk = templateInfo.getUniqueName() + ".vmdk";

                    extraDetails.put(DiskTO.VMDK, extraDetailsVmdk);
                    extraDetails.put(DiskTO.EXPAND_DATASTORE, Boolean.TRUE.toString());
                }

                copyCmdAnswer = performResignature(volumeInfo, hostVO, extraDetails);

                verifyCopyCmdAnswer(copyCmdAnswer, templateInfo);

                // If using VMware, have the host rescan its software HBA if dynamic discovery is in use.
                if (HypervisorType.VMware.equals(templateInfo.getHypervisorType())) {
                    disconnectHostFromVolume(hostVO, volumeInfo.getPoolId(), volumeInfo.get_iScsiName());
                }
            }
            else {
                VolumeObjectTO newVolume = new VolumeObjectTO();

                newVolume.setSize(volumeInfo.getSize());
                newVolume.setPath(volumeInfo.getPath());
                newVolume.setFormat(volumeInfo.getFormat());

                copyCmdAnswer = new CopyCmdAnswer(newVolume);
            }
        } catch (Exception ex) {
            try {
                volumeInfo.getDataStore().getDriver().deleteAsync(volumeInfo.getDataStore(), volumeInfo, null);
            }
            catch (Exception exc) {
                LOGGER.warn("Failed to delete volume", exc);
            }

            if (templateInfo != null) {
                errMsg = "Create volume from template (ID = " + templateInfo.getId() + ") failed: " + ex.getMessage();
            }
            else {
                errMsg = "Create volume from template failed: " + ex.getMessage();
            }

            throw new CloudRuntimeException(errMsg);
        }
        finally {
            if (copyCmdAnswer == null) {
                copyCmdAnswer = new CopyCmdAnswer(errMsg);
            }

            CopyCommandResult result = new CopyCommandResult(null, copyCmdAnswer);

            result.setResult(errMsg);

            callback.complete(result);
        }
    }

};