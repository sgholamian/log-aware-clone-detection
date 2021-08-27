//,temp,sample_4625.java,2,12,temp,sample_4624.java,2,10
//,3
public class xxx {
public String rewrite(String url, HttpServletRequest request) throws Exception {
RewrittenUrl response = urlRewriter.processRequest(request, null);
if (response != null) {
String answer = response.getTarget();


log.info("rewrite url");
}
}

};