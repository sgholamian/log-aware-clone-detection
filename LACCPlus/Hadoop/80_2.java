//,temp,ReconfigurationServlet.java,214,236,temp,ReconfigurationServlet.java,199,212
//,3
public class xxx {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    LOG.info("GET");
    resp.setContentType("text/html");
    PrintWriter out = resp.getWriter();
    
    Reconfigurable reconf = getReconfigurable(req);
    String nodeName = reconf.getClass().getCanonicalName();

    printHeader(out, nodeName);
    printConf(out, reconf);
    printFooter(out);
  }

};