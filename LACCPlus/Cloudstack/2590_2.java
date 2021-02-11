//,temp,MD5UserAuthenticator.java,45,67,temp,PlainTextUserAuthenticator.java,36,58
//,3
public class xxx {
    @Override
    public Pair<Boolean, ActionOnFailedAuthentication> authenticate(String username, String password, Long domainId, Map<String, Object[]> requestParameters) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Retrieving user: " + username);
        }

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            s_logger.debug("Username or Password cannot be empty");
            return new Pair<Boolean, ActionOnFailedAuthentication>(false, null);
        }

        UserAccount user = _userAccountDao.getUserAccount(username, domainId);
        if (user == null) {
            s_logger.debug("Unable to find user with " + username + " in domain " + domainId);
            return new Pair<Boolean, ActionOnFailedAuthentication>(false, null);
        }

        if (!user.getPassword().equals(password)) {
            s_logger.debug("Password does not match");
            return new Pair<Boolean, ActionOnFailedAuthentication>(false, ActionOnFailedAuthentication.INCREMENT_INCORRECT_LOGIN_ATTEMPT_COUNT);
        }
        return new Pair<Boolean, ActionOnFailedAuthentication>(true, null);
    }

};