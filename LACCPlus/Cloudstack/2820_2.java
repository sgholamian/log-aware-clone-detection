//,temp,BareMetalPingServiceImpl.java,158,264,temp,BaremetalDhcpManagerImpl.java,193,279
//,3
public class xxx {
    @Override
    @DB
    public BaremetalDhcpVO addDchpServer(AddBaremetalDhcpCmd cmd) {
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
        DataCenterVO zone = _dcDao.findById(zoneId);

        PhysicalNetworkServiceProviderVO ntwkSvcProvider =
            _physicalNetworkServiceProviderDao.findByServiceProvider(pNetwork.getId(), BaremetalDhcpManager.BAREMETAL_DHCP_SERVICE_PROVIDER.getName());
        if (ntwkSvcProvider == null) {
            throw new CloudRuntimeException("Network Service Provider: " + BaremetalDhcpManager.BAREMETAL_DHCP_SERVICE_PROVIDER.getName() +
                " is not enabled in the physical network: " + cmd.getPhysicalNetworkId() + "to add this device");
        } else if (ntwkSvcProvider.getState() == PhysicalNetworkServiceProvider.State.Shutdown) {
            throw new CloudRuntimeException("Network Service Provider: " + ntwkSvcProvider.getProviderName() + " is in shutdown state in the physical network: " +
                cmd.getPhysicalNetworkId() + "to add this device");
        }

        List<HostVO> dhcps = _resourceMgr.listAllUpAndEnabledHosts(Host.Type.BaremetalDhcp, null, null, zoneId);
        if (dhcps.size() != 0) {
            throw new IllegalArgumentException("Already had a DHCP server in zone: " + zoneId);
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
            ipAddress = cmd.getUrl(); // the url is raw ip. For backforward compatibility, we have to support http://ip format as well
        }
        String guid = getDhcpServerGuid(Long.toString(zoneId), "ExternalDhcp", ipAddress);
        Map params = new HashMap<String, String>();
        params.put("type", cmd.getDhcpType());
        params.put("zone", Long.toString(zoneId));
        params.put("ip", ipAddress);
        params.put("username", cmd.getUsername());
        params.put("password", cmd.getPassword());
        params.put("guid", guid);
        String dns = zone.getDns1();
        if (dns == null) {
            dns = zone.getDns2();
        }
        params.put("dns", dns);

        ServerResource resource = null;
        try {
            if (cmd.getDhcpType().equalsIgnoreCase(BaremetalDhcpType.DNSMASQ.toString())) {
                resource = new BaremetalDnsmasqResource();
                resource.configure("Dnsmasq resource", params);
            } else if (cmd.getDhcpType().equalsIgnoreCase(BaremetalDhcpType.DHCPD.toString())) {
                resource = new BaremetalDhcpdResource();
                resource.configure("Dhcpd resource", params);
            } else {
                throw new CloudRuntimeException("Unsupport DHCP server type: " + cmd.getDhcpType());
            }
        } catch (Exception e) {
            s_logger.debug(e);
            throw new CloudRuntimeException(e.getMessage());
        }

        Host dhcpServer = _resourceMgr.addHost(zoneId, resource, Host.Type.BaremetalDhcp, params);
        if (dhcpServer == null) {
            throw new CloudRuntimeException("Cannot add external Dhcp server as a host");
        }

        BaremetalDhcpVO vo = new BaremetalDhcpVO();
        vo.setDeviceType(cmd.getDhcpType());
        vo.setHostId(dhcpServer.getId());
        vo.setNetworkServiceProviderId(ntwkSvcProvider.getId());
        vo.setPhysicalNetworkId(cmd.getPhysicalNetworkId());
        _extDhcpDao.persist(vo);
        return vo;
    }

};