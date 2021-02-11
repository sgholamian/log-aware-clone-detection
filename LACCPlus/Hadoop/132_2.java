//,temp,MetricsSystemImpl.java,295,304,temp,MetricsSystemImpl.java,260,272
//,3
public class xxx {
  synchronized
  void registerSource(String name, String desc, MetricsSource source) {
    checkNotNull(config, "config");
    MetricsConfig conf = sourceConfigs.get(name);
    MetricsSourceAdapter sa = conf != null
        ? new MetricsSourceAdapter(prefix, name, desc, source,
                                   injectedTags, period, conf)
        : new MetricsSourceAdapter(prefix, name, desc, source,
          injectedTags, period, config.subset(SOURCE_KEY));
    sources.put(name, sa);
    sa.start();
    LOG.debug("Registered source "+ name);
  }

};