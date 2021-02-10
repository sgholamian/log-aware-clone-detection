//,temp,HttpServer2.java,700,711,temp,HttpServer.java,558,567
//,3
public class xxx {
  @Override
  public void addGlobalFilter(String name, String classname,
      Map<String, String> parameters) {
    final String[] ALL_URLS = { "/*" };
    defineFilter(webAppContext, name, classname, parameters, ALL_URLS);
    for (Context ctx : defaultContexts.keySet()) {
      defineFilter(ctx, name, classname, parameters, ALL_URLS);
    }
    LOG.info("Added global filter '" + name + "' (class=" + classname + ")");
  }

};