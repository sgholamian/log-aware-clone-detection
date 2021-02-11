//,temp,ExternalLoadBalancerDeviceManagerImpl.java,220,326,temp,ExternalFirewallDeviceManagerImpl.java,188,277
//,3
public class xxx {
    @Override
    @DB
    public ExternalFirewallDeviceVO addExternalFirewall(long physicalNetworkId, String url, String username, String password, final String deviceName,
        ServerResource resource) {
        String guid;
        PhysicalNetworkVO pNetwork = null;
        NetworkDevice ntwkDevice = NetworkDevice.getNetworkDevice(deviceName);
        long zoneId;

        if ((ntwkDevice == null) || (url == null) || (username == null) || (resource == null) || (password == null)) {
            throw new InvalidParameterValueException("Atleast one of the required parameters (url, username, password,"
                + " server resource, zone id/physical network id) is not specified or a valid parameter.");
        }

        pNetwork = _physicalNetworkDao.findById(physicalNetworkId);
        if (pNetwork == null) {
            throw new InvalidParameterValueException("Could not find phyical network with ID: " + physicalNetworkId);
        }
        zoneId = pNetwork.getDataCenterId();

        final PhysicalNetworkServiceProviderVO ntwkSvcProvider =
            _physicalNetworkServiceProviderDao.findByServiceProvider(pNetwork.getId(), ntwkDevice.getNetworkServiceProvder());
        if (ntwkSvcProvider == null) {
            throw new CloudRuntimeException("Network Service Provider: " + ntwkDevice.getNetworkServiceProvder() + " is not enabled in the physical network: " +
                physicalNetworkId + "to add this device");
        } else if (ntwkSvcProvider.getState() == PhysicalNetworkServiceProvider.State.Shutdown) {
            throw new CloudRuntimeException("Network Service Provider: " + ntwkSvcProvider.getProviderName() +
                " is not added or in shutdown state in the physical network: " + physicalNetworkId + "to add this device");
        }

        URI uri;
        try {
            uri = new URI(url);
        } catch (Exception e) {
            s_logger.debug(e);
            throw new InvalidParameterValueException(e.getMessage());
        }

        String ipAddress = uri.getHost();
        Map hostDetails = new HashMap<String, String>();
        guid = getExternalNetworkResourceGuid(pNetwork.getId(), deviceName, ipAddress);
        hostDetails.put("name", guid);
        hostDetails.put("guid", guid);
        hostDetails.put("zoneId", String.valueOf(pNetwork.getDataCenterId()));
        hostDetails.put("ip", ipAddress);
        hostDetails.put("physicalNetworkId", String.valueOf(pNetwork.getId()));
        hostDetails.put("username", username);
        hostDetails.put("password", password);
        hostDetails.put("deviceName", deviceName);
        final Map<String, String> configParams = new HashMap<String, String>();
        UrlUtil.parseQueryParameters(uri.getQuery(), false, configParams);
        hostDetails.putAll(configParams);

        // let the server resource to do parameters validation
        try {
            resource.configure(guid, hostDetails);
        } catch (ConfigurationException e) {
            throw new CloudRuntimeException(e.getMessage());
        }

        final Host externalFirewall = _resourceMgr.addHost(zoneId, resource, Host.Type.ExternalFirewall, hostDetails);
        if (externalFirewall != null) {
            final PhysicalNetworkVO pNetworkFinal = pNetwork;
            return Transaction.execute(new TransactionCallback<ExternalFirewallDeviceVO>() {
                @Override
                public ExternalFirewallDeviceVO doInTransaction(TransactionStatus status) {
                    boolean dedicatedUse =
                        (configParams.get(ApiConstants.FIREWALL_DEVICE_DEDICATED) != null) ? Boolean.parseBoolean(configParams.get(ApiConstants.FIREWALL_DEVICE_DEDICATED))
                            : false;
                    long capacity = NumbersUtil.parseLong(configParams.get(ApiConstants.FIREWALL_DEVICE_CAPACITY), 0);
                    if (capacity == 0) {
                        capacity = _defaultFwCapacity;
                    }

                    ExternalFirewallDeviceVO fwDevice =
                        new ExternalFirewallDeviceVO(externalFirewall.getId(), pNetworkFinal.getId(), ntwkSvcProvider.getProviderName(), deviceName, capacity,
                            dedicatedUse);

                    _externalFirewallDeviceDao.persist(fwDevice);

                    DetailVO hostDetail = new DetailVO(externalFirewall.getId(), ApiConstants.FIREWALL_DEVICE_ID, String.valueOf(fwDevice.getId()));
                    _hostDetailDao.persist(hostDetail);

                    return fwDevice;
                }
            });
        } else {
            return null;
        }
    }

};