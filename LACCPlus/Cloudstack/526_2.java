//,temp,SHA256SaltedUserAuthenticator.java,48,92,temp,PBKDF2UserAuthenticator.java,52,104
//,3
public class xxx {
    @Override
    public Pair<Boolean, UserAuthenticator.ActionOnFailedAuthentication> authenticate(String username, String password, Long domainId, Map<String, Object[]> requestParameters) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Retrieving user: " + username);
        }

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            s_logger.debug("Username or Password cannot be empty");
            return new Pair<Boolean, ActionOnFailedAuthentication>(false, null);
        }

        boolean isValidUser = false;
        UserAccount user = this._userAccountDao.getUserAccount(username, domainId);
        if (user != null) {
            isValidUser = true;
        } else {
            s_logger.debug("Unable to find user with " + username + " in domain " + domainId);
        }

        byte[] salt = new byte[0];
        int rounds = s_rounds;
        try {
            if (isValidUser) {
                String[] storedPassword = user.getPassword().split(":");
                if ((storedPassword.length != 3) || (!StringUtils.isNumeric(storedPassword[2]))) {
                    s_logger.warn("The stored password for " + username + " isn't in the right format for this authenticator");
                    isValidUser = false;
                } else {
                    // Encoding format = <salt>:<password hash>:<rounds>
                    salt = decode(storedPassword[0]);
                    rounds = Integer.parseInt(storedPassword[2]);
                }
            }
            boolean result = false;
            if (isValidUser && validateCredentials(password, salt)) {
                result = ConstantTimeComparator.compareStrings(user.getPassword(), encode(password, salt, rounds));
            }

            UserAuthenticator.ActionOnFailedAuthentication action = null;
            if ((!result) && (isValidUser)) {
                action = UserAuthenticator.ActionOnFailedAuthentication.INCREMENT_INCORRECT_LOGIN_ATTEMPT_COUNT;
            }
            return new Pair(Boolean.valueOf(result), action);
        } catch (NumberFormatException e) {
            throw new CloudRuntimeException("Unable to hash password", e);
        } catch (NoSuchAlgorithmException e) {
            throw new CloudRuntimeException("Unable to hash password", e);
        } catch (UnsupportedEncodingException e) {
            throw new CloudRuntimeException("Unable to hash password", e);
        } catch (InvalidKeySpecException e) {
            throw new CloudRuntimeException("Unable to hash password", e);
        }
    }

};