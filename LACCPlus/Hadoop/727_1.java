//,temp,HttpServer2.java,700,711,temp,HttpServer.java,558,567
//,3
public class xxx {
  @Override
  public void addGlobalFilter(String name, String classname,
      Map<String, String> parameters) {
    final String[] ALL_URLS = { "/*" };
    FilterHolder filterHolder = getFilterHolder(name, classname, parameters);
    FilterMapping fmap = getFilterMapping(name, ALL_URLS);
    defineFilter(webAppContext, filterHolder, fmap);
    for (Context ctx : defaultContexts.keySet()) {
      defineFilter(ctx, filterHolder, fmap);
    }
    LOG.info("Added global filter '" + name + "' (class=" + classname + ")");
  }

};