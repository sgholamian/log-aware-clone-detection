//,temp,TestRSGroupsAdmin2.java,416,461,temp,TestRSGroupsAdmin2.java,169,220
//,3
public class xxx {
  @Test
  public void testMoveServersFromDefaultGroup() throws Exception {
    // create groups and assign servers
    rsGroupAdmin.addRSGroup("foo");

    RSGroupInfo fooGroup = rsGroupAdmin.getRSGroupInfo("foo");
    assertEquals(0, fooGroup.getServers().size());
    RSGroupInfo defaultGroup = rsGroupAdmin.getRSGroupInfo(RSGroupInfo.DEFAULT_GROUP);

    // test remove all servers from default
    try {
      rsGroupAdmin.moveServers(defaultGroup.getServers(), fooGroup.getName());
      fail(RSGroupAdminServer.KEEP_ONE_SERVER_IN_DEFAULT_ERROR_MESSAGE);
    } catch (ConstraintException ex) {
      assertTrue(
        ex.getMessage().contains(RSGroupAdminServer.KEEP_ONE_SERVER_IN_DEFAULT_ERROR_MESSAGE));
    }

    // test success case, remove one server from default ,keep at least one server
    if (defaultGroup.getServers().size() > 1) {
      Address serverInDefaultGroup = defaultGroup.getServers().iterator().next();
      LOG.info("moving server " + serverInDefaultGroup + " from group default to group " +
        fooGroup.getName());
      rsGroupAdmin.moveServers(Sets.newHashSet(serverInDefaultGroup), fooGroup.getName());
    }

    fooGroup = rsGroupAdmin.getRSGroupInfo("foo");
    LOG.info("moving servers " + fooGroup.getServers() + " to group default");
    rsGroupAdmin.moveServers(fooGroup.getServers(), RSGroupInfo.DEFAULT_GROUP);

    TEST_UTIL.waitFor(WAIT_TIMEOUT, new Waiter.Predicate<Exception>() {
      @Override
      public boolean evaluate() throws Exception {
        return getNumServers() == rsGroupAdmin.getRSGroupInfo(RSGroupInfo.DEFAULT_GROUP)
          .getServers().size();
      }
    });

    fooGroup = rsGroupAdmin.getRSGroupInfo("foo");
    assertEquals(0, fooGroup.getServers().size());

    // test group removal
    LOG.info("Remove group " + fooGroup.getName());
    rsGroupAdmin.removeRSGroup(fooGroup.getName());
    Assert.assertEquals(null, rsGroupAdmin.getRSGroupInfo(fooGroup.getName()));
  }

};