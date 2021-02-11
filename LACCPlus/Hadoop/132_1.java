//,temp,MetricsSystemImpl.java,295,304,temp,MetricsSystemImpl.java,260,272
//,3
public class xxx {
  synchronized void registerSink(String name, String desc, MetricsSink sink) {
    checkNotNull(config, "config");
    MetricsConfig conf = sinkConfigs.get(name);
    MetricsSinkAdapter sa = conf != null
        ? newSink(name, desc, sink, conf)
        : newSink(name, desc, sink, config.subset(SINK_KEY));
    sinks.put(name, sa);
    sa.start();
    LOG.info("Registered sink "+ name);
  }

};