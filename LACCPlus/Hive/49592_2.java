//,temp,DynamicPartitionPruner.java,379,409,temp,SparkDynamicPartitionPruner.java,260,282
//,3
public class xxx {
    SourceInfo(TableDesc table, ExprNodeDesc partKey, String columnName, String columnType, JobConf jobConf)
        throws SerDeException {
      this.partKey = partKey;
      this.columnName = columnName;
      this.columnType = columnType;

      AbstractSerDe serDe = ReflectionUtils.newInstance(table.getSerDeClass(), null);
      serDe.initialize(jobConf, table.getProperties(), null);
      deserializer = serDe;

      ObjectInspector inspector = deserializer.getObjectInspector();
      if (LOG.isDebugEnabled()) {
        LOG.debug("Type of obj insp: " + inspector.getTypeName());
      }

      soi = (StructObjectInspector) inspector;
      List<? extends StructField> fields = soi.getAllStructFieldRefs();
      assert(fields.size() > 1) : "expecting single field in input";

      field = fields.get(0);
      fieldInspector =
          ObjectInspectorUtils.getStandardObjectInspector(field.getFieldObjectInspector());
    }

};