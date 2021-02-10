//,temp,TestPathFilter.java,60,70,temp,TestGlobalFilter.java,60,70
//,2
public class xxx {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
        FilterChain chain) throws IOException, ServletException {
      if (filterConfig == null)
         return;

      String uri = ((HttpServletRequest)request).getRequestURI();
      LOG.info("filtering " + uri);
      RECORDS.add(uri);
      chain.doFilter(request, response);
    }

};