//,temp,NicPlugInOutRules.java,154,217,temp,VpcVirtualNetworkApplianceManagerImpl.java,637,697
//,3
public class xxx {
    private Pair<Map<String, PublicIpAddress>, Map<String, PublicIpAddress>> getNicsToChangeOnRouter(final NetworkTopologyVisitor visitor) {
        // 1) check which nics need to be plugged/unplugged and plug/unplug them

        final Map<String, PublicIpAddress> nicsToPlug = new HashMap<String, PublicIpAddress>();
        final Map<String, PublicIpAddress> nicsToUnplug = new HashMap<String, PublicIpAddress>();

        VpcManager vpcMgr = visitor.getVirtualNetworkApplianceFactory().getVpcMgr();
        NicDao nicDao = visitor.getVirtualNetworkApplianceFactory().getNicDao();
        // find out nics to unplug
        for (PublicIpAddress ip : _ipAddresses) {
            long publicNtwkId = ip.getNetworkId();

            // if ip is not associated to any network, and there are no firewall
            // rules, release it on the backend
            if (!vpcMgr.isIpAllocatedToVpc(ip)) {
                ip.setState(IpAddress.State.Releasing);
            }

            if (ip.getState() == IpAddress.State.Releasing) {
                Nic nic = nicDao.findByIp4AddressAndNetworkIdAndInstanceId(publicNtwkId, _router.getId(), ip.getAddress().addr());
                if (nic != null) {
                    nicsToUnplug.put(ip.getVlanTag(), ip);
                    s_logger.debug("Need to unplug the nic for ip=" + ip + "; vlan=" + ip.getVlanTag() + " in public network id =" + publicNtwkId);
                }
            }
        }

        // find out nics to plug
        for (PublicIpAddress ip : _ipAddresses) {
            URI broadcastUri = BroadcastDomainType.Vlan.toUri(ip.getVlanTag());
            long publicNtwkId = ip.getNetworkId();

            // if ip is not associated to any network, and there are no firewall
            // rules, release it on the backend
            if (!vpcMgr.isIpAllocatedToVpc(ip)) {
                ip.setState(IpAddress.State.Releasing);
            }

            if (ip.getState() == IpAddress.State.Allocated || ip.getState() == IpAddress.State.Allocating) {
                // nic has to be plugged only when there are no nics for this
                // vlan tag exist on VR
                Nic nic = nicDao.findByNetworkIdInstanceIdAndBroadcastUri(publicNtwkId, _router.getId(), broadcastUri.toString());

                if (nic == null && nicsToPlug.get(ip.getVlanTag()) == null) {
                    nicsToPlug.put(ip.getVlanTag(), ip);
                    s_logger.debug("Need to plug the nic for ip=" + ip + "; vlan=" + ip.getVlanTag() + " in public network id =" + publicNtwkId);
                } else {
                    final PublicIpAddress nicToUnplug = nicsToUnplug.get(ip.getVlanTag());
                    if (nicToUnplug != null) {
                        NicVO nicVO = nicDao.findByIp4AddressAndNetworkIdAndInstanceId(publicNtwkId, _router.getId(), nicToUnplug.getAddress().addr());
                        nicVO.setIPv4Address(ip.getAddress().addr());
                        nicDao.update(nicVO.getId(), nicVO);
                        s_logger.debug("Updated the nic " + nicVO + " with the new ip address " + ip.getAddress().addr());
                        nicsToUnplug.remove(ip.getVlanTag());
                    }
                }
            }
        }

        Pair<Map<String, PublicIpAddress>, Map<String, PublicIpAddress>> nicsToChange = new Pair<Map<String, PublicIpAddress>, Map<String, PublicIpAddress>>(nicsToPlug,
                nicsToUnplug);

        return nicsToChange;
    }

};