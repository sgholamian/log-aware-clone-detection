//,temp,VirtualNetworkApplianceManagerImpl.java,1310,1497,temp,SecondaryStorageManagerImpl.java,1114,1213
//,3
public class xxx {
    @Override
    public boolean finalizeVirtualMachineProfile(VirtualMachineProfile profile, DeployDestination dest, ReservationContext context) {
        SecondaryStorageVmVO vm = _secStorageVmDao.findById(profile.getId());
        Map<String, String> details = _vmDetailsDao.listDetailsKeyPairs(vm.getId());
        vm.setDetails(details);

        DataStore secStore = _dataStoreMgr.getImageStore(dest.getDataCenter().getId());
        assert (secStore != null);

        StringBuilder buf = profile.getBootArgsBuilder();
        buf.append(" template=domP type=secstorage");
        buf.append(" host=").append(StringUtils.toCSVList(indirectAgentLB.getManagementServerList(dest.getHost().getId(), dest.getDataCenter().getId(), null)));
        buf.append(" port=").append(_mgmtPort);
        buf.append(" name=").append(profile.getVirtualMachine().getHostName());

        buf.append(" zone=").append(dest.getDataCenter().getId());
        buf.append(" pod=").append(dest.getPod().getId());

        buf.append(" guid=").append(profile.getVirtualMachine().getHostName());

        buf.append(" workers=").append(_configDao.getValue("workers"));

        if (_configDao.isPremium()) {
            s_logger.debug("VmWare hypervisor configured, telling the ssvm to load the PremiumSecondaryStorageResource");
            buf.append(" resource=com.cloud.storage.resource.PremiumSecondaryStorageResource");
        } else {
            buf.append(" resource=org.apache.cloudstack.storage.resource.NfsSecondaryStorageResource");
        }
        buf.append(" instance=SecStorage");
        buf.append(" sslcopy=").append(Boolean.toString(_useSSlCopy));
        buf.append(" role=").append(vm.getRole().toString());
        buf.append(" mtu=").append(_secStorageVmMtuSize);

        boolean externalDhcp = false;
        String externalDhcpStr = _configDao.getValue("direct.attach.network.externalIpAllocator.enabled");
        if (externalDhcpStr != null && externalDhcpStr.equalsIgnoreCase("true")) {
            externalDhcp = true;
        }

        if (Boolean.valueOf(_configDao.getValue("system.vm.random.password"))) {
            buf.append(" vmpassword=").append(_configDao.getValue("system.vm.password"));
        }

        if (NTPServerConfig.value() != null) {
            buf.append(" ntpserverlist=").append(NTPServerConfig.value().replaceAll("\\s+",""));
        }

        for (NicProfile nic : profile.getNics()) {
            int deviceId = nic.getDeviceId();
            if (nic.getIPv4Address() == null) {
                buf.append(" eth").append(deviceId).append("mask=").append("0.0.0.0");
                buf.append(" eth").append(deviceId).append("ip=").append("0.0.0.0");
            } else {
                buf.append(" eth").append(deviceId).append("ip=").append(nic.getIPv4Address());
                buf.append(" eth").append(deviceId).append("mask=").append(nic.getIPv4Netmask());
            }

            if (nic.isDefaultNic()) {
                buf.append(" gateway=").append(nic.getIPv4Gateway());
            }
            if (nic.getTrafficType() == TrafficType.Management) {
                String mgmt_cidr = _configDao.getValue(Config.ManagementNetwork.key());
                if (NetUtils.isValidIp4Cidr(mgmt_cidr)) {
                    buf.append(" mgmtcidr=").append(mgmt_cidr);
                }
                buf.append(" localgw=").append(dest.getPod().getGateway());
                buf.append(" private.network.device=").append("eth").append(deviceId);
            } else if (nic.getTrafficType() == TrafficType.Public) {
                buf.append(" public.network.device=").append("eth").append(deviceId);
            } else if (nic.getTrafficType() == TrafficType.Storage) {
                buf.append(" storageip=").append(nic.getIPv4Address());
                buf.append(" storagenetmask=").append(nic.getIPv4Netmask());
                buf.append(" storagegateway=").append(nic.getIPv4Gateway());
            }
        }

        /* External DHCP mode */
        if (externalDhcp) {
            buf.append(" bootproto=dhcp");
        }

        DataCenterVO dc = _dcDao.findById(profile.getVirtualMachine().getDataCenterId());
        buf.append(" internaldns1=").append(dc.getInternalDns1());
        if (dc.getInternalDns2() != null) {
            buf.append(" internaldns2=").append(dc.getInternalDns2());
        }
        buf.append(" dns1=").append(dc.getDns1());
        if (dc.getDns2() != null) {
            buf.append(" dns2=").append(dc.getDns2());
        }
        Integer nfsVersion = imageStoreDetailsUtil != null ? imageStoreDetailsUtil.getNfsVersion(secStore.getId()) : null;
        buf.append(" nfsVersion=").append(nfsVersion);

        String bootArgs = buf.toString();
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Boot Args for " + profile + ": " + bootArgs);
        }

        return true;
    }

};