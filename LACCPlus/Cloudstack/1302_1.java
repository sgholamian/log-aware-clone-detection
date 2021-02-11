//,temp,IpAddressManagerImpl.java,2074,2122,temp,IpAddressManagerImpl.java,2022,2072
//,3
public class xxx {
    @Override
    @DB
    public void allocateNicValues(final NicProfile nic, final DataCenter dc, final VirtualMachineProfile vm, final Network network, final String requestedIpv4,
            final String requestedIpv6) throws InsufficientVirtualNetworkCapacityException, InsufficientAddressCapacityException {
        Transaction.execute(new TransactionCallbackWithExceptionNoReturn<InsufficientAddressCapacityException>() {
            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) throws InsufficientAddressCapacityException {
                //This method allocates direct ip for the Shared network in Advance zones
                boolean ipv4 = false;

                if (network.getGateway() != null) {
                    if (nic.getIPv4Address() == null) {
                        ipv4 = true;
                        // PublicIp ip = null;

                        //Get ip address from the placeholder and don't allocate a new one
                        if (requestedIpv4 != null && vm.getType() == VirtualMachine.Type.DomainRouter) {
                            s_logger.debug("There won't be nic assignment for VR id " + vm.getId() + "  in this network " + network);

                        }

                        // nic ip address is not set here. Because the DHCP is external to cloudstack
                        nic.setIPv4Gateway(network.getGateway());
                        nic.setIPv4Netmask(NetUtils.getCidrNetmask(network.getCidr()));

                        List<VlanVO> vlan = _vlanDao.listVlansByNetworkId(network.getId());

                        //TODO: get vlan tag for the ntwork
                        if (vlan != null && !vlan.isEmpty()) {
                            nic.setIsolationUri(IsolationType.Vlan.toUri(vlan.get(0).getVlanTag()));
                        }

                        nic.setBroadcastType(BroadcastDomainType.Vlan);
                        nic.setBroadcastType(network.getBroadcastDomainType());

                        nic.setBroadcastUri(network.getBroadcastUri());
                        nic.setFormat(AddressFormat.Ip4);
                        if(nic.getMacAddress() == null) {
                            nic.setMacAddress(_networkModel.getNextAvailableMacAddressInNetwork(network.getId()));
                        }
                    }
                    nic.setIPv4Dns1(dc.getDns1());
                    nic.setIPv4Dns2(dc.getDns2());
                }

                _ipv6Mgr.setNicIp6Address(nic, dc, network);
            }
        });
    }

};