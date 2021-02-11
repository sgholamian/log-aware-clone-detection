//,temp,InternalLoadBalancerVMManagerImpl.java,172,242,temp,ElasticLoadBalancerManagerImpl.java,410,489
//,3
public class xxx {
    @Override
    public boolean finalizeVirtualMachineProfile(VirtualMachineProfile profile, DeployDestination dest, ReservationContext context) {

        List<NicProfile> elbNics = profile.getNics();
        Long guestNtwkId = null;
        for (NicProfile routerNic : elbNics) {
            if (routerNic.getTrafficType() == TrafficType.Guest) {
                guestNtwkId = routerNic.getNetworkId();
                break;
            }
        }

        NetworkVO guestNetwork = _networkDao.findById(guestNtwkId);

        DataCenter dc = dest.getDataCenter();

        StringBuilder buf = profile.getBootArgsBuilder();
        buf.append(" template=domP type=" + SystemVmType);
        buf.append(" name=").append(profile.getHostName());
        NicProfile controlNic = null;
        String defaultDns1 = null;
        String defaultDns2 = null;

        for (NicProfile nic : profile.getNics()) {
            int deviceId = nic.getDeviceId();
            buf.append(" eth").append(deviceId).append("ip=").append(nic.getIPv4Address());
            buf.append(" eth").append(deviceId).append("mask=").append(nic.getIPv4Netmask());
            if (nic.isDefaultNic()) {
                buf.append(" gateway=").append(nic.getIPv4Gateway());
                defaultDns1 = nic.getIPv4Dns1();
                defaultDns2 = nic.getIPv4Dns2();
            }
            if (nic.getTrafficType() == TrafficType.Management) {
                buf.append(" localgw=").append(dest.getPod().getGateway());
            } else if (nic.getTrafficType() == TrafficType.Control) {
                //  control command is sent over management network in VMware
                if (dest.getHost().getHypervisorType() == HypervisorType.VMware) {
                    if (s_logger.isInfoEnabled()) {
                        s_logger.info("Check if we need to add management server explicit route to ELB vm. pod cidr: " + dest.getPod().getCidrAddress() + "/"
                                + dest.getPod().getCidrSize() + ", pod gateway: " + dest.getPod().getGateway() + ", management host: "
                                + ApiServiceConfiguration.ManagementServerAddresses.value());
                    }

                    if (s_logger.isDebugEnabled()) {
                        s_logger.debug("Added management server explicit route to ELB vm.");
                    }
                    // always add management explicit route, for basic networking setup
                    buf.append(" mgmtcidr=").append(_mgmtCidr);
                    buf.append(" localgw=").append(dest.getPod().getGateway());

                    if (dc.getNetworkType() == NetworkType.Basic) {
                        // ask elb vm to setup SSH on guest network
                        buf.append(" sshonguest=true");
                    }

                }

                controlNic = nic;
            }
        }
        String domain = guestNetwork.getNetworkDomain();
        if (domain != null) {
            buf.append(" domain=" + domain);
        }

        buf.append(" dns1=").append(defaultDns1);
        if (defaultDns2 != null) {
            buf.append(" dns2=").append(defaultDns2);
        }

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Boot Args for " + profile + ": " + buf.toString());
        }

        if (controlNic == null) {
            throw new CloudRuntimeException("Didn't start a control port");
        }

        return true;
    }

};