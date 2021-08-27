//,temp,sample_1130.java,2,12,temp,sample_2283.java,2,14
//,3
public class xxx {
public void initialize(Configuration conf, Properties tbl) throws SerDeException {
serdeParams = new HBaseSerDeParameters(conf, tbl, getClass().getName());
cachedObjectInspector = HBaseLazyObjectFactory.createLazyHBaseStructInspector(serdeParams, tbl);
cachedHBaseRow = new LazyHBaseRow( (LazySimpleStructObjectInspector) cachedObjectInspector, serdeParams);
serializer = new HBaseRowSerializer(serdeParams);
if (LOG.isDebugEnabled()) {


log.info("hbaseserde initialized with");
}
}

};