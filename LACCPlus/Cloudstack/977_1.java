//,temp,ConsoleProxyServlet.java,577,591,temp,ApiServer.java,1137,1151
//,3
public class xxx {
    public boolean verifyUser(Long userId) {
        // copy from ApiServer.java, a bit ugly here
        User user = _accountMgr.getUserIncludingRemoved(userId);
        Account account = null;
        if (user != null) {
            account = _accountMgr.getAccount(user.getAccountId());
        }

        if ((user == null) || (user.getRemoved() != null) || !user.getState().equals(Account.State.enabled) || (account == null) ||
            !account.getState().equals(Account.State.enabled)) {
            s_logger.warn("Deleted/Disabled/Locked user with id=" + userId + " attempting to access public API");
            return false;
        }
        return true;
    }

};