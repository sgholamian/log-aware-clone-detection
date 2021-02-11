//,temp,TestRSGroupsAdmin2.java,416,461,temp,TestRSGroupsAdmin2.java,169,220
//,3
public class xxx {
  @Test
  public void testMoveServers() throws Exception {
    // create groups and assign servers
    addGroup("bar", 3);
    rsGroupAdmin.addRSGroup("foo");

    RSGroupInfo barGroup = rsGroupAdmin.getRSGroupInfo("bar");
    RSGroupInfo fooGroup = rsGroupAdmin.getRSGroupInfo("foo");
    assertEquals(3, barGroup.getServers().size());
    assertEquals(0, fooGroup.getServers().size());

    // test fail bogus server move
    try {
      rsGroupAdmin.moveServers(Sets.newHashSet(Address.fromString("foo:9999")), "foo");
      fail("Bogus servers shouldn't have been successfully moved.");
    } catch (IOException ex) {
      String exp = "Source RSGroup for server foo:9999 does not exist.";
      String msg = "Expected '" + exp + "' in exception message: ";
      assertTrue(msg + " " + ex.getMessage(), ex.getMessage().contains(exp));
    }

    // test success case
    LOG.info("moving servers " + barGroup.getServers() + " to group foo");
    rsGroupAdmin.moveServers(barGroup.getServers(), fooGroup.getName());

    barGroup = rsGroupAdmin.getRSGroupInfo("bar");
    fooGroup = rsGroupAdmin.getRSGroupInfo("foo");
    assertEquals(0, barGroup.getServers().size());
    assertEquals(3, fooGroup.getServers().size());

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
    LOG.info("Remove group " + barGroup.getName());
    rsGroupAdmin.removeRSGroup(barGroup.getName());
    Assert.assertEquals(null, rsGroupAdmin.getRSGroupInfo(barGroup.getName()));
    LOG.info("Remove group " + fooGroup.getName());
    rsGroupAdmin.removeRSGroup(fooGroup.getName());
    Assert.assertEquals(null, rsGroupAdmin.getRSGroupInfo(fooGroup.getName()));
  }

};