//,temp,TestRouterWebServices.java,298,313,temp,TestRouterRMAdminService.java,243,258
//,3
public class xxx {
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

};