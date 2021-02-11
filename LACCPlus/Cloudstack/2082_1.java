//,temp,BaremetalKickStartServiceImpl.java,286,364,temp,BareMetalPingServiceImpl.java,158,264
//,3
public class xxx {
    @Override
    @DB
    public BaremetalPxeVO addPxeServer(AddBaremetalPxeCmd cmd) {
        AddBaremetalKickStartPxeCmd kcmd = (AddBaremetalKickStartPxeCmd)cmd;
        PhysicalNetworkVO pNetwork = null;
        long zoneId;

        if (cmd.getPhysicalNetworkId() == null || cmd.getUrl() == null || cmd.getUsername() == null || cmd.getPassword() == null) {
            throw new IllegalArgumentException("At least one of the required parameters(physical network id, url, username, password) is null");
        }

        pNetwork = _physicalNetworkDao.findById(cmd.getPhysicalNetworkId());
        if (pNetwork == null) {
            throw new IllegalArgumentException("Could not find phyical network with ID: " + cmd.getPhysicalNetworkId());
        }
        zoneId = pNetwork.getDataCenterId();

        PhysicalNetworkServiceProviderVO ntwkSvcProvider =
                _physicalNetworkServiceProviderDao.findByServiceProvider(pNetwork.getId(), BaremetalPxeManager.BAREMETAL_PXE_SERVICE_PROVIDER.getName());
        if (ntwkSvcProvider == null) {
            throw new CloudRuntimeException("Network Service Provider: " + BaremetalPxeManager.BAREMETAL_PXE_SERVICE_PROVIDER.getName() +
                    " is not enabled in the physical network: " + cmd.getPhysicalNetworkId() + "to add this device");
        } else if (ntwkSvcProvider.getState() == PhysicalNetworkServiceProvider.State.Shutdown) {
            throw new CloudRuntimeException("Network Service Provider: " + ntwkSvcProvider.getProviderName() + " is in shutdown state in the physical network: " +
                    cmd.getPhysicalNetworkId() + "to add this device");
        }

        List<HostVO> pxes = _resourceMgr.listAllHostsInOneZoneByType(Host.Type.BaremetalPxe, zoneId);
        if (!pxes.isEmpty()) {
            throw new IllegalArgumentException("Already had a PXE server zone: " + zoneId);
        }

        String tftpDir = kcmd.getTftpDir();
        if (tftpDir == null) {
            throw new IllegalArgumentException("No TFTP directory specified");
        }

        URI uri;
        try {
            uri = new URI(cmd.getUrl());
        } catch (Exception e) {
            s_logger.debug(e);
            throw new IllegalArgumentException(e.getMessage());
        }
        String ipAddress = uri.getHost();
        if (ipAddress == null) {
            ipAddress = cmd.getUrl();
        }

        String guid = getPxeServerGuid(Long.toString(zoneId), BaremetalPxeType.KICK_START.toString(), ipAddress);

        ServerResource resource = null;
        Map params = new HashMap<String, String>();
        params.put(BaremetalPxeService.PXE_PARAM_ZONE, Long.toString(zoneId));
        params.put(BaremetalPxeService.PXE_PARAM_IP, ipAddress);
        params.put(BaremetalPxeService.PXE_PARAM_USERNAME, cmd.getUsername());
        params.put(BaremetalPxeService.PXE_PARAM_PASSWORD, cmd.getPassword());
        params.put(BaremetalPxeService.PXE_PARAM_TFTP_DIR, tftpDir);
        params.put(BaremetalPxeService.PXE_PARAM_GUID, guid);
        resource = new BaremetalKickStartPxeResource();
        try {
            resource.configure("KickStart PXE resource", params);
        } catch (Exception e) {
            throw new CloudRuntimeException(e.getMessage(), e);
        }

        Host pxeServer = _resourceMgr.addHost(zoneId, resource, Host.Type.BaremetalPxe, params);
        if (pxeServer == null) {
            throw new CloudRuntimeException("Cannot add PXE server as a host");
        }

        BaremetalPxeVO vo = new BaremetalPxeVO();
        vo.setHostId(pxeServer.getId());
        vo.setNetworkServiceProviderId(ntwkSvcProvider.getId());
        vo.setPhysicalNetworkId(kcmd.getPhysicalNetworkId());
        vo.setDeviceType(BaremetalPxeType.KICK_START.toString());
        _pxeDao.persist(vo);
        return vo;
    }

};