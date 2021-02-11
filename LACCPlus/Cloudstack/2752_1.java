//,temp,ConsoleProxyServlet.java,595,688,temp,ApiServer.java,826,979
//,3
public class xxx {
    private boolean verifyRequest(Map<String, Object[]> requestParameters) {
        try {
            String apiKey = null;
            String secretKey = null;
            String signature = null;
            String unsignedRequest = null;

            // - build a request string with sorted params, make sure it's all lowercase
            // - sign the request, verify the signature is the same
            List<String> parameterNames = new ArrayList<String>();

            for (Object paramNameObj : requestParameters.keySet()) {
                parameterNames.add((String)paramNameObj); // put the name in a list that we'll sort later
            }

            Collections.sort(parameterNames);

            for (String paramName : parameterNames) {
                // parameters come as name/value pairs in the form String/String[]
                String paramValue = ((String[])requestParameters.get(paramName))[0];

                if ("signature".equalsIgnoreCase(paramName)) {
                    signature = paramValue;
                } else {
                    if ("apikey".equalsIgnoreCase(paramName)) {
                        apiKey = paramValue;
                    }

                    if (unsignedRequest == null) {
                        unsignedRequest = paramName + "=" + URLEncoder.encode(paramValue, "UTF-8").replaceAll("\\+", "%20");
                    } else {
                        unsignedRequest = unsignedRequest + "&" + paramName + "=" + URLEncoder.encode(paramValue, "UTF-8").replaceAll("\\+", "%20");
                    }
                }
            }

            // if api/secret key are passed to the parameters
            if ((signature == null) || (apiKey == null)) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("expired session, missing signature, or missing apiKey -- ignoring request...sig: " + signature + ", apiKey: " + apiKey);
                }
                return false; // no signature, bad request
            }

            TransactionLegacy txn = TransactionLegacy.open(TransactionLegacy.CLOUD_DB);
            txn.close();
            User user = null;
            // verify there is a user with this api key
            Pair<User, Account> userAcctPair = _accountMgr.findUserByApiKey(apiKey);
            if (userAcctPair == null) {
                s_logger.debug("apiKey does not map to a valid user -- ignoring request, apiKey: " + apiKey);
                return false;
            }

            user = userAcctPair.first();
            Account account = userAcctPair.second();

            if (!user.getState().equals(Account.State.enabled) || !account.getState().equals(Account.State.enabled)) {
                s_logger.debug("disabled or locked user accessing the api, userid = " + user.getId() + "; name = " + user.getUsername() + "; state: " + user.getState() +
                    "; accountState: " + account.getState());
                return false;
            }

            // verify secret key exists
            secretKey = user.getSecretKey();
            if (secretKey == null) {
                s_logger.debug("User does not have a secret key associated with the account -- ignoring request, username: " + user.getUsername());
                return false;
            }

            unsignedRequest = unsignedRequest.toLowerCase();

            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA1");
            mac.init(keySpec);
            mac.update(unsignedRequest.getBytes());
            byte[] encryptedBytes = mac.doFinal();
            String computedSignature = Base64.encodeBase64String(encryptedBytes);
            boolean equalSig = ConstantTimeComparator.compareStrings(signature, computedSignature);
            if (!equalSig) {
                s_logger.debug("User signature: " + signature + " is not equaled to computed signature: " + computedSignature);
            }

            if (equalSig) {
                requestParameters.put("userid", new Object[] {String.valueOf(user.getId())});
                requestParameters.put("account", new Object[] {account.getAccountName()});
                requestParameters.put("accountobj", new Object[] {account});
            }
            return equalSig;
        } catch (Exception ex) {
            s_logger.error("unable to verifty request signature", ex);
        }
        return false;
    }

};