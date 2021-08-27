//,temp,RelOptHiveTable.java,256,311,temp,CalcitePlanner.java,3516,3575
//,3
public class xxx {
    private RelDataType inferNotNullableColumns(Table tabMetaData, RelDataType rowType)
        throws HiveException {
      final NotNullConstraint nnc = tabMetaData.getNotNullConstraint();
      final PrimaryKeyInfo pkc = tabMetaData.getPrimaryKeyInfo();
      if ((nnc == null || nnc.getNotNullConstraints().isEmpty()) &&
          (pkc == null || pkc.getColNames().isEmpty())) {
        return rowType;
      }

      // Build the bitset with not null columns
      ImmutableBitSet.Builder builder = ImmutableBitSet.builder();
      if (nnc != null) {
        for (String nnCol : nnc.getNotNullConstraints().values()) {
          int nnPos = -1;
          for (int i = 0; i < rowType.getFieldNames().size(); i++) {
            if (rowType.getFieldNames().get(i).equals(nnCol)) {
              nnPos = i;
              break;
            }
          }
          if (nnPos == -1) {
            LOG.error("Column for not null constraint definition " + nnCol + " not found");
            return rowType;
          }
          builder.set(nnPos);
        }
      }
      if (pkc != null) {
        for (String pkCol : pkc.getColNames().values()) {
          int pkPos = -1;
          for (int i = 0; i < rowType.getFieldNames().size(); i++) {
            if (rowType.getFieldNames().get(i).equals(pkCol)) {
              pkPos = i;
              break;
            }
          }
          if (pkPos == -1) {
            LOG.error("Column for not null constraint definition " + pkCol + " not found");
            return rowType;
          }
          builder.set(pkPos);
        }
      }
      ImmutableBitSet bitSet = builder.build();

      RexBuilder rexBuilder = cluster.getRexBuilder();
      RelDataTypeFactory dtFactory = rexBuilder.getTypeFactory();

      List<RelDataType> fieldTypes = new LinkedList<RelDataType>();
      List<String> fieldNames = new LinkedList<String>();
      for (RelDataTypeField rdtf : rowType.getFieldList()) {
        if (bitSet.indexOf(rdtf.getIndex()) != -1) {
          fieldTypes.add(dtFactory.createTypeWithNullability(rdtf.getType(), false));
        } else {
          fieldTypes.add(rdtf.getType());
        }
        fieldNames.add(rdtf.getName());
      }
      return dtFactory.createStructType(fieldTypes, fieldNames);
    }

};