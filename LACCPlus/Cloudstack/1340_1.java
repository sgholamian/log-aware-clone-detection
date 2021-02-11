//,temp,InternalLoadBalancerVMManagerImpl.java,172,242,temp,ElasticLoadBalancerManagerImpl.java,410,489
//,3
public class xxx {
    @Override
    public boolean finalizeVirtualMachineProfile(final VirtualMachineProfile profile, final DeployDestination dest, final ReservationContext context) {

        //Internal LB vm starts up with 2 Nics
        //Nic #1 - Guest Nic with IP address that would act as the LB entry point
        //Nic #2 - Control/Management Nic

        final StringBuilder buf = profile.getBootArgsBuilder();
        buf.append(" template=domP");
        buf.append(" name=").append(profile.getHostName());

        if (Boolean.valueOf(_configDao.getValue("system.vm.random.password"))) {
            buf.append(" vmpassword=").append(_configDao.getValue("system.vm.password"));
        }

        NicProfile controlNic = null;
        Network guestNetwork = null;

        for (final NicProfile nic : profile.getNics()) {
            final int deviceId = nic.getDeviceId();
            buf.append(" eth").append(deviceId).append("ip=").append(nic.getIPv4Address());
            buf.append(" eth").append(deviceId).append("mask=").append(nic.getIPv4Netmask());

            if (nic.isDefaultNic()) {
                buf.append(" gateway=").append(nic.getIPv4Gateway());
                buf.append(" dns1=").append(nic.getIPv4Gateway());
            }

            if (nic.getTrafficType() == TrafficType.Guest) {
                guestNetwork = _ntwkModel.getNetwork(nic.getNetworkId());
            } else if (nic.getTrafficType() == TrafficType.Management) {
                buf.append(" localgw=").append(dest.getPod().getGateway());
            } else if (nic.getTrafficType() == TrafficType.Control) {
                controlNic = nic;
                // Internal LB control command is sent over management server in VMware
                if (dest.getHost().getHypervisorType() == HypervisorType.VMware) {
                    if (s_logger.isInfoEnabled()) {
                        s_logger.info("Check if we need to add management server explicit route to Internal LB. pod cidr: " + dest.getPod().getCidrAddress() + "/" +
                                dest.getPod().getCidrSize() + ", pod gateway: " + dest.getPod().getGateway() + ", management host: " + _mgmtHost);
                    }

                    if (s_logger.isInfoEnabled()) {
                        s_logger.info("Add management server explicit route to Internal LB.");
                    }

                    buf.append(" mgmtcidr=").append(_mgmtCidr);
                    buf.append(" localgw=").append(dest.getPod().getGateway());
                }
            }
        }

        if (controlNic == null) {
            throw new CloudRuntimeException("Didn't start a control port");
        }

        if (guestNetwork != null) {
            final String domain = guestNetwork.getNetworkDomain();
            if (domain != null) {
                buf.append(" domain=" + domain);
            }
        }

        final String type = "ilbvm";
        buf.append(" type=" + type);

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Boot Args for " + profile + ": " + buf.toString());
        }

        return true;
    }

};