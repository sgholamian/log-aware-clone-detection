//,temp,JDBCJoinPushDownRule.java,80,106,temp,JDBCProjectPushDownRule.java,72,87
//,3
public class xxx {
  @Override
  public void onMatch(RelOptRuleCall call) {
    LOG.debug("JDBCProjectPushDownRule has been called");

    final HiveProject project = call.rel(0);
    final HiveJdbcConverter converter = call.rel(1);

    JdbcProject jdbcProject = new JdbcProject(
        project.getCluster(),
        project.getTraitSet().replace(converter.getJdbcConvention()),
        converter.getInput(),
        project.getProjects(),
        project.getRowType());

    call.transformTo(converter.copy(converter.getTraitSet(), jdbcProject));
  }

};