//,temp,SemanticAnalyzer.java,8522,8608,temp,CalcitePlanner.java,5270,5370
//,3
public class xxx {
  private Operator genUDTFPlan(GenericUDTF genericUDTF, String outputTableAlias, List<String> colAliases, QB qb,
      Operator input, boolean outerLV) throws SemanticException {

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

    LOG.debug("Table alias: {} Col aliases: {}", outputTableAlias, colAliases);

    // Use the RowResolver from the input operator to generate a input
    // ObjectInspector that can be used to initialize the UDTF. Then, the

    // resulting output object inspector can be used to make the RowResolver
    // for the UDTF operator
    RowResolver selectRR = opParseCtx.get(input).getRowResolver();
    List<ColumnInfo> inputCols = selectRR.getColumnInfos();

    // Create the object inspector for the input columns and initialize the UDTF
    List<String> colNames = new ArrayList<String>();
    ObjectInspector[] colOIs = new ObjectInspector[inputCols.size()];
    for (int i = 0; i < inputCols.size(); i++) {
      colNames.add(inputCols.get(i).getInternalName());
      colOIs[i] = inputCols.get(i).getObjectInspector();
    }
    StandardStructObjectInspector rowOI =
        ObjectInspectorFactory.getStandardStructObjectInspector(colNames, Arrays.asList(colOIs));
    StructObjectInspector outputOI = genericUDTF.initialize(rowOI);

    int numUdtfCols = outputOI.getAllStructFieldRefs().size();
    if (colAliases.isEmpty()) {
      // user did not specfied alias names, infer names from outputOI
      for (StructField field : outputOI.getAllStructFieldRefs()) {
        colAliases.add(field.getFieldName());
      }
    }
    // Make sure that the number of column aliases in the AS clause matches
    // the number of columns output by the UDTF
    int numSuppliedAliases = colAliases.size();
    if (numUdtfCols != numSuppliedAliases) {
      throw new SemanticException(ErrorMsg.UDTF_ALIAS_MISMATCH
          .getMsg("expected " + numUdtfCols + " aliases " + "but got "
              + numSuppliedAliases));
    }

    // Generate the output column info's / row resolver using internal names.
    List<ColumnInfo> udtfCols = new ArrayList<ColumnInfo>();

    Iterator<String> colAliasesIter = colAliases.iterator();
    for (StructField sf : outputOI.getAllStructFieldRefs()) {

      String colAlias = colAliasesIter.next();
      assert (colAlias != null);

      // Since the UDTF operator feeds into a LVJ operator that will rename
      // all the internal names, we can just use field name from the UDTF's OI
      // as the internal name
      ColumnInfo col = new ColumnInfo(sf.getFieldName(), TypeInfoUtils
          .getTypeInfoFromObjectInspector(sf.getFieldObjectInspector()),
          outputTableAlias, false);
      udtfCols.add(col);
    }

    // Create the row resolver for this operator from the output columns
    RowResolver out_rwsch = new RowResolver();
    for (int i = 0; i < udtfCols.size(); i++) {
      out_rwsch.put(outputTableAlias, colAliases.get(i), udtfCols.get(i));
    }

    // Add the UDTFOperator to the operator DAG
    return putOpInsertMap(OperatorFactory.getAndMakeChild(
        new UDTFDesc(genericUDTF, outerLV), new RowSchema(out_rwsch.getColumnInfos()),
        input), out_rwsch);
  }

};