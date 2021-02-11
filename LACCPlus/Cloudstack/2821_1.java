//,temp,ExternalLoadBalancerDeviceManagerImpl.java,220,326,temp,ExternalFirewallDeviceManagerImpl.java,188,277
//,3
public class xxx {
    @Override
    @DB
    public ExternalLoadBalancerDeviceVO addExternalLoadBalancer(long physicalNetworkId, String url, String username, String password, final String deviceName,
        ServerResource resource, final boolean gslbProvider, final boolean exclusiveGslbProivider,
        final String gslbSitePublicIp, final String gslbSitePrivateIp) {

        PhysicalNetworkVO pNetwork = null;
        final NetworkDevice ntwkDevice = NetworkDevice.getNetworkDevice(deviceName);
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
        PhysicalNetworkServiceProviderVO ntwkSvcProvider =
            _physicalNetworkServiceProviderDao.findByServiceProvider(pNetwork.getId(), ntwkDevice.getNetworkServiceProvder());

        ntwkSvcProvider = _physicalNetworkServiceProviderDao.findByServiceProvider(pNetwork.getId(), ntwkDevice.getNetworkServiceProvder());
        if (ntwkSvcProvider == null) {
            throw new CloudRuntimeException("Network Service Provider: " + ntwkDevice.getNetworkServiceProvder() + " is not enabled in the physical network: " +
                physicalNetworkId + "to add this device");
        } else if (ntwkSvcProvider.getState() == PhysicalNetworkServiceProvider.State.Shutdown) {
            throw new CloudRuntimeException("Network Service Provider: " + ntwkSvcProvider.getProviderName() + " is in shutdown state in the physical network: " +
                physicalNetworkId + "to add this device");
        }

        if (gslbProvider) {
            ExternalLoadBalancerDeviceVO zoneGslbProvider =
                _externalLoadBalancerDeviceDao.findGslbServiceProvider(physicalNetworkId, ntwkDevice.getNetworkServiceProvder());
            if (zoneGslbProvider != null) {
                throw new CloudRuntimeException("There is a GSLB service provider configured in the zone alredy.");
            }
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
        String hostName = getExternalLoadBalancerResourceGuid(pNetwork.getId(), deviceName, ipAddress);
        hostDetails.put("name", hostName);
        hostDetails.put("guid", UUID.randomUUID().toString());
        hostDetails.put("zoneId", String.valueOf(pNetwork.getDataCenterId()));
        hostDetails.put("ip", ipAddress);
        hostDetails.put("physicalNetworkId", String.valueOf(pNetwork.getId()));
        hostDetails.put("username", username);
        hostDetails.put("password", password);
        hostDetails.put("deviceName", deviceName);

        // leave parameter validation to be part server resource configure
        Map<String, String> configParams = new HashMap<String, String>();
        UrlUtil.parseQueryParameters(uri.getQuery(), false, configParams);
        hostDetails.putAll(configParams);

        try {
            resource.configure(hostName, hostDetails);

            final Host host = _resourceMgr.addHost(zoneId, resource, Host.Type.ExternalLoadBalancer, hostDetails);
            if (host != null) {

                final boolean dedicatedUse =
                    (configParams.get(ApiConstants.LOAD_BALANCER_DEVICE_DEDICATED) != null) ? Boolean.parseBoolean(configParams.get(ApiConstants.LOAD_BALANCER_DEVICE_DEDICATED))
                        : false;
                long capacity = NumbersUtil.parseLong(configParams.get(ApiConstants.LOAD_BALANCER_DEVICE_CAPACITY), 0);
                if (capacity == 0) {
                    capacity = _defaultLbCapacity;
                }

                final long capacityFinal = capacity;
                final PhysicalNetworkVO pNetworkFinal = pNetwork;
                return Transaction.execute(new TransactionCallback<ExternalLoadBalancerDeviceVO>() {
                    @Override
                    public ExternalLoadBalancerDeviceVO doInTransaction(TransactionStatus status) {
                        ExternalLoadBalancerDeviceVO lbDeviceVO =
                            new ExternalLoadBalancerDeviceVO(host.getId(), pNetworkFinal.getId(), ntwkDevice.getNetworkServiceProvder(), deviceName, capacityFinal,
                                dedicatedUse, gslbProvider);
                        if (gslbProvider) {
                            lbDeviceVO.setGslbSitePublicIP(gslbSitePublicIp);
                            lbDeviceVO.setGslbSitePrivateIP(gslbSitePrivateIp);
                            lbDeviceVO.setExclusiveGslbProvider(exclusiveGslbProivider);
                        }
                        _externalLoadBalancerDeviceDao.persist(lbDeviceVO);
                        DetailVO hostDetail = new DetailVO(host.getId(), ApiConstants.LOAD_BALANCER_DEVICE_ID, String.valueOf(lbDeviceVO.getId()));
                        _hostDetailDao.persist(hostDetail);

                        return lbDeviceVO;
                    }
                });
            } else {
                throw new CloudRuntimeException("Failed to add load balancer device due to internal error.");
            }
        } catch (ConfigurationException e) {
            throw new CloudRuntimeException(e.getMessage());
        }
    }

};