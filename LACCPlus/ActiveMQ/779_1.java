//,temp,DiscoveryRegistryServlet.java,93,101,temp,DiscoveryRegistryServlet.java,41,49
//,3
public class xxx {
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String group = req.getPathInfo();
        String service = req.getHeader("service");
        LOG.debug("Unregistering: group="+group+", service="+service);

        ConcurrentMap<String, Long> services = getServiceGroup(group);
        services.remove(service);
    }

};