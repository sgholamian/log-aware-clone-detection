//,temp,JDBCJoinPushDownRule.java,80,106,temp,JDBCProjectPushDownRule.java,72,87
//,3
public class xxx {
  @Override
  public void onMatch(RelOptRuleCall call) {
    LOG.debug("JDBCJoinPushDownRule has been called");

    final HiveJoin join = call.rel(0);
    final HiveJdbcConverter converter1 = call.rel(1);
    final RelNode input1 = converter1.getInput();
    final HiveJdbcConverter converter2 = call.rel(2);
    final RelNode input2 = converter2.getInput();

    JdbcJoin jdbcJoin;
    try {
      jdbcJoin = new JdbcJoin(
          join.getCluster(),
          join.getTraitSet().replace(converter1.getJdbcConvention()),
          input1,
          input2,
          join.getCondition(),
          join.getVariablesSet(),
          join.getJoinType());
    } catch (InvalidRelException e) {
      LOG.warn(e.toString());
      return;
    }

    call.transformTo(converter1.copy(converter1.getTraitSet(), jdbcJoin));
  }

};