//,temp,JDBCExpandExpressionsRule.java,97,120,temp,JDBCExpandExpressionsRule.java,70,87
//,3
public class xxx {
    @Override
    public void onMatch(RelOptRuleCall call) {
      LOG.debug("JDBCExpandExpressionsRule.FilterCondition has been called");

      final JdbcFilter filter = call.rel(0);
      final RexNode condition = filter.getCondition();

      RexNode newCondition = analyzeRexNode(
          filter.getCluster().getRexBuilder(), condition);

      // If we could not transform anything, we bail out
      if (newCondition.toString().equals(condition.toString())) {
        return;
      }

      RelNode newNode = filter.copy(filter.getTraitSet(), filter.getInput(), newCondition);
      call.transformTo(newNode);
    }

};