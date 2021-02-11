//,temp,AccountManagerImpl.java,2861,2872,temp,AccountManagerImpl.java,2831,2844
//,3
public class xxx {
    @Override
    public void checkAccess(User user, ControlledEntity entity) throws PermissionDeniedException {
        for (SecurityChecker checker : _securityCheckers) {
            if (checker.checkAccess(user, entity)) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Access granted to " + user + "to " + entity + "by " + checker.getName());
                }
                return;
            }
        }
        throw new PermissionDeniedException("There's no way to confirm " + user + " has access to " + entity);
    }

};