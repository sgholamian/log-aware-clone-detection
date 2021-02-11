//,temp,VirtualNetworkApplianceManagerImpl.java,1310,1497,temp,SecondaryStorageManagerImpl.java,1114,1213
//,3
public class xxx {
    @Override
    public boolean finalizeVirtualMachineProfile(final VirtualMachineProfile profile, final DeployDestination dest, final ReservationContext context) {

        boolean dnsProvided = true;
        boolean dhcpProvided = true;
        boolean publicNetwork = false;
        final DataCenterVO dc = _dcDao.findById(dest.getDataCenter().getId());
        _dcDao.loadDetails(dc);

        // 1) Set router details
        final DomainRouterVO router = _routerDao.findById(profile.getVirtualMachine().getId());
        final Map<String, String> details = _vmDetailsDao.listDetailsKeyPairs(router.getId());
        router.setDetails(details);

        // 2) Prepare boot loader elements related with Control network

        final StringBuilder buf = profile.getBootArgsBuilder();
        buf.append(" template=domP");
        buf.append(" name=").append(profile.getHostName());

        if (Boolean.valueOf(_configDao.getValue("system.vm.random.password"))) {
            buf.append(" vmpassword=").append(_configDao.getValue("system.vm.password"));
        }

        NicProfile controlNic = null;
        String defaultDns1 = null;
        String defaultDns2 = null;
        String defaultIp6Dns1 = null;
        String defaultIp6Dns2 = null;
        for (final NicProfile nic : profile.getNics()) {
            final int deviceId = nic.getDeviceId();
            boolean ipv4 = false, ipv6 = false;
            if (nic.getIPv4Address() != null) {
                ipv4 = true;
                buf.append(" eth").append(deviceId).append("ip=").append(nic.getIPv4Address());
                buf.append(" eth").append(deviceId).append("mask=").append(nic.getIPv4Netmask());
            }
            if (nic.getIPv6Address() != null) {
                ipv6 = true;
                buf.append(" eth").append(deviceId).append("ip6=").append(nic.getIPv6Address());
                buf.append(" eth").append(deviceId).append("ip6prelen=").append(NetUtils.getIp6CidrSize(nic.getIPv6Cidr()));
            }

            if (nic.isDefaultNic()) {
                if (ipv4) {
                    buf.append(" gateway=").append(nic.getIPv4Gateway());
                }
                if (ipv6) {
                    buf.append(" ip6gateway=").append(nic.getIPv6Gateway());
                }
                defaultDns1 = nic.getIPv4Dns1();
                defaultDns2 = nic.getIPv4Dns2();
                defaultIp6Dns1 = nic.getIPv6Dns1();
                defaultIp6Dns2 = nic.getIPv6Dns2();
            }

            if (nic.getTrafficType() == TrafficType.Management) {
                buf.append(" localgw=").append(dest.getPod().getGateway());
            } else if (nic.getTrafficType() == TrafficType.Control) {
                controlNic = nic;
                buf.append(createRedundantRouterArgs(controlNic, router));

                // DOMR control command is sent over management server in VMware
                if (dest.getHost().getHypervisorType() == HypervisorType.VMware || dest.getHost().getHypervisorType() == HypervisorType.Hyperv) {
                    s_logger.info("Check if we need to add management server explicit route to DomR. pod cidr: " + dest.getPod().getCidrAddress() + "/"
                            + dest.getPod().getCidrSize() + ", pod gateway: " + dest.getPod().getGateway() + ", management host: "
                            + ApiServiceConfiguration.ManagementServerAddresses.value());

                    if (s_logger.isInfoEnabled()) {
                        s_logger.info("Add management server explicit route to DomR.");
                    }

                    // always add management explicit route, for basic
                    // networking setup, DomR may have two interfaces while both
                    // are on the same subnet
                    _mgmtCidr = _configDao.getValue(Config.ManagementNetwork.key());
                    if (NetUtils.isValidIp4Cidr(_mgmtCidr)) {
                        buf.append(" mgmtcidr=").append(_mgmtCidr);
                        buf.append(" localgw=").append(dest.getPod().getGateway());
                    }

                    if (dc.getNetworkType() == NetworkType.Basic) {
                        // ask domR to setup SSH on guest network
                        buf.append(" sshonguest=true");
                    }

                }
            } else if (nic.getTrafficType() == TrafficType.Guest) {
                dnsProvided = _networkModel.isProviderSupportServiceInNetwork(nic.getNetworkId(), Service.Dns, Provider.VirtualRouter);
                dhcpProvided = _networkModel.isProviderSupportServiceInNetwork(nic.getNetworkId(), Service.Dhcp, Provider.VirtualRouter);
                // build bootloader parameter for the guest
                buf.append(createGuestBootLoadArgs(nic, defaultDns1, defaultDns2, router));
            } else if (nic.getTrafficType() == TrafficType.Public) {
                publicNetwork = true;
            }
        }

        if (controlNic == null) {
            throw new CloudRuntimeException("Didn't start a control port");
        }

        final String rpValue = _configDao.getValue(Config.NetworkRouterRpFilter.key());
        if (rpValue != null && rpValue.equalsIgnoreCase("true")) {
            _disableRpFilter = true;
        } else {
            _disableRpFilter = false;
        }

        String rpFilter = " ";
        String type = null;
        if (router.getVpcId() != null) {
            type = "vpcrouter";
            if (_disableRpFilter) {
                rpFilter = " disable_rp_filter=true";
            }
        } else if (!publicNetwork) {
            type = "dhcpsrvr";
        } else {
            type = "router";
            if (_disableRpFilter) {
                rpFilter = " disable_rp_filter=true";
            }
        }

        if (_disableRpFilter) {
            rpFilter = " disable_rp_filter=true";
        }

        buf.append(" type=" + type + rpFilter);

        final String domain_suffix = dc.getDetail(ZoneConfig.DnsSearchOrder.getName());
        if (domain_suffix != null) {
            buf.append(" dnssearchorder=").append(domain_suffix);
        }

        if (profile.getHypervisorType() == HypervisorType.VMware || profile.getHypervisorType() == HypervisorType.Hyperv) {
            buf.append(" extra_pubnics=" + _routerExtraPublicNics);
        }

        /*
         * If virtual router didn't provide DNS service but provide DHCP
         * service, we need to override the DHCP response to return DNS server
         * rather than virtual router itself.
         */
        if (dnsProvided || dhcpProvided) {
            if (defaultDns1 != null) {
                buf.append(" dns1=").append(defaultDns1);
            }
            if (defaultDns2 != null) {
                buf.append(" dns2=").append(defaultDns2);
            }
            if (defaultIp6Dns1 != null) {
                buf.append(" ip6dns1=").append(defaultIp6Dns1);
            }
            if (defaultIp6Dns2 != null) {
                buf.append(" ip6dns2=").append(defaultIp6Dns2);
            }

            boolean useExtDns = !dnsProvided;
            /* For backward compatibility */
            useExtDns = useExtDns || UseExternalDnsServers.valueIn(dc.getId());

            if (useExtDns) {
                buf.append(" useextdns=true");
            }
        }

        if (Boolean.valueOf(_configDao.getValue(Config.BaremetalProvisionDoneNotificationEnabled.key()))) {
            final QueryBuilder<UserVO> acntq = QueryBuilder.create(UserVO.class);
            acntq.and(acntq.entity().getUsername(), SearchCriteria.Op.EQ, "baremetal-system-account");
            final UserVO user = acntq.find();
            if (user == null) {
                s_logger.warn(String
                        .format("global setting[baremetal.provision.done.notification] is enabled but user baremetal-system-account is not found. Baremetal provision done notification will not be enabled"));
            } else {
                buf.append(String.format(" baremetalnotificationsecuritykey=%s", user.getSecretKey()));
                buf.append(String.format(" baremetalnotificationapikey=%s", user.getApiKey()));
                buf.append(" host=").append(ApiServiceConfiguration.ManagementServerAddresses.value());
                buf.append(" port=").append(_configDao.getValue(Config.BaremetalProvisionDoneNotificationPort.key()));
            }
        }

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Boot Args for " + profile + ": " + buf.toString());
        }

        return true;
    }

};