//,temp,ConsoleProxyServlet.java,595,688,temp,ApiServer.java,826,979
//,3
public class xxx {
    @Override
    public boolean verifyRequest(final Map<String, Object[]> requestParameters, final Long userId, InetAddress remoteAddress) throws ServerApiException {
        try {
            String apiKey = null;
            String secretKey = null;
            String signature = null;
            String unsignedRequest = null;

            final String[] command = (String[])requestParameters.get(ApiConstants.COMMAND);
            if (command == null) {
                s_logger.info("missing command, ignoring request...");
                return false;
            }

            final String commandName = command[0];

            // if userId not null, that mean that user is logged in
            if (userId != null) {
                final User user = ApiDBUtils.findUserById(userId);

                if (!commandAvailable(remoteAddress, commandName, user)) {
                    return false;
                }

                return true;
            } else {
                // check against every available command to see if the command exists or not
                if (!s_apiNameCmdClassMap.containsKey(commandName) && !commandName.equals("login") && !commandName.equals("logout")) {
                    final String errorMessage = "The given command " + commandName + " either does not exist, is not available" +
                            " for user, or not available from ip address '" + remoteAddress.getHostAddress() + "'.";
                    s_logger.debug(errorMessage);
                    return false;
                }
            }

            // - build a request string with sorted params, make sure it's all lowercase
            // - sign the request, verify the signature is the same
            final List<String> parameterNames = new ArrayList<String>();

            for (final Object paramNameObj : requestParameters.keySet()) {
                parameterNames.add((String)paramNameObj); // put the name in a list that we'll sort later
            }

            Collections.sort(parameterNames);

            String signatureVersion = null;
            String expires = null;

            for (final String paramName : parameterNames) {
                // parameters come as name/value pairs in the form String/String[]
                final String paramValue = ((String[])requestParameters.get(paramName))[0];

                if (ApiConstants.SIGNATURE.equalsIgnoreCase(paramName)) {
                    signature = paramValue;
                } else {
                    if (ApiConstants.API_KEY.equalsIgnoreCase(paramName)) {
                        apiKey = paramValue;
                    } else if (ApiConstants.SIGNATURE_VERSION.equalsIgnoreCase(paramName)) {
                        signatureVersion = paramValue;
                    } else if (ApiConstants.EXPIRES.equalsIgnoreCase(paramName)) {
                        expires = paramValue;
                    }

                    if (unsignedRequest == null) {
                        unsignedRequest = paramName + "=" + URLEncoder.encode(paramValue, HttpUtils.UTF_8).replaceAll("\\+", "%20");
                    } else {
                        unsignedRequest = unsignedRequest + "&" + paramName + "=" + URLEncoder.encode(paramValue, HttpUtils.UTF_8).replaceAll("\\+", "%20");
                    }
                }
            }

            // if api/secret key are passed to the parameters
            if ((signature == null) || (apiKey == null)) {
                s_logger.debug("Expired session, missing signature, or missing apiKey -- ignoring request. Signature: " + signature + ", apiKey: " + apiKey);
                return false; // no signature, bad request
            }

            Date expiresTS = null;
            // FIXME: Hard coded signature, why not have an enum
            if ("3".equals(signatureVersion)) {
                // New signature authentication. Check for expire parameter and its validity
                if (expires == null) {
                    s_logger.debug("Missing Expires parameter -- ignoring request. Signature: " + signature + ", apiKey: " + apiKey);
                    return false;
                }

                try {
                    expiresTS = DateUtil.parseTZDateString(expires);
                } catch (final ParseException pe) {
                    s_logger.debug("Incorrect date format for Expires parameter", pe);
                    return false;
                }

                final Date now = new Date(System.currentTimeMillis());
                if (expiresTS.before(now)) {
                    s_logger.debug("Request expired -- ignoring ...sig: " + signature + ", apiKey: " + apiKey);
                    return false;
                }
            }

            final TransactionLegacy txn = TransactionLegacy.open(TransactionLegacy.CLOUD_DB);
            txn.close();
            User user = null;
            // verify there is a user with this api key
            final Pair<User, Account> userAcctPair = accountMgr.findUserByApiKey(apiKey);
            if (userAcctPair == null) {
                s_logger.debug("apiKey does not map to a valid user -- ignoring request, apiKey: " + apiKey);
                return false;
            }

            user = userAcctPair.first();
            final Account account = userAcctPair.second();

            if (user.getState() != Account.State.enabled || !account.getState().equals(Account.State.enabled)) {
                s_logger.info("disabled or locked user accessing the api, userid = " + user.getId() + "; name = " + user.getUsername() + "; state: " + user.getState() +
                        "; accountState: " + account.getState());
                return false;
            }

            if (!commandAvailable(remoteAddress, commandName, user)) {
                return false;
            }

            // verify secret key exists
            secretKey = user.getSecretKey();
            if (secretKey == null) {
                s_logger.info("User does not have a secret key associated with the account -- ignoring request, username: " + user.getUsername());
                return false;
            }

            unsignedRequest = unsignedRequest.toLowerCase();

            final Mac mac = Mac.getInstance("HmacSHA1");
            final SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA1");
            mac.init(keySpec);
            mac.update(unsignedRequest.getBytes());

            final byte[] encryptedBytes = mac.doFinal();
            final String computedSignature = Base64.encodeBase64String(encryptedBytes);
            final boolean equalSig = ConstantTimeComparator.compareStrings(signature, computedSignature);

            if (!equalSig) {
                s_logger.info("User signature: " + signature + " is not equaled to computed signature: " + computedSignature);
            } else {
                CallContext.register(user, account);
            }
            return equalSig;
        } catch (final ServerApiException ex) {
            throw ex;
        } catch (final Exception ex) {
            s_logger.error("unable to verify request signature");
        }
        return false;
    }

};