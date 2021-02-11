//,temp,sample_4184.java,2,11,temp,sample_9364.java,2,13
//,3
public class xxx {
public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
ProxyUtils.rejectNonHttpRequests(req);
HttpServletRequest httpReq = (HttpServletRequest)req;
HttpServletResponse httpResp = (HttpServletResponse)resp;
if (LOG.isDebugEnabled()) {


log.info("remote address for request is");
}
}

};