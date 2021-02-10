//,temp,TestRouterWebServices.java,281,332,temp,TestRouterClientRMService.java,217,268
//,3
public class xxx {
  @Test
  public void testWebPipelineConcurrent() throws InterruptedException {
    final String user = "test1";

    /*
     * ClientTestThread is a thread to simulate a client request to get a
     * RESTRequestInterceptor for the user.
     */
    class ClientTestThread extends Thread {
      private RESTRequestInterceptor interceptor;
      @Override public void run() {
        try {
          interceptor = pipeline();
        } catch (IOException | InterruptedException e) {
          e.printStackTrace();
        }
      }
      private RESTRequestInterceptor pipeline()
          throws IOException, InterruptedException {
        return UserGroupInformation.createRemoteUser(user).doAs(
            new PrivilegedExceptionAction<RESTRequestInterceptor>() {
              @Override
              public RESTRequestInterceptor run() throws Exception {
                RequestInterceptorChainWrapper wrapper =
                    getInterceptorChain(user);
                RESTRequestInterceptor interceptor =
                    wrapper.getRootInterceptor();
                Assert.assertNotNull(interceptor);
                LOG.info("init web interceptor success for user" + user);
                return interceptor;
              }
            });
      }
    }

    /*
     * We start the first thread. It should not finish initing a chainWrapper
     * before the other thread starts. In this way, the second thread can
     * init at the same time of the first one. In the end, we validate that
     * the 2 threads get the same chainWrapper without going into error.
     */
    ClientTestThread client1 = new ClientTestThread();
    ClientTestThread client2 = new ClientTestThread();
    client1.start();
    client2.start();
    client1.join();
    client2.join();

    Assert.assertNotNull(client1.interceptor);
    Assert.assertNotNull(client2.interceptor);
    Assert.assertTrue(client1.interceptor == client2.interceptor);
  }

};