//,temp,sample_6694.java,2,13,temp,sample_2735.java,2,13
//,2
public class xxx {
protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
final UserGroupInformation ugi;
final ServletContext context = getServletContext();
final Configuration conf = NameNodeHttpServer.getConfFromContext(context);
try {
ugi = getUGI(req, conf);
} catch(IOException ioe) {


log.info("request for token received with no authentication from");
}
}

};