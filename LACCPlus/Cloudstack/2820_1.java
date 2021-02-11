//,temp,BareMetalPingServiceImpl.java,158,264,temp,BaremetalDhcpManagerImpl.java,193,279
//,3
public class xxx {
    @Override
    @DB
    public BaremetalPxeVO addPxeServer(AddBaremetalPxeCmd cmd) {
        AddBaremetalPxePingServerCmd pcmd = (AddBaremetalPxePingServerCmd)cmd;

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

        HostPodVO pod = _podDao.findById(cmd.getPodId());
        if (pod == null) {
            throw new IllegalArgumentException("Could not find pod with ID: " + cmd.getPodId());
        }

        List<HostVO> pxes = _resourceMgr.listAllUpAndEnabledHosts(Host.Type.BaremetalPxe, null, cmd.getPodId(), zoneId);
        if (pxes.size() != 0) {
            throw new IllegalArgumentException("Already had a PXE server in Pod: " + cmd.getPodId() + " zone: " + zoneId);
        }

        String storageServerIp = pcmd.getPingStorageServerIp();
        if (storageServerIp == null) {
            throw new IllegalArgumentException("No IP for storage server specified");
        }
        String pingDir = pcmd.getPingDir();
        if (pingDir == null) {
            throw new IllegalArgumentException("No direcotry for storage server specified");
        }
        String tftpDir = pcmd.getTftpDir();
        if (tftpDir == null) {
            throw new IllegalArgumentException("No TFTP directory specified");
        }

        String cifsUsername = pcmd.getPingStorageServerUserName();
        if (cifsUsername == null || cifsUsername.equalsIgnoreCase("")) {
            cifsUsername = "xxx";
        }
        String cifsPassword = pcmd.getPingStorageServerPassword();
        if (cifsPassword == null || cifsPassword.equalsIgnoreCase("")) {
            cifsPassword = "xxx";
        }

        URI uri;
        try {
            uri = new URI(cmd.getUrl());
        } catch (Exception e) {
            s_logger.debug(e);
            throw new IllegalArgumentException(e.getMessage());
        }
        String ipAddress = uri.getHost();

        String guid = getPxeServerGuid(Long.toString(zoneId) + "-" + pod.getId(), BaremetalPxeType.PING.toString(), ipAddress);

        ServerResource resource = null;
        Map params = new HashMap<String, String>();
        params.put(BaremetalPxeService.PXE_PARAM_ZONE, Long.toString(zoneId));
        params.put(BaremetalPxeService.PXE_PARAM_POD, String.valueOf(pod.getId()));
        params.put(BaremetalPxeService.PXE_PARAM_IP, ipAddress);
        params.put(BaremetalPxeService.PXE_PARAM_USERNAME, cmd.getUsername());
        params.put(BaremetalPxeService.PXE_PARAM_PASSWORD, cmd.getPassword());
        params.put(BaremetalPxeService.PXE_PARAM_PING_STORAGE_SERVER_IP, storageServerIp);
        params.put(BaremetalPxeService.PXE_PARAM_PING_ROOT_DIR, pingDir);
        params.put(BaremetalPxeService.PXE_PARAM_TFTP_DIR, tftpDir);
        params.put(BaremetalPxeService.PXE_PARAM_PING_STORAGE_SERVER_USERNAME, cifsUsername);
        params.put(BaremetalPxeService.PXE_PARAM_PING_STORAGE_SERVER_PASSWORD, cifsPassword);
        params.put(BaremetalPxeService.PXE_PARAM_GUID, guid);

        resource = new BaremetalPingPxeResource();
        try {
            resource.configure("PING PXE resource", params);
        } catch (Exception e) {
            s_logger.debug(e);
            throw new CloudRuntimeException(e.getMessage());
        }

        Host pxeServer = _resourceMgr.addHost(zoneId, resource, Host.Type.BaremetalPxe, params);
        if (pxeServer == null) {
            throw new CloudRuntimeException("Cannot add PXE server as a host");
        }

        BaremetalPxeVO vo = new BaremetalPxeVO();
        vo.setHostId(pxeServer.getId());
        vo.setNetworkServiceProviderId(ntwkSvcProvider.getId());
        vo.setPodId(pod.getId());
        vo.setPhysicalNetworkId(pcmd.getPhysicalNetworkId());
        vo.setDeviceType(BaremetalPxeType.PING.toString());
        _pxeDao.persist(vo);
        return vo;
    }

};