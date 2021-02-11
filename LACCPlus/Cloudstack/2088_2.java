//,temp,RemoteAccessVpnManagerImpl.java,503,599,temp,RemoteAccessVpnManagerImpl.java,282,374
//,3
public class xxx {
    @Override
    @DB
    @ActionEvent(eventType = EventTypes.EVENT_REMOTE_ACCESS_VPN_DESTROY, eventDescription = "removing remote access vpn", async = true)
    public boolean destroyRemoteAccessVpnForIp(long ipId, Account caller, final boolean forceCleanup) throws ResourceUnavailableException {
        final RemoteAccessVpnVO vpn = _remoteAccessVpnDao.findByPublicIpAddress(ipId);
        if (vpn == null) {
            s_logger.debug("there are no Remote access vpns for public ip address id=" + ipId);
            return true;
        }

        _accountMgr.checkAccess(caller, AccessType.OperateEntry, true, vpn);

        RemoteAccessVpn.State prevState = vpn.getState();
        vpn.setState(RemoteAccessVpn.State.Removed);
        _remoteAccessVpnDao.update(vpn.getId(), vpn);

        boolean success = false;
        try {
            for (RemoteAccessVPNServiceProvider element : _vpnServiceProviders) {
                if (element.stopVpn(vpn)) {
                    success = true;
                    break;
                }
            }
        }catch (ResourceUnavailableException ex) {
            vpn.setState(prevState);
            _remoteAccessVpnDao.update(vpn.getId(), vpn);
            s_logger.debug("Failed to stop the vpn " + vpn.getId() + " , so reverted state to "+
                    RemoteAccessVpn.State.Running);
            success = false;
        } finally {
            if (success|| forceCleanup) {
                //Cleanup corresponding ports
                final List<? extends FirewallRule> vpnFwRules = _rulesDao.listByIpAndPurpose(ipId, Purpose.Vpn);

                boolean applyFirewall = false;
                final List<FirewallRuleVO> fwRules = new ArrayList<FirewallRuleVO>();
                //if related firewall rule is created for the first vpn port, it would be created for the 2 other ports as well, so need to cleanup the backend
                if (vpnFwRules.size() != 0 && _rulesDao.findByRelatedId(vpnFwRules.get(0).getId()) != null) {
                    applyFirewall = true;
                }

                if (applyFirewall) {
                    Transaction.execute(new TransactionCallbackNoReturn() {
                        @Override
                        public void doInTransactionWithoutResult(TransactionStatus status) {
                            for (FirewallRule vpnFwRule : vpnFwRules) {
                                //don't apply on the backend yet; send all 3 rules in a banch
                                _firewallMgr.revokeRelatedFirewallRule(vpnFwRule.getId(), false);
                                fwRules.add(_rulesDao.findByRelatedId(vpnFwRule.getId()));
                            }

                            s_logger.debug("Marked " + fwRules.size() + " firewall rules as Revoked as a part of disable remote access vpn");
                        }
                    });

                    //now apply vpn rules on the backend
                    s_logger.debug("Reapplying firewall rules for ip id=" + ipId + " as a part of disable remote access vpn");
                    success = _firewallMgr.applyIngressFirewallRules(ipId, caller);
                }

                if (success|| forceCleanup) {
                    try {
                        Transaction.execute(new TransactionCallbackNoReturn() {
                            @Override
                            public void doInTransactionWithoutResult(TransactionStatus status) {
                                _remoteAccessVpnDao.remove(vpn.getId());
                                // Stop billing of VPN users when VPN is removed. VPN_User_ADD events will be generated when VPN is created again
                                List<VpnUserVO> vpnUsers = _vpnUsersDao.listByAccount(vpn.getAccountId());
                                for (VpnUserVO user : vpnUsers) {
                                    // VPN_USER_REMOVE event is already generated for users in Revoke state
                                    if (user.getState() != VpnUser.State.Revoke) {
                                        UsageEventUtils.publishUsageEvent(EventTypes.EVENT_VPN_USER_REMOVE, user.getAccountId(), 0, user.getId(), user.getUsername(),
                                            user.getClass().getName(), user.getUuid());
                                    }
                                }
                                if (vpnFwRules != null) {
                                    for (FirewallRule vpnFwRule : vpnFwRules) {
                                        _rulesDao.remove(vpnFwRule.getId());
                                        s_logger.debug("Successfully removed firewall rule with ip id=" + vpnFwRule.getSourceIpAddressId() + " and port " +
                                            vpnFwRule.getSourcePortStart().intValue() + " as a part of vpn cleanup");
                                    }
                                }
                            }
                        });
                    } catch (Exception ex) {
                        s_logger.warn("Unable to release the three vpn ports from the firewall rules", ex);
                    }
                }
            }
        }
        return success;
    }

};