//,temp,TestRouterWebServices.java,298,313,temp,TestRouterRMAdminService.java,243,258
//,3
public class xxx {
      private RMAdminRequestInterceptor pipeline()
          throws IOException, InterruptedException {
        return UserGroupInformation.createRemoteUser(user).doAs(
            new PrivilegedExceptionAction<RMAdminRequestInterceptor>() {
              @Override
              public RMAdminRequestInterceptor run() throws Exception {
                RequestInterceptorChainWrapper wrapper =
                    getRouterRMAdminService().getInterceptorChain();
                RMAdminRequestInterceptor interceptor =
                    wrapper.getRootInterceptor();
                Assert.assertNotNull(interceptor);
                LOG.info("init rm admin interceptor success for user" + user);
                return interceptor;
              }
            });
      }

};