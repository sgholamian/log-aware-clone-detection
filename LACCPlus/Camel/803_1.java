//,temp,ResteasyCamelServlet.java,296,303,temp,CamelHttpTransportServlet.java,77,85
//,3
public class xxx {
    public void destroy() {
        DefaultHttpRegistry.removeHttpRegistry(getServletName());
        if (httpRegistry != null) {
            httpRegistry.unregister(this);
            httpRegistry = null;
        }
        LOG.info("Destroyed CamelResteasyServlet[{}]", getServletName());
    }

};