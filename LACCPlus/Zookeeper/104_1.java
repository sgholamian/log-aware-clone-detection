//,temp,ZooKeeperSaslClient.java,308,351,temp,SaslQuorumAuthLearner.java,181,222
//,3
public class xxx {
    private byte[] createSaslToken(final byte[] saslToken) throws SaslException {
        if (saslToken == null) {
            // TODO: introspect about runtime environment (such as jaas.conf)
            saslState = SaslState.FAILED;
            throw new SaslException("Error in authenticating with a Zookeeper Quorum member: the quorum member's saslToken is null.");
        }

        Subject subject = login.getSubject();
        if (subject != null) {
            synchronized(login) {
                try {
                    final byte[] retval =
                        Subject.doAs(subject, new PrivilegedExceptionAction<byte[]>() {
                                public byte[] run() throws SaslException {
                                    LOG.debug("saslClient.evaluateChallenge(len="+saslToken.length+")");
                                    return saslClient.evaluateChallenge(saslToken);
                                }
                            });
                    return retval;
                }
                catch (PrivilegedActionException e) {
                    String error = "An error: (" + e + ") occurred when evaluating Zookeeper Quorum Member's " +
                      " received SASL token.";
                    // Try to provide hints to use about what went wrong so they can fix their configuration.
                    // TODO: introspect about e: look for GSS information.
                    final String UNKNOWN_SERVER_ERROR_TEXT =
                      "(Mechanism level: Server not found in Kerberos database (7) - UNKNOWN_SERVER)";
                    if (e.toString().contains(UNKNOWN_SERVER_ERROR_TEXT)) {
                        error += " This may be caused by Java's being unable to resolve the Zookeeper Quorum Member's" +
                          " hostname correctly. You may want to try to adding" +
                          " '-Dsun.net.spi.nameservice.provider.1=dns,sun' to your client's JVMFLAGS environment.";
                    }
                    error += " Zookeeper Client will go to AUTH_FAILED state.";
                    LOG.error(error);
                    saslState = SaslState.FAILED;
                    throw new SaslException(error);
                }
            }
        }
        else {
            throw new SaslException("Cannot make SASL token without subject defined. " +
              "For diagnosis, please look for WARNs and ERRORs in your log related to the Login class.");
        }
    }

};