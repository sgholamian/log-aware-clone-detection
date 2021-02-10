//,temp,HttpServer2.java,607,616,temp,HttpServer.java,468,477
//,2
public class xxx {
  public void addJerseyResourcePackage(final String packageName,
      final String pathSpec) {
    LOG.info("addJerseyResourcePackage: packageName=" + packageName
        + ", pathSpec=" + pathSpec);
    final ServletHolder sh = new ServletHolder(ServletContainer.class);
    sh.setInitParameter("com.sun.jersey.config.property.resourceConfigClass",
        "com.sun.jersey.api.core.PackagesResourceConfig");
    sh.setInitParameter("com.sun.jersey.config.property.packages", packageName);
    webAppContext.addServlet(sh, pathSpec);
  }

};