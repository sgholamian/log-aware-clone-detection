//,temp,JDBCFilterPushDownRule.java,62,76,temp,JDBCUnionPushDownRule.java,74,90
//,3
public class xxx {
  @Override
  public void onMatch(RelOptRuleCall call) {
    LOG.debug("JDBCFilterPushDown has been called");

    final HiveFilter filter = call.rel(0);
    final HiveJdbcConverter converter = call.rel(1);

    Filter newHiveFilter = filter.copy(filter.getTraitSet(), converter.getInput(), filter.getCondition());
    JdbcFilter newJdbcFilter = (JdbcFilter) new JdbcFilterRule(converter.getJdbcConvention()).convert(newHiveFilter);
    if (newJdbcFilter != null) {
      RelNode converterRes = converter.copy(converter.getTraitSet(), Arrays.asList(newJdbcFilter));

      call.transformTo(converterRes);
    }
  }

};