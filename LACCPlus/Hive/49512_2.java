//,temp,InternalUtil.java,149,155,temp,HBaseSerDe.java,113,127
//,3
public class xxx {
  @Override
  public void initialize(Configuration configuration, Properties tableProperties, Properties partitionProperties)
      throws SerDeException {
    super.initialize(configuration, tableProperties, partitionProperties);

    serdeParams = new HBaseSerDeParameters(configuration, tableProperties, getClass().getName());

    cachedObjectInspector = HBaseLazyObjectFactory.createLazyHBaseStructInspector(serdeParams, tableProperties);

    cachedHBaseRow = new LazyHBaseRow((LazySimpleStructObjectInspector) cachedObjectInspector, serdeParams);

    serializer = new HBaseRowSerializer(serdeParams);

    LOG.debug("HBaseSerDe initialized with : {}", serdeParams);
  }

};