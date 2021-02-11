//,temp,VpcVirtualRouterElement.java,615,645,temp,VpcVirtualRouterElement.java,584,613
//,2
public class xxx {
    @Override
    public boolean startSite2SiteVpn(final Site2SiteVpnConnection conn) throws ResourceUnavailableException {
        final Site2SiteVpnGateway vpnGw = _vpnGatewayDao.findById(conn.getVpnGatewayId());
        final IpAddress ip = _ipAddressDao.findById(vpnGw.getAddrId());

        final Map<Capability, String> vpnCapabilities = capabilities.get(Service.Vpn);
        if (!vpnCapabilities.get(Capability.VpnTypes).contains("s2svpn")) {
            s_logger.error("try to start site 2 site vpn on unsupported network element?");
            return false;
        }

        final Long vpcId = ip.getVpcId();
        final Vpc vpc = _entityMgr.findById(Vpc.class, vpcId);

        if (!_ntwkModel.isProviderEnabledInZone(vpc.getZoneId(), Provider.VPCVirtualRouter.getName())) {
            throw new ResourceUnavailableException("VPC provider is not enabled in zone " + vpc.getZoneId(), DataCenter.class, vpc.getZoneId());
        }

        final List<DomainRouterVO> routers = _vpcRouterMgr.getVpcRouters(ip.getVpcId());
        if (routers == null) {
            throw new ResourceUnavailableException("Cannot enable site-to-site VPN on the backend; virtual router doesn't exist in the vpc " + ip.getVpcId(), DataCenter.class,
                    vpc.getZoneId());
        }

        boolean result = true;
        for (final DomainRouterVO domainRouterVO : routers) {
            result = result && _vpcRouterMgr.startSite2SiteVpn(conn, domainRouterVO);
        }
        return result;
    }

};