//,temp,UserGroupInformation.java,1061,1100,temp,UserGroupInformation.java,994,1052
//,3
public class xxx {
  @InterfaceAudience.Public
  @InterfaceStability.Evolving
  public synchronized void reloginFromTicketCache()
  throws IOException {
    if (!isSecurityEnabled() || 
        user.getAuthenticationMethod() != AuthenticationMethod.KERBEROS ||
        !isKrbTkt)
      return;
    LoginContext login = getLogin();
    if (login == null) {
      throw new IOException("login must be done first");
    }
    long now = Time.now();
    if (!hasSufficientTimeElapsed(now)) {
      return;
    }
    // register most recent relogin attempt
    user.setLastLogin(now);
    try {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Initiating logout for " + getUserName());
      }
      //clear up the kerberos state. But the tokens are not cleared! As per 
      //the Java kerberos login module code, only the kerberos credentials
      //are cleared
      login.logout();
      //login and also update the subject field of this instance to 
      //have the new credentials (pass it to the LoginContext constructor)
      login = 
        newLoginContext(HadoopConfiguration.USER_KERBEROS_CONFIG_NAME, 
            getSubject(), new HadoopConfiguration());
      if (LOG.isDebugEnabled()) {
        LOG.debug("Initiating re-login for " + getUserName());
      }
      login.login();
      setLogin(login);
    } catch (LoginException le) {
      throw new IOException("Login failure for " + getUserName(), le);
    } 
  }

};