//,temp,SemanticAnalyzer.java,8522,8608,temp,CalcitePlanner.java,5270,5370
//,3
public class xxx {
    private RelNode genUDTFPlan(GenericUDTF genericUDTF, String genericUDTFName, String outputTableAlias,
        ArrayList<String> colAliases, QB qb, List<RexNode> selectColLst, RowResolver selectRR, RelNode input) throws SemanticException {

      // No GROUP BY / DISTRIBUTE BY / SORT BY / CLUSTER BY
      QBParseInfo qbp = qb.getParseInfo();
      if (!qbp.getDestToGroupBy().isEmpty()) {
        throw new SemanticException(ErrorMsg.UDTF_NO_GROUP_BY.getMsg());
      }
      if (!qbp.getDestToDistributeBy().isEmpty()) {
        throw new SemanticException(ErrorMsg.UDTF_NO_DISTRIBUTE_BY.getMsg());
      }
      if (!qbp.getDestToSortBy().isEmpty()) {
        throw new SemanticException(ErrorMsg.UDTF_NO_SORT_BY.getMsg());
      }
      if (!qbp.getDestToClusterBy().isEmpty()) {
        throw new SemanticException(ErrorMsg.UDTF_NO_CLUSTER_BY.getMsg());
      }
      if (!qbp.getAliasToLateralViews().isEmpty()) {
        throw new SemanticException(ErrorMsg.UDTF_LATERAL_VIEW.getMsg());
      }

      LOG.debug("Table alias: " + outputTableAlias + " Col aliases: " + colAliases);

      // Create the return type info for the input columns and initialize the
      // UDTF
      StructTypeInfo type = (StructTypeInfo) TypeConverter.convert(
          functionHelper.getReturnType(
              functionHelper.getFunctionInfo(genericUDTFName),
              selectColLst));

      int numUdtfCols = type.getAllStructFieldNames().size();
      if (colAliases.isEmpty()) {
        // user did not specfied alias names, infer names from outputOI
        for (String fieldName : type.getAllStructFieldNames()) {
          colAliases.add(fieldName);
        }
      }
      // Make sure that the number of column aliases in the AS clause matches
      // the number of columns output by the UDTF
      int numSuppliedAliases = colAliases.size();
      if (numUdtfCols != numSuppliedAliases) {
        throw new SemanticException(ErrorMsg.UDTF_ALIAS_MISMATCH.getMsg("expected " + numUdtfCols
            + " aliases " + "but got " + numSuppliedAliases));
      }

      // Generate the output column info's / row resolver using internal names.
      List<ColumnInfo> udtfCols = new ArrayList<ColumnInfo>();

      Iterator<String> colAliasesIter = colAliases.iterator();
      for (int i = 0; i < type.getAllStructFieldTypeInfos().size(); i++) {
        final String fieldName = type.getAllStructFieldNames().get(i);
        final TypeInfo fieldTypeInfo = type.getAllStructFieldTypeInfos().get(i);

        String colAlias = colAliasesIter.next();
        assert (colAlias != null);

        // Since the UDTF operator feeds into a LVJ operator that will rename
        // all the internal names, we can just use field name from the UDTF's OI
        // as the internal name
        ColumnInfo col = new ColumnInfo(fieldName, fieldTypeInfo, outputTableAlias, false);
        udtfCols.add(col);
      }

      // Create the row resolver for this operator from the output columns
      RowResolver outputRR = new RowResolver();
      for (int i = 0; i < udtfCols.size(); i++) {
        outputRR.put(outputTableAlias, colAliases.get(i), udtfCols.get(i));
      }

      // Add the UDTFOperator to the operator DAG
      RelTraitSet traitSet = TraitsUtil.getDefaultTraitSet(cluster);

      // Build row type from field <type, name>
      RelDataType retType = TypeConverter.getType(cluster, outputRR, null);

      Builder<RelDataType> argTypeBldr = ImmutableList.<RelDataType> builder();

      RexBuilder rexBuilder = cluster.getRexBuilder();
      RelDataTypeFactory dtFactory = rexBuilder.getTypeFactory();
      RowSchema rs = selectRR.getRowSchema();
      for (ColumnInfo ci : rs.getSignature()) {
        argTypeBldr.add(TypeConverter.convert(ci.getType(), dtFactory));
      }

      SqlOperator calciteOp = SqlFunctionConverter.getCalciteOperator(genericUDTFName, genericUDTF,
             argTypeBldr.build(), retType);

      // Hive UDTF only has a single input
      List<RelNode> list = new ArrayList<>();
      list.add(input);

      RexNode rexNode = cluster.getRexBuilder().makeCall(calciteOp, selectColLst);

      RelNode udtf = HiveTableFunctionScan.create(cluster, traitSet, list, rexNode, null, retType,
          null);
      // Add new rel & its RR to the maps
      relToHiveColNameCalcitePosMap.put(udtf, buildHiveToCalciteColumnMap(outputRR));
      relToHiveRR.put(udtf, outputRR);

      return udtf;
    }

};