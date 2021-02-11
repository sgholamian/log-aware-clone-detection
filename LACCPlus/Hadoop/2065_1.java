//,temp,GetDelegationTokenServlet.java,44,85,temp,CancelDelegationTokenServlet.java,43,82
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
    LOG.info("Sending token: {" + ugi.getUserName() + "," + req.getRemoteAddr() +"}");
    final NameNode nn = NameNodeHttpServer.getNameNodeFromContext(context);
    String renewer = req.getParameter(RENEWER);
    final String renewerFinal = (renewer == null) ? 
        req.getUserPrincipal().getName() : renewer;
    
    DataOutputStream dos = null;
    try {
      dos = new DataOutputStream(resp.getOutputStream());
      final DataOutputStream dosFinal = dos; // for doAs block
      ugi.doAs(new PrivilegedExceptionAction<Void>() {
        @Override
        public Void run() throws IOException {
          final Credentials ts = DelegationTokenSecretManager.createCredentials(
              nn, ugi, renewerFinal);
          ts.write(dosFinal);
          return null;
        }
      });

    } catch(Exception e) {
      LOG.info("Exception while sending token. Re-throwing ", e);
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    } finally {
      if(dos != null) dos.close();
    }
  }

};