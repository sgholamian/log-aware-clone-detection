//,temp,ReconfigurationServlet.java,214,236,temp,ReconfigurationServlet.java,199,212
//,3
public class xxx {
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    LOG.info("POST");
    resp.setContentType("text/html");
    PrintWriter out = resp.getWriter();

    Reconfigurable reconf = getReconfigurable(req);
    String nodeName = reconf.getClass().getCanonicalName();

    printHeader(out, nodeName);

    try { 
      applyChanges(out, reconf, req);
    } catch (ReconfigurationException e) {
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                     StringUtils.stringifyException(e));
      return;
    }

    out.println("<p><a href=\"" + req.getServletPath() + "\">back</a></p>");
    printFooter(out);
  }

};