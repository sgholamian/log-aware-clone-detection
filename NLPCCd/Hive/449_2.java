//,temp,sample_2629.java,2,13,temp,sample_2366.java,2,14
//,3
public class xxx {
private String validateCookie(HttpServletRequest request) throws UnsupportedEncodingException {
Cookie[] cookies = request.getCookies();
if (cookies == null) {
if (LOG.isDebugEnabled()) {
}
return null;
}
if (LOG.isDebugEnabled()) {


log.info("received cookies");
}
}

};