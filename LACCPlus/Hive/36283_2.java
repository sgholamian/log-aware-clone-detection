//,temp,SemanticAnalyzer.java,4490,4747,temp,CalcitePlanner.java,4917,5255
//,3
public class xxx {
    private Pair<RelNode, RowResolver> internalGenSelectLogicalPlan(QB qb, RelNode srcRel, RelNode starSrcRel,
        ImmutableMap<String, Integer> outerNameToPosMap, RowResolver outerRR, boolean isAllColRefRewrite)
        throws SemanticException {
      // 0. Generate a Select Node for Windowing
      // Exclude the newly-generated select columns from */etc. resolution.
      HashSet<ColumnInfo> excludedColumns = new HashSet<ColumnInfo>();
      RelNode selForWindow = genSelectForWindowing(qb, srcRel, excludedColumns);
      srcRel = (selForWindow == null) ? srcRel : selForWindow;

      List<RexNode> columnList = new ArrayList<>();

      // 1. Get Select Expression List
      QBParseInfo qbp = getQBParseInfo(qb);
      String selClauseName = qbp.getClauseNames().iterator().next();
      ASTNode selExprList = qbp.getSelForClause(selClauseName);

      // make sure if there is subquery it is top level expression
      SubQueryUtils.checkForTopLevelSubqueries(selExprList);

      final boolean cubeRollupGrpSetPresent = (!qbp.getDestRollups().isEmpty()
              || !qbp.getDestGroupingSets().isEmpty() || !qbp.getDestCubes().isEmpty());

      // 2.Row resolvers for input, output
      RowResolver outputRR = new RowResolver();
      Integer pos = Integer.valueOf(0);
      // TODO: will this also fix windowing? try
      RowResolver inputRR = this.relToHiveRR.get(srcRel), starRR = inputRR;
      inputRR.setCheckForAmbiguity(true);
      if (starSrcRel != null) {
        starRR = this.relToHiveRR.get(starSrcRel);
      }

      // 3. Query Hints
      // TODO: Handle Query Hints; currently we ignore them
      int posn = 0;
      boolean hintPresent = (selExprList.getChild(0).getType() == HiveParser.QUERY_HINT);
      if (hintPresent) {
        posn++;
      }

      // 4. Bailout if select involves Transform
      boolean isInTransform = (selExprList.getChild(posn).getChild(0).getType() == HiveParser.TOK_TRANSFORM);
      if (isInTransform) {
        String msg = String.format("SELECT TRANSFORM is currently not supported in CBO,"
            + " turn off cbo to use TRANSFORM.");
        LOG.debug(msg);
        throw new CalciteSemanticException(msg, UnsupportedFeature.Select_transform);
      }

      // 5. Check if select involves UDTF
      String udtfTableAlias = null;
      GenericUDTF genericUDTF = null;
      String genericUDTFName = null;
      ArrayList<String> udtfColAliases = new ArrayList<String>();
      ASTNode expr = (ASTNode) selExprList.getChild(posn).getChild(0);
      int exprType = expr.getType();
      if (exprType == HiveParser.TOK_FUNCTION || exprType == HiveParser.TOK_FUNCTIONSTAR) {
        String funcName = TypeCheckProcFactory.getFunctionText(expr, true);
        FunctionInfo fi = FunctionRegistry.getFunctionInfo(funcName);
        if (fi != null && fi.getGenericUDTF() != null) {
          LOG.debug("Find UDTF " + funcName);
          genericUDTF = fi.getGenericUDTF();
          genericUDTFName = funcName;
          if (!fi.isNative()) {
            unparseTranslator.addIdentifierTranslation((ASTNode) expr.getChild(0));
          }
          if (genericUDTF != null && exprType == HiveParser.TOK_FUNCTIONSTAR) {
            genRexNodeRegex(".*", null, (ASTNode) expr.getChild(0),
                columnList, null, inputRR, starRR, pos, outputRR, qb.getAliases(), false);
          }
        }
      }

      if (genericUDTF != null) {
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
            throw new SemanticException("Find invalid token type " + selExprChild.getType()
                + " in UDTF.");
          }
        }
        LOG.debug("UDTF table alias is " + udtfTableAlias);
        LOG.debug("UDTF col aliases are " + udtfColAliases);
      }

      // 6. Iterate over all expression (after SELECT)
      ASTNode exprList;
      if (genericUDTF != null) {
        exprList = expr;
      } else {
        exprList = selExprList;
      }
      // For UDTF's, skip the function name to get the expressions
      int startPosn = genericUDTF != null ? posn + 1 : posn;
      for (int i = startPosn; i < exprList.getChildCount(); ++i) {

        // 6.1 child can be EXPR AS ALIAS, or EXPR.
        ASTNode child = (ASTNode) exprList.getChild(i);
        boolean hasAsClause = (!isInTransform) && (child.getChildCount() == 2);

        // 6.2 EXPR AS (ALIAS,...) parses, but is only allowed for UDTF's
        // This check is not needed and invalid when there is a transform b/c
        // the
        // AST's are slightly different.
        if (genericUDTF == null && child.getChildCount() > 2) {
          throw new SemanticException(SemanticAnalyzer.generateErrorMessage(
              (ASTNode) child.getChild(2), ErrorMsg.INVALID_AS.getMsg()));
        }

        String tabAlias;
        String colAlias;

        if (genericUDTF != null) {
          tabAlias = null;
          colAlias = getAutogenColAliasPrfxLbl() + i;
          expr = child;
        } else {
          // 6.3 Get rid of TOK_SELEXPR
          expr = (ASTNode) child.getChild(0);
          String[] colRef = getColAlias(child, getAutogenColAliasPrfxLbl(),
                  inputRR, autogenColAliasPrfxIncludeFuncName(), i);
          tabAlias = colRef[0];
          colAlias = colRef[1];
          if (hasAsClause) {
            unparseTranslator.addIdentifierTranslation((ASTNode) child
                    .getChild(1));
          }
        }

        Map<ASTNode, QBSubQueryParseInfo> subQueryToRelNode = new HashMap<>();
        boolean isSubQuery = genSubQueryRelNode(qb, expr, srcRel, false,
                subQueryToRelNode);
        if(isSubQuery) {
          RexNode subQueryExpr = genRexNode(expr, relToHiveRR.get(srcRel),
                  outerRR, subQueryToRelNode, true, cluster.getRexBuilder());
          columnList.add(subQueryExpr);
          ColumnInfo colInfo = new ColumnInfo(SemanticAnalyzer.getColumnInternalName(pos),
              TypeInfoUtils.getStandardWritableObjectInspectorFromTypeInfo(
                  TypeConverter.convert(subQueryExpr.getType())),
              tabAlias, false);
          if (!outputRR.putWithCheck(tabAlias, colAlias, null, colInfo)) {
            throw new CalciteSemanticException("Cannot add column to RR: " + tabAlias + "."
                    + colAlias + " => " + colInfo + " due to duplication, see previous warnings",
                    UnsupportedFeature.Duplicates_in_RR);
          }
          pos = Integer.valueOf(pos.intValue() + 1);
        } else {

          // 6.4 Build ExprNode corresponding to colums
          if (expr.getType() == HiveParser.TOK_ALLCOLREF) {
            pos = genRexNodeRegex(".*",
                expr.getChildCount() == 0 ? null : getUnescapedName((ASTNode) expr.getChild(0)).toLowerCase(),
                expr, columnList, excludedColumns, inputRR, starRR, pos, outputRR, qb.getAliases(), true);
          } else if (expr.getType() == HiveParser.TOK_TABLE_OR_COL
                  && !hasAsClause
                  && !inputRR.getIsExprResolver()
                  && isRegex(
                  unescapeIdentifier(expr.getChild(0).getText()), conf)) {
            // In case the expression is a regex COL.
            // This can only happen without AS clause
            // We don't allow this for ExprResolver - the Group By case
            pos = genRexNodeRegex(unescapeIdentifier(expr.getChild(0).getText()), null,
                expr, columnList, excludedColumns, inputRR, starRR, pos, outputRR, qb.getAliases(), true);
          } else if (expr.getType() == HiveParser.DOT
                  && expr.getChild(0).getType() == HiveParser.TOK_TABLE_OR_COL
                  && inputRR.hasTableAlias(unescapeIdentifier(expr.getChild(0)
                  .getChild(0).getText().toLowerCase()))
                  && !hasAsClause
                  && !inputRR.getIsExprResolver()
                  && isRegex(
                  unescapeIdentifier(expr.getChild(1).getText()), conf)) {
            // In case the expression is TABLE.COL (col can be regex).
            // This can only happen without AS clause
            // We don't allow this for ExprResolver - the Group By case
            pos = genRexNodeRegex(
                    unescapeIdentifier(expr.getChild(1).getText()),
                    unescapeIdentifier(expr.getChild(0).getChild(0).getText().toLowerCase()),
                    expr, columnList, excludedColumns, inputRR, starRR, pos,
                    outputRR, qb.getAliases(), true);
          } else if (ParseUtils.containsTokenOfType(expr, HiveParser.TOK_FUNCTIONDI) &&
              !ParseUtils.containsTokenOfType(expr, HiveParser.TOK_WINDOWSPEC) &&
              !(srcRel instanceof HiveAggregate ||
              (srcRel.getInputs().size() == 1 && srcRel.getInput(0) instanceof HiveAggregate))) {
            // Likely a malformed query eg, select hash(distinct c1) from t1;
            throw new CalciteSemanticException("Distinct without an aggregation.",
                    UnsupportedFeature.Distinct_without_an_aggreggation);
          } else {
            // Case when this is an expression
            TypeCheckCtx tcCtx = new TypeCheckCtx(inputRR, cluster.getRexBuilder());
            // We allow stateful functions in the SELECT list (but nowhere else)
            tcCtx.setAllowStatefulFunctions(true);
            if (!qbp.getDestToGroupBy().isEmpty()) {
              // Special handling of grouping function
              expr = rewriteGroupingFunctionAST(getGroupByForClause(qbp, selClauseName), expr,
                      !cubeRollupGrpSetPresent);
            }
            RexNode expression = genRexNode(expr, inputRR, tcCtx);

            String recommended = recommendName(expression, colAlias, inputRR);
            if (recommended != null && outputRR.get(null, recommended) == null) {
              colAlias = recommended;
            }
            columnList.add(expression);

            TypeInfo typeInfo = expression.isA(SqlKind.LITERAL) ?
                TypeConverter.convertLiteralType((RexLiteral) expression) :
                TypeConverter.convert(expression.getType());
            ColumnInfo colInfo = new ColumnInfo(SemanticAnalyzer.getColumnInternalName(pos),
                TypeInfoUtils.getStandardWritableObjectInspectorFromTypeInfo(typeInfo),
                tabAlias, false);
            outputRR.put(tabAlias, colAlias, colInfo);

            pos = Integer.valueOf(pos.intValue() + 1);
          }
        }
      }

      // 7. For correlated queries
      ImmutableMap<String, Integer> hiveColNameCalcitePosMap =
          buildHiveColNameToInputPosMap(columnList, inputRR);
      CorrelationConverter cc = new CorrelationConverter(
          new InputContext(srcRel.getRowType(), hiveColNameCalcitePosMap, relToHiveRR.get(srcRel)),
          outerNameToPosMap, outerRR, subqueryId);
      columnList = columnList.stream()
          .map(cc::apply)
          .collect(Collectors.toList());

      // 8. Build Calcite Rel
      RelNode outputRel = null;
      if (genericUDTF != null) {
        // The basic idea for CBO support of UDTF is to treat UDTF as a special
        // project.
        // In AST return path, as we just need to generate a SEL_EXPR, we just
        // need to remember the expressions and the alias.
        // In OP return path, we need to generate a SEL and then a UDTF
        // following old semantic analyzer.
        outputRel = genUDTFPlan(genericUDTF, genericUDTFName, udtfTableAlias, udtfColAliases, qb,
            columnList, outputRR, srcRel);
      } else {
        String dest = qbp.getClauseNames().iterator().next();
        ASTNode obAST = qbp.getOrderByForClause(dest);
        ASTNode sbAST = qbp.getSortByForClause(dest);

        RowResolver originalRR = null;
        // We only support limited unselected column following by order by.
        // TODO: support unselected columns in genericUDTF and windowing functions.
        // We examine the order by in this query block and adds in column needed
        // by order by in select list.
        //
        // If DISTINCT is present, it is not possible to ORDER BY unselected
        // columns, and in fact adding all columns would change the behavior of
        // DISTINCT, so we bypass this logic.
        if ((obAST != null || sbAST != null)
            && selExprList.getToken().getType() != HiveParser.TOK_SELECTDI
            && !isAllColRefRewrite) {
          // 1. OB Expr sanity test
          // in strict mode, in the presence of order by, limit must be
          // specified
          Integer limit = qb.getParseInfo().getDestLimit(dest);
          if (limit == null) {
            String error = StrictChecks.checkNoLimit(conf);
            if (error != null) {
              throw new SemanticException(SemanticAnalyzer.generateErrorMessage(obAST, error));
            }
          }
          List<RexNode> originalInputRefs = Lists.transform(srcRel.getRowType().getFieldList(),
              new Function<RelDataTypeField, RexNode>() {
                @Override
                public RexNode apply(RelDataTypeField input) {
                  return new RexInputRef(input.getIndex(), input.getType());
                }
              });
          originalRR = outputRR.duplicate();
          for (int i = 0; i < inputRR.getColumnInfos().size(); i++) {
            ColumnInfo colInfo = new ColumnInfo(inputRR.getColumnInfos().get(i));
            String internalName = SemanticAnalyzer.getColumnInternalName(outputRR.getColumnInfos()
                .size() + i);
            colInfo.setInternalName(internalName);
            // if there is any confict, then we do not generate it in the new select
            // otherwise, we add it into the calciteColLst and generate the new select
            if (!outputRR.putWithCheck(colInfo.getTabAlias(), colInfo.getAlias(), internalName,
                colInfo)) {
              LOG.trace("Column already present in RR. skipping.");
            } else {
              columnList.add(originalInputRefs.get(i));
            }
          }
          outputRel = genSelectRelNode(columnList, outputRR, srcRel);
          // outputRel is the generated augmented select with extra unselected
          // columns, and originalRR is the original generated select
          return new Pair<RelNode, RowResolver>(outputRel, originalRR);
        } else {
          outputRel = genSelectRelNode(columnList, outputRR, srcRel);
        }
      }
      // 9. Handle select distinct as GBY if there exist windowing functions
      if (selForWindow != null && selExprList.getToken().getType() == HiveParser.TOK_SELECTDI) {
        ImmutableBitSet groupSet = ImmutableBitSet.range(outputRel.getRowType().getFieldList().size());
        outputRel = new HiveAggregate(cluster, cluster.traitSetOf(HiveRelNode.CONVENTION),
              outputRel, groupSet, null, new ArrayList<AggregateCall>());
        RowResolver groupByOutputRowResolver = new RowResolver();
        for (int i = 0; i < outputRR.getColumnInfos().size(); i++) {
          ColumnInfo colInfo = outputRR.getColumnInfos().get(i);
          ColumnInfo newColInfo = new ColumnInfo(colInfo.getInternalName(),
              colInfo.getType(), colInfo.getTabAlias(), colInfo.getIsVirtualCol());
          groupByOutputRowResolver.put(colInfo.getTabAlias(), colInfo.getAlias(), newColInfo);
        }
        relToHiveColNameCalcitePosMap.put(outputRel, buildHiveToCalciteColumnMap(groupByOutputRowResolver));
        this.relToHiveRR.put(outputRel, groupByOutputRowResolver);
      }

      inputRR.setCheckForAmbiguity(false);
      return new Pair<RelNode, RowResolver>(outputRel, null);
    }

};