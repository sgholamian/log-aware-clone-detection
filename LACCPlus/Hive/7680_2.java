//,temp,JDBCFilterPushDownRule.java,62,76,temp,JDBCUnionPushDownRule.java,74,90
//,3
public class xxx {
  @Override
  public void onMatch(RelOptRuleCall call) {
    LOG.debug("JDBCUnionPushDown has been called");

    final HiveUnion union = call.rel(0);
    final HiveJdbcConverter converter1 = call.rel(1);
    final HiveJdbcConverter converter2 = call.rel(2);

    List<RelNode> unionInput = Arrays.asList(converter1.getInput(), converter2.getInput());
    JdbcUnion jdbcUnion = new JdbcUnion(
        union.getCluster(),
        union.getTraitSet().replace(converter1.getJdbcConvention()),
        unionInput,
        union.all);

    call.transformTo(converter1.copy(converter1.getTraitSet(), jdbcUnion));
  }

};