//,temp,RenewDelegationTokenServlet.java,47,91,temp,CancelDelegationTokenServlet.java,43,82
//,3
public class xxx {
  @Override
  protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
      throws ServletException, IOException {
    final UserGroupInformation ugi;
    final ServletContext context = getServletContext();
    final Configuration conf = NameNodeHttpServer.getConfFromContext(context);
    try {
      ugi = getUGI(req, conf);
    } catch(IOException ioe) {
      LOG.info("Request for token received with no authentication from "
          + req.getRemoteAddr(), ioe);
      resp.sendError(HttpServletResponse.SC_FORBIDDEN, 
          "Unable to identify or authenticate user");
      return;
    }
    final NameNode nn = NameNodeHttpServer.getNameNodeFromContext(
        context);
    String tokenString = req.getParameter(TOKEN);
    if (tokenString == null) {
      resp.sendError(HttpServletResponse.SC_MULTIPLE_CHOICES,
                     "Token to renew not specified");
    }
    final Token<DelegationTokenIdentifier> token = 
      new Token<DelegationTokenIdentifier>();
    token.decodeFromUrlString(tokenString);
    
    try {
      ugi.doAs(new PrivilegedExceptionAction<Void>() {
        @Override
        public Void run() throws Exception {
          nn.getRpcServer().cancelDelegationToken(token);
          return null;
        }
      });
    } catch(Exception e) {
      LOG.info("Exception while cancelling token. Re-throwing. ", e);
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                     e.getMessage());
    }
  }

};