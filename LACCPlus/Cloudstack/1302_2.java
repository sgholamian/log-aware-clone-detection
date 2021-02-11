//,temp,IpAddressManagerImpl.java,2074,2122,temp,IpAddressManagerImpl.java,2022,2072
//,3
public class xxx {
    @Override
    @DB
    public void allocateDirectIp(final NicProfile nic, final DataCenter dc, final VirtualMachineProfile vm, final Network network, final String requestedIpv4,
            final String requestedIpv6) throws InsufficientVirtualNetworkCapacityException, InsufficientAddressCapacityException {
        Transaction.execute(new TransactionCallbackWithExceptionNoReturn<InsufficientAddressCapacityException>() {
            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) throws InsufficientAddressCapacityException {
                //This method allocates direct ip for the Shared network in Advance zones
                boolean ipv4 = false;

                if (network.getGateway() != null) {
                    if (nic.getIPv4Address() == null) {
                        PublicIp ip = null;

                        //Get ip address from the placeholder and don't allocate a new one
                        if (requestedIpv4 != null && vm.getType() == VirtualMachine.Type.DomainRouter) {
                            Nic placeholderNic = _networkModel.getPlaceholderNicForRouter(network, null);
                            if (placeholderNic != null) {
                                IPAddressVO userIp = _ipAddressDao.findByIpAndSourceNetworkId(network.getId(), placeholderNic.getIPv4Address());
                                ip = PublicIp.createFromAddrAndVlan(userIp, _vlanDao.findById(userIp.getVlanId()));
                                s_logger.debug("Nic got an ip address " + placeholderNic.getIPv4Address() + " stored in placeholder nic for the network " + network);
                            }
                        }

                        if (ip == null) {
                            ip = assignPublicIpAddress(dc.getId(), null, vm.getOwner(), VlanType.DirectAttached, network.getId(), requestedIpv4, false, false);
                        }

                        nic.setIPv4Address(ip.getAddress().toString());
                        nic.setIPv4Gateway(ip.getGateway());
                        nic.setIPv4Netmask(ip.getNetmask());
                        nic.setIsolationUri(IsolationType.Vlan.toUri(ip.getVlanTag()));
                        nic.setBroadcastType(network.getBroadcastDomainType());
                        if (network.getBroadcastUri() != null)
                            nic.setBroadcastUri(network.getBroadcastUri());
                        else
                            nic.setBroadcastUri(BroadcastDomainType.Vlan.toUri(ip.getVlanTag()));
                        nic.setFormat(AddressFormat.Ip4);
                        nic.setReservationId(String.valueOf(ip.getVlanTag()));
                        if(nic.getMacAddress() == null) {
                            nic.setMacAddress(ip.getMacAddress());
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