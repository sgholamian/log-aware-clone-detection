//,temp,DynamicPartitionPruner.java,379,409,temp,SparkDynamicPartitionPruner.java,260,282
//,3
public class xxx {
    public SourceInfo(TableDesc table, ExprNodeDesc partKey, ExprNodeDesc predicate, String columnName, String columnType, JobConf jobConf)
        throws SerDeException {

      this.skipPruning.set(false);

      this.partKey = partKey;
      this.predicate = predicate;

      this.columnName = columnName;
      this.columnType = columnType;
      this.mustKeepOnePartition = jobConf.getBoolean(Utilities.ENSURE_OPERATORS_EXECUTED, false);

      AbstractSerDe serDe = ReflectionUtils.newInstance(table.getSerDeClass(), null);
      serDe.initialize(jobConf, table.getProperties(), null);

      this.deserializer = serDe;

      ObjectInspector inspector = deserializer.getObjectInspector();
      LOG.debug("Type of obj insp: " + inspector.getTypeName());

      soi = (StructObjectInspector) inspector;
      List<? extends StructField> fields = soi.getAllStructFieldRefs();
      if (fields.size() > 1) {
        LOG.error("expecting single field in input");
      }

      field = fields.get(0);

      fieldInspector =
          ObjectInspectorUtils.getStandardObjectInspector(field.getFieldObjectInspector());
    }

};