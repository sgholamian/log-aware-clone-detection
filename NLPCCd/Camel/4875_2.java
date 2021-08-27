//,temp,sample_649.java,2,17,temp,sample_2249.java,2,17
//,2
public class xxx {
public void dummy_method(){
server = new Server(PORT);
port = PORT;
ServletContextHandler servletContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
servletContext.setSecurityHandler(basicAuth("camel", "camelPass", "Private!"));
servletContext.setContextPath("/");
server.setHandler(servletContext);
servletContext.addServlet(new ServletHolder(new MyHttpServlet()), "/*");
try {
server.start();
} catch (Exception ex) {


log.info("could not start server");
}
}

};