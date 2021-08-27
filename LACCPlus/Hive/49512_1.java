//,temp,InternalUtil.java,149,155,temp,HBaseSerDe.java,113,127
//,3
public class xxx {
  static void initializeDeserializer(Deserializer deserializer, Configuration conf,
                     HCatTableInfo info, HCatSchema schema) throws SerDeException {
    Properties props = getSerdeProperties(info, schema);
    LOG.info("Initializing " + deserializer.getClass().getName() + " with properties " + props);
    AbstractSerDe serDe = (AbstractSerDe) deserializer;
    serDe.initialize(conf, props, null);
  }

};