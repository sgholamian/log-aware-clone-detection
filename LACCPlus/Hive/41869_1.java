//,temp,JDBCExpandExpressionsRule.java,97,120,temp,JDBCExpandExpressionsRule.java,70,87
//,3
public class xxx {
    @Override
    public void onMatch(RelOptRuleCall call) {
      LOG.debug("JDBCExpandExpressionsRule.JoinCondition has been called");

      final Join join = call.rel(0);
      final RexNode condition = RexUtil.pullFactors(
          join.getCluster().getRexBuilder(), join.getCondition());

      RexNode newCondition = analyzeRexNode(
          join.getCluster().getRexBuilder(), condition);

      // If we could not transform anything, we bail out
      if (newCondition.toString().equals(condition.toString())) {
        return;
      }

      RelNode newNode = join.copy(join.getTraitSet(),
          newCondition,
          join.getLeft(),
          join.getRight(),
          join.getJoinType(),
          join.isSemiJoinDone());
      call.transformTo(newNode);
    }

};