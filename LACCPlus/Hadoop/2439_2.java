//,temp,BaseAMRMProxyTest.java,408,454,temp,BaseAMRMProxyTest.java,325,369
//,3
public class xxx {
  protected <T> List<RegisterApplicationMasterResponseInfo<T>> registerApplicationMastersInParallel(
      final ArrayList<T> testContexts) {
    List<RegisterApplicationMasterResponseInfo<T>> responses =
        runInParallel(testContexts,
            new Function<T, RegisterApplicationMasterResponseInfo<T>>() {
              @Override
              public RegisterApplicationMasterResponseInfo<T> invoke(
                  T testContext) {
                RegisterApplicationMasterResponseInfo<T> response = null;
                try {
                  int index = testContexts.indexOf(testContext);
                  response =
                      new RegisterApplicationMasterResponseInfo<T>(
                          registerApplicationMaster(index), testContext);
                  Assert.assertNotNull(response.getResponse());
                  Assert.assertEquals(Integer.toString(index), response
                      .getResponse().getQueue());

                  LOG.info("Sucessfully registered application master with test context: "
                      + testContext);
                } catch (Throwable ex) {
                  response = null;
                  LOG.error("Failed to register application master with test context: "
                      + testContext);
                }

                return response;
              }
            });

    Assert.assertEquals(
        "Number of responses received does not match with request",
        testContexts.size(), responses.size());

    Set<T> contextResponses = new TreeSet<T>();
    for (RegisterApplicationMasterResponseInfo<T> item : responses) {
      contextResponses.add(item.getTestContext());
    }

    for (T ep : testContexts) {
      Assert.assertTrue(contextResponses.contains(ep));
    }

    return responses;
  }

};