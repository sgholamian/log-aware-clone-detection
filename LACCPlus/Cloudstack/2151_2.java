//,temp,NetworkServiceImpl.java,559,607,temp,NetworkServiceImpl.java,519,557
//,3
public class xxx {
    @Override
    @ActionEvent(eventType = EventTypes.EVENT_NET_IP_ASSIGN, eventDescription = "allocating Ip", create = true)
    public IpAddress allocateIP(Account ipOwner, long zoneId, Long networkId, Boolean displayIp)
            throws ResourceAllocationException, InsufficientAddressCapacityException, ConcurrentOperationException {

        Account caller = CallContext.current().getCallingAccount();
        long callerUserId = CallContext.current().getCallingUserId();
        DataCenter zone = _entityMgr.findById(DataCenter.class, zoneId);

        if (networkId != null) {
            Network network = _networksDao.findById(networkId);
            if (network == null) {
                throw new InvalidParameterValueException("Invalid network id is given");
            }

            if (network.getGuestType() == Network.GuestType.Shared) {
                if (zone == null) {
                    throw new InvalidParameterValueException("Invalid zone Id is given");
                }
                // if shared network in the advanced zone, then check the caller against the network for 'AccessType.UseNetwork'
                if (zone.getNetworkType() == NetworkType.Advanced) {
                    if (isSharedNetworkOfferingWithServices(network.getNetworkOfferingId())) {
                        _accountMgr.checkAccess(caller, AccessType.UseEntry, false, network);
                        if (s_logger.isDebugEnabled()) {
                            s_logger.debug("Associate IP address called by the user " + callerUserId + " account " + ipOwner.getId());
                        }
                        return _ipAddrMgr.allocateIp(ipOwner, false, caller, callerUserId, zone, displayIp);
                    } else {
                        throw new InvalidParameterValueException("Associate IP address can only be called on the shared networks in the advanced zone"
                                + " with Firewall/Source Nat/Static Nat/Port Forwarding/Load balancing services enabled");
                    }
                }
            }
        } else {
            _accountMgr.checkAccess(caller, null, false, ipOwner);
        }

        return _ipAddrMgr.allocateIp(ipOwner, false, caller, callerUserId, zone, displayIp);
    }

};