//,temp,HttpServer2.java,677,698,temp,HttpServer.java,538,556
//,3
public class xxx {
  @Override
  public void addFilter(String name, String classname,
      Map<String, String> parameters) {

    FilterHolder filterHolder = getFilterHolder(name, classname, parameters);
    final String[] USER_FACING_URLS = { "*.html", "*.jsp" };
    FilterMapping fmap = getFilterMapping(name, USER_FACING_URLS);
    defineFilter(webAppContext, filterHolder, fmap);
    LOG.info(
        "Added filter " + name + " (class=" + classname + ") to context " + webAppContext.getDisplayName());
    final String[] ALL_URLS = { "/*" };
    fmap = getFilterMapping(name, ALL_URLS);
    for (Map.Entry<Context, Boolean> e : defaultContexts.entrySet()) {
      if (e.getValue()) {
        Context ctx = e.getKey();
        defineFilter(ctx, filterHolder, fmap);
        LOG.info("Added filter " + name + " (class=" + classname
            + ") to context " + ctx.getDisplayName());
      }
    }
    filterNames.add(name);
  }

};