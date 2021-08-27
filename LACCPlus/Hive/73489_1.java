//,temp,RelOptHiveTable.java,256,311,temp,CalcitePlanner.java,3516,3575
//,3
public class xxx {
  private Pair<List<ImmutableBitSet>, List<ImmutableBitSet>> generateKeys() {
    final PrimaryKeyInfo primaryKeyInfo = hiveTblMetadata.getPrimaryKeyInfo();
    final UniqueConstraint uniqueKeyInfo = hiveTblMetadata.getUniqueKeyInfo();
    ImmutableList.Builder<ImmutableBitSet> builder = ImmutableList.builder();
    ImmutableList.Builder<ImmutableBitSet> nonNullbuilder = ImmutableList.builder();
    // First PK
    if (primaryKeyInfo != null && !primaryKeyInfo.getColNames().isEmpty()) {
      ImmutableBitSet.Builder keys = ImmutableBitSet.builder();
      for (String pkColName : primaryKeyInfo.getColNames().values()) {
        int pkPos;
        for (pkPos = 0; pkPos < rowType.getFieldNames().size(); pkPos++) {
          String colName = rowType.getFieldNames().get(pkPos);
          if (pkColName.equals(colName)) {
            break;
          }
        }
        if (pkPos == rowType.getFieldNames().size()) {
          LOG.error("Column for primary key definition " + pkColName + " not found");
        }
        keys.set(pkPos);
      }
      ImmutableBitSet key = keys.build();
      builder.add(key);
      nonNullbuilder.add(key);
    }
    // Then UKs
    if (uniqueKeyInfo != null && !uniqueKeyInfo.getUniqueConstraints().isEmpty()) {
      for (List<UniqueConstraintCol> ukCols : uniqueKeyInfo.getUniqueConstraints().values()) {
        ImmutableBitSet.Builder keys = ImmutableBitSet.builder();
        boolean isNonNullable = true;
        for (UniqueConstraintCol ukCol : ukCols) {
          int ukPos;
          for (ukPos = 0; ukPos < rowType.getFieldNames().size(); ukPos++) {
            String colName = rowType.getFieldNames().get(ukPos);
            if (ukCol.colName.equals(colName)) {
              if (rowType.getFieldList().get(ukPos).getType().isNullable()) {
                // they should all be nullable
                isNonNullable = false;
              }
              break;
            }
          }
          if (ukPos == rowType.getFieldNames().size()) {
            LOG.error("Column for unique constraint definition " + ukCol.colName + " not found");
          }
          keys.set(ukPos);
        }
        ImmutableBitSet key = keys.build();
        builder.add(key);
        if (isNonNullable) {
          nonNullbuilder.add(key);
        }
      }
    }
    return new Pair<>(builder.build(), nonNullbuilder.build());
  }

};