//,temp,BaseAMRMProxyTest.java,408,454,temp,BaseAMRMProxyTest.java,325,369
//,3
public class xxx {
  protected <T> List<FinishApplicationMasterResponseInfo<T>> finishApplicationMastersInParallel(
      final ArrayList<T> testContexts) {
    List<FinishApplicationMasterResponseInfo<T>> responses =
        runInParallel(testContexts,
            new Function<T, FinishApplicationMasterResponseInfo<T>>() {
              @Override
              public FinishApplicationMasterResponseInfo<T> invoke(
                  T testContext) {
                FinishApplicationMasterResponseInfo<T> response = null;
                try {
                  response =
                      new FinishApplicationMasterResponseInfo<T>(
                          finishApplicationMaster(
                              testContexts.indexOf(testContext),
                              FinalApplicationStatus.SUCCEEDED),
                          testContext);
                  Assert.assertNotNull(response.getResponse());

                  LOG.info("Sucessfully finished application master with test contexts: "
                      + testContext);
                } catch (Throwable ex) {
                  response = null;
                  LOG.error("Failed to finish application master with test context: "
                      + testContext);
                }

                return response;
              }
            });

    Assert.assertEquals(
        "Number of responses received does not match with request",
        testContexts.size(), responses.size());

    Set<T> contextResponses = new TreeSet<T>();
    for (FinishApplicationMasterResponseInfo<T> item : responses) {
      Assert.assertNotNull(item);
      Assert.assertNotNull(item.getResponse());
      contextResponses.add(item.getTestContext());
    }

    for (T ep : testContexts) {
      Assert.assertTrue(contextResponses.contains(ep));
    }

    return responses;
  }

};