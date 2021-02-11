//,temp,RemoteAccessVpnManagerImpl.java,503,599,temp,RemoteAccessVpnManagerImpl.java,282,374
//,3
public class xxx {
    @DB
    @Override
    public boolean applyVpnUsers(long vpnOwnerId, String userName) throws  ResourceUnavailableException {
        Account caller = CallContext.current().getCallingAccount();
        Account owner = _accountDao.findById(vpnOwnerId);
        _accountMgr.checkAccess(caller, null, true, owner);

        s_logger.debug("Applying vpn users for " + owner);
        List<RemoteAccessVpnVO> vpns = _remoteAccessVpnDao.findByAccount(vpnOwnerId);

        if (CollectionUtils.isEmpty(vpns)) {
            s_logger.debug("There are no remote access vpns configured on this account  " + owner +" to apply vpn user, failing add vpn user ");
            return false;
        }

        RemoteAccessVpnVO vpnTemp  = null;

        List<VpnUserVO> users = _vpnUsersDao.listByAccount(vpnOwnerId);

        //If user is in Active state, we still have to resend them therefore their status has to be Add
        for (VpnUserVO user : users) {
            if (user.getState() == State.Active) {
                user.setState(State.Add);
                _vpnUsersDao.update(user.getId(), user);
            }
        }

        boolean success = true;

        Boolean[] finals = new Boolean[users.size()];
        for (RemoteAccessVPNServiceProvider element : _vpnServiceProviders) {
            s_logger.debug("Applying vpn access to " + element.getName());
            for (RemoteAccessVpnVO vpn : vpns) {
                try {
                    String[] results = element.applyVpnUsers(vpn, users);
                    if (results != null) {
                        int indexUser = -1;
                        for (int i = 0; i < results.length; i++) {
                            indexUser ++;
                            if (indexUser == users.size()) {
                                indexUser = 0; // results on multiple VPC routers are combined in commit 13eb789, reset user index if one VR is done.
                            }
                            s_logger.debug("VPN User " + users.get(indexUser) + (results[i] == null ? " is set on " : (" couldn't be set due to " + results[i]) + " on ") + vpn.getUuid());
                            if (results[i] == null) {
                                if (finals[indexUser] == null) {
                                    finals[indexUser] = true;
                                }
                            } else {
                                finals[indexUser] = false;
                                success = false;
                                vpnTemp = vpn;
                            }
                        }
                    }
                } catch (Exception e) {
                    s_logger.warn("Unable to apply vpn users ", e);
                    success = false;
                    vpnTemp = vpn;

                    for (int i = 0; i < finals.length; i++) {
                        finals[i] = false;
                    }
                }
            }
        }

        for (int i = 0; i < finals.length; i++) {
            final VpnUserVO user = users.get(i);
            if (finals[i]) {
                if (user.getState() == State.Add) {
                    user.setState(State.Active);
                    _vpnUsersDao.update(user.getId(), user);
                } else if (user.getState() == State.Revoke) {
                    _vpnUsersDao.remove(user.getId());
                }
            } else {
                if (user.getState() == State.Add && (user.getUsername()).equals(userName)) {
                    Transaction.execute(new TransactionCallbackNoReturn() {
                        @Override
                        public void doInTransactionWithoutResult(TransactionStatus status) {
                            _vpnUsersDao.remove(user.getId());
                            UsageEventUtils.publishUsageEvent(EventTypes.EVENT_VPN_USER_REMOVE, user.getAccountId(), 0, user.getId(), user.getUsername(), user.getClass()
                                .getName(), user.getUuid());
                        }
                    });
                }
                s_logger.warn("Failed to apply vpn for user " + user.getUsername() + ", accountId=" + user.getAccountId());
            }
        }

        if (!success) {
            throw new  ResourceUnavailableException("Failed add vpn user due to Resource unavailable ",
                    RemoteAccessVPNServiceProvider.class, vpnTemp.getId());
        }

        return success;
    }

};