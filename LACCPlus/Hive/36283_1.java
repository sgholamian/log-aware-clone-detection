//,temp,SemanticAnalyzer.java,4490,4747,temp,CalcitePlanner.java,4917,5255
//,3
public class xxx {
  @SuppressWarnings("nls")
  private Operator<?> genSelectPlan(String dest, ASTNode selExprList, QB qb, Operator<?> input,
                                    Operator<?> inputForSelectStar, boolean outerLV) throws SemanticException {

    LOG.debug("tree: {}", selExprList.toStringTree());

    List<ExprNodeDesc> colList = new ArrayList<ExprNodeDesc>();
    RowResolver out_rwsch = new RowResolver();
    ASTNode trfm = null;
    Integer pos = 0;
    RowResolver inputRR = opParseCtx.get(input).getRowResolver();
    RowResolver starRR = null;
    if (inputForSelectStar != null && inputForSelectStar != input) {
      starRR = opParseCtx.get(inputForSelectStar).getRowResolver();
    }
    // SELECT * or SELECT TRANSFORM(*)
    boolean selectStar = false;
    int posn = 0;
    boolean hintPresent = (selExprList.getChild(0).getType() == HiveParser.QUERY_HINT);
    if (hintPresent) {
      posn++;
    }

    boolean isInTransform = (selExprList.getChild(posn).getChild(0).getType() ==
        HiveParser.TOK_TRANSFORM);
    if (isInTransform) {
      queryProperties.setUsesScript(true);
      globalLimitCtx.setHasTransformOrUDTF(true);
      trfm = (ASTNode) selExprList.getChild(posn).getChild(0);
    }

    // Detect queries of the form SELECT udtf(col) AS ...
    // by looking for a function as the first child, and then checking to see
    // if the function is a Generic UDTF. It's not as clean as TRANSFORM due to
    // the lack of a special token.
    boolean isUDTF = false;
    String udtfTableAlias = null;
    List<String> udtfColAliases = new ArrayList<String>();
    ASTNode udtfExpr = (ASTNode) selExprList.getChild(posn).getChild(0);
    GenericUDTF genericUDTF = null;

    int udtfExprType = udtfExpr.getType();
    if (udtfExprType == HiveParser.TOK_FUNCTION
        || udtfExprType == HiveParser.TOK_FUNCTIONSTAR) {
      String funcName = TypeCheckProcFactory.getFunctionText(udtfExpr, true);
      FunctionInfo fi = FunctionRegistry.getFunctionInfo(funcName);
      if (fi != null) {
        genericUDTF = fi.getGenericUDTF();
      }
      isUDTF = (genericUDTF != null);
      if (isUDTF) {
        globalLimitCtx.setHasTransformOrUDTF(true);
      }
      if (isUDTF && !fi.isNative()) {
        unparseTranslator.addIdentifierTranslation((ASTNode) udtfExpr
            .getChild(0));
      }
      if (isUDTF && (selectStar = udtfExprType == HiveParser.TOK_FUNCTIONSTAR)) {
        genExprNodeDescRegex(".*", null, (ASTNode) udtfExpr.getChild(0),
            colList, null, inputRR, starRR, pos, out_rwsch, qb.getAliases(), false);
      }
    }

    if (isUDTF) {
      // Only support a single expression when it's a UDTF
      if (selExprList.getChildCount() > 1) {
        throw new SemanticException(generateErrorMessage(
            (ASTNode) selExprList.getChild(1),
            ErrorMsg.UDTF_MULTIPLE_EXPR.getMsg()));
      }

      ASTNode selExpr = (ASTNode) selExprList.getChild(posn);

      // Get the column / table aliases from the expression. Start from 1 as
      // 0 is the TOK_FUNCTION
      // column names also can be inferred from result of UDTF
      for (int i = 1; i < selExpr.getChildCount(); i++) {
        ASTNode selExprChild = (ASTNode) selExpr.getChild(i);
        switch (selExprChild.getType()) {
        case HiveParser.Identifier:
          udtfColAliases.add(unescapeIdentifier(selExprChild.getText().toLowerCase()));
          unparseTranslator.addIdentifierTranslation(selExprChild);
          break;
        case HiveParser.TOK_TABALIAS:
          assert (selExprChild.getChildCount() == 1);
          udtfTableAlias = unescapeIdentifier(selExprChild.getChild(0)
              .getText());
          qb.addAlias(udtfTableAlias);
          unparseTranslator.addIdentifierTranslation((ASTNode) selExprChild
              .getChild(0));
          break;
        default:
          assert (false);
        }
      }
      LOG.debug("UDTF table alias is {}", udtfTableAlias);
      LOG.debug("UDTF col aliases are {}", udtfColAliases);
    }

    // The list of expressions after SELECT or SELECT TRANSFORM.
    ASTNode exprList;
    if (isInTransform) {
      exprList = (ASTNode) trfm.getChild(0);
    } else if (isUDTF) {
      exprList = udtfExpr;
    } else {
      exprList = selExprList;
    }

    LOG.debug("genSelectPlan: input = {} starRr = {}", inputRR, starRR);

    // For UDTF's, skip the function name to get the expressions
    int startPosn = isUDTF ? posn + 1 : posn;
    if (isInTransform) {
      startPosn = 0;
    }

    final boolean cubeRollupGrpSetPresent = (!qb.getParseInfo().getDestRollups().isEmpty()
        || !qb.getParseInfo().getDestGroupingSets().isEmpty()
        || !qb.getParseInfo().getDestCubes().isEmpty());
    Set<String> colAliases = new HashSet<String>();
    int offset = 0;
    // Iterate over all expression (either after SELECT, or in SELECT TRANSFORM)
    for (int i = startPosn; i < exprList.getChildCount(); ++i) {

      // child can be EXPR AS ALIAS, or EXPR.
      ASTNode child = (ASTNode) exprList.getChild(i);
      boolean hasAsClause = (!isInTransform) && (child.getChildCount() == 2);
      boolean isWindowSpec = child.getChildCount() == 3 &&
          child.getChild(2).getType() == HiveParser.TOK_WINDOWSPEC;

      // EXPR AS (ALIAS,...) parses, but is only allowed for UDTF's
      // This check is not needed and invalid when there is a transform b/c the
      // AST's are slightly different.
      if (!isWindowSpec && !isInTransform && !isUDTF && child.getChildCount() > 2) {
        throw new SemanticException(generateErrorMessage(
            (ASTNode) child.getChild(2),
            ErrorMsg.INVALID_AS.getMsg()));
      }

      // The real expression
      ASTNode expr;
      String tabAlias;
      String colAlias;

      if (isInTransform || isUDTF) {
        tabAlias = null;
        colAlias = autogenColAliasPrfxLbl + i;
        expr = child;
      } else {
        // Get rid of TOK_SELEXPR
        expr = (ASTNode) child.getChild(0);
        String[] colRef = getColAlias(child, autogenColAliasPrfxLbl, inputRR,
            autogenColAliasPrfxIncludeFuncName, i + offset);
        tabAlias = colRef[0];
        colAlias = colRef[1];
        if (hasAsClause) {
          unparseTranslator.addIdentifierTranslation((ASTNode) child
              .getChild(1));
        }
      }
      colAliases.add(colAlias);

      // The real expression
      if (expr.getType() == HiveParser.TOK_ALLCOLREF) {
        int initPos = pos;
        pos = genExprNodeDescRegex(".*", expr.getChildCount() == 0 ? null
                : getUnescapedName((ASTNode) expr.getChild(0)).toLowerCase(),
            expr, colList, null, inputRR, starRR, pos, out_rwsch, qb.getAliases(), false);
        if (unparseTranslator.isEnabled()) {
          offset += pos - initPos - 1;
        }
        selectStar = true;
      } else if (expr.getType() == HiveParser.TOK_TABLE_OR_COL && !hasAsClause
          && !inputRR.getIsExprResolver()
          && isRegex(unescapeIdentifier(expr.getChild(0).getText()), conf)) {
        // In case the expression is a regex COL.
        // This can only happen without AS clause
        // We don't allow this for ExprResolver - the Group By case
        pos = genExprNodeDescRegex(unescapeIdentifier(expr.getChild(0).getText()),
            null, expr, colList, null, inputRR, starRR, pos, out_rwsch, qb.getAliases(), false);
      } else if (expr.getType() == HiveParser.DOT
          && expr.getChild(0).getType() == HiveParser.TOK_TABLE_OR_COL
          && inputRR.hasTableAlias(unescapeIdentifier(expr.getChild(0)
          .getChild(0).getText().toLowerCase())) && !hasAsClause
          && !inputRR.getIsExprResolver()
          && isRegex(unescapeIdentifier(expr.getChild(1).getText()), conf)) {
        // In case the expression is TABLE.COL (col can be regex).
        // This can only happen without AS clause
        // We don't allow this for ExprResolver - the Group By case
        pos = genExprNodeDescRegex(unescapeIdentifier(expr.getChild(1).getText()),
            unescapeIdentifier(expr.getChild(0).getChild(0).getText().toLowerCase()),
            expr, colList, null, inputRR, starRR, pos, out_rwsch, qb.getAliases(), false);
      } else {
        // Case when this is an expression
        TypeCheckCtx tcCtx = new TypeCheckCtx(inputRR, true, isCBOExecuted());
        // We allow stateful functions in the SELECT list (but nowhere else)
        tcCtx.setAllowStatefulFunctions(true);
        tcCtx.setAllowDistinctFunctions(false);
        if (!isCBOExecuted() && !qb.getParseInfo().getDestToGroupBy().isEmpty()) {
          // If CBO did not optimize the query, we might need to replace grouping function
          // Special handling of grouping function
          expr = rewriteGroupingFunctionAST(getGroupByForClause(qb.getParseInfo(), dest), expr,
              !cubeRollupGrpSetPresent);
        }
        ExprNodeDesc exp = genExprNodeDesc(expr, inputRR, tcCtx);
        String recommended = recommendName(exp, colAlias);
        if (recommended != null && !colAliases.contains(recommended) &&
            out_rwsch.get(null, recommended) == null) {
          colAlias = recommended;
        }
        colList.add(exp);

        ColumnInfo colInfo = new ColumnInfo(getColumnInternalName(pos),
            exp.getWritableObjectInspector(), tabAlias, false);
        colInfo.setSkewedCol((exp instanceof ExprNodeColumnDesc) && ((ExprNodeColumnDesc) exp)
            .isSkewedCol());
        out_rwsch.put(tabAlias, colAlias, colInfo);

        if ( exp instanceof ExprNodeColumnDesc ) {
          ExprNodeColumnDesc colExp = (ExprNodeColumnDesc) exp;
          String[] altMapping = inputRR.getAlternateMappings(colExp.getColumn());
          if ( altMapping != null ) {
            out_rwsch.put(altMapping[0], altMapping[1], colInfo);
          }
        }

        pos++;
      }
    }
    selectStar = selectStar && exprList.getChildCount() == posn + 1;

    out_rwsch = handleInsertStatementSpec(colList, dest, out_rwsch, qb, selExprList);

    List<String> columnNames = new ArrayList<String>();
    Map<String, ExprNodeDesc> colExprMap = new HashMap<String, ExprNodeDesc>();
    for (int i = 0; i < colList.size(); i++) {
      String outputCol = getColumnInternalName(i);
      colExprMap.put(outputCol, colList.get(i));
      columnNames.add(outputCol);
    }

    Operator output = putOpInsertMap(OperatorFactory.getAndMakeChild(
        new SelectDesc(colList, columnNames, selectStar), new RowSchema(
            out_rwsch.getColumnInfos()), input), out_rwsch);

    output.setColumnExprMap(colExprMap);
    if (isInTransform) {
      output = genScriptPlan(trfm, qb, output);
    }

    if (isUDTF) {
      output = genUDTFPlan(genericUDTF, udtfTableAlias, udtfColAliases, qb, output, outerLV);
    }

    LOG.debug("Created Select Plan row schema: {}", out_rwsch);
    return output;
  }

};