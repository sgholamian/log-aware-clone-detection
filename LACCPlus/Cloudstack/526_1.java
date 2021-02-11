//,temp,SHA256SaltedUserAuthenticator.java,48,92,temp,PBKDF2UserAuthenticator.java,52,104
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

        boolean realUser = true;
        UserAccount user = _userAccountDao.getUserAccount(username, domainId);
        if (user == null) {
            s_logger.debug("Unable to find user with " + username + " in domain " + domainId);
            realUser = false;
        }
        /* Fake Data */
        String realPassword = new String(s_defaultPassword);
        byte[] salt = new String(s_defaultSalt).getBytes();
        if (realUser) {
            String storedPassword[] = user.getPassword().split(":");
            if (storedPassword.length != 2) {
                s_logger.warn("The stored password for " + username + " isn't in the right format for this authenticator");
                realUser = false;
            } else {
                realPassword = storedPassword[1];
                salt = Base64.decode(storedPassword[0]);
            }
        }
        try {
            String hashedPassword = encode(password, salt);
            /* constantTimeEquals comes first in boolean since we need to thwart timing attacks */
            boolean result = constantTimeEquals(realPassword, hashedPassword) && realUser;
            ActionOnFailedAuthentication action = null;
            if (!result && realUser) {
                action = ActionOnFailedAuthentication.INCREMENT_INCORRECT_LOGIN_ATTEMPT_COUNT;
            }
            return new Pair<Boolean, ActionOnFailedAuthentication>(result, action);
        } catch (NoSuchAlgorithmException e) {
            throw new CloudRuntimeException("Unable to hash password", e);
        } catch (UnsupportedEncodingException e) {
            throw new CloudRuntimeException("Unable to hash password", e);
        }
    }

};