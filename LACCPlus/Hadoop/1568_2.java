//,temp,UserGroupInformation.java,1061,1100,temp,UserGroupInformation.java,994,1052
//,3
public class xxx {
  @InterfaceAudience.Public
  @InterfaceStability.Evolving
  public synchronized void reloginFromKeytab()
  throws IOException {
    if (!isSecurityEnabled() ||
         user.getAuthenticationMethod() != AuthenticationMethod.KERBEROS ||
         !isKeytab)
      return;
    
    long now = Time.now();
    if (!shouldRenewImmediatelyForTests && !hasSufficientTimeElapsed(now)) {
      return;
    }

    KerberosTicket tgt = getTGT();
    //Return if TGT is valid and is not going to expire soon.
    if (tgt != null && !shouldRenewImmediatelyForTests &&
        now < getRefreshTime(tgt)) {
      return;
    }
    
    LoginContext login = getLogin();
    if (login == null || keytabFile == null) {
      throw new IOException("loginUserFromKeyTab must be done first");
    }
    
    long start = 0;
    // register most recent relogin attempt
    user.setLastLogin(now);
    try {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Initiating logout for " + getUserName());
      }
      synchronized (UserGroupInformation.class) {
        // clear up the kerberos state. But the tokens are not cleared! As per
        // the Java kerberos login module code, only the kerberos credentials
        // are cleared
        login.logout();
        // login and also update the subject field of this instance to
        // have the new credentials (pass it to the LoginContext constructor)
        login = newLoginContext(
            HadoopConfiguration.KEYTAB_KERBEROS_CONFIG_NAME, getSubject(),
            new HadoopConfiguration());
        if (LOG.isDebugEnabled()) {
          LOG.debug("Initiating re-login for " + keytabPrincipal);
        }
        start = Time.now();
        login.login();
        metrics.loginSuccess.add(Time.now() - start);
        setLogin(login);
      }
    } catch (LoginException le) {
      if (start > 0) {
        metrics.loginFailure.add(Time.now() - start);
      }
      throw new IOException("Login failure for " + keytabPrincipal + 
          " from keytab " + keytabFile, le);
    } 
  }

};