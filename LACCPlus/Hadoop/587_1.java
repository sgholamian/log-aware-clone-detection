//,temp,HttpServer2.java,658,675,temp,HttpServer.java,519,536
//,2
public class xxx {
  public void addInternalServlet(String name, String pathSpec,
      Class<? extends HttpServlet> clazz, boolean requireAuth) {
    ServletHolder holder = new ServletHolder(clazz);
    if (name != null) {
      holder.setName(name);
    }
    webAppContext.addServlet(holder, pathSpec);

    if(requireAuth && UserGroupInformation.isSecurityEnabled()) {
       LOG.info("Adding Kerberos (SPNEGO) filter to " + name);
       ServletHandler handler = webAppContext.getServletHandler();
       FilterMapping fmap = new FilterMapping();
       fmap.setPathSpec(pathSpec);
       fmap.setFilterName(SPNEGO_FILTER);
       fmap.setDispatches(Handler.ALL);
       handler.addFilterMapping(fmap);
    }
  }

};