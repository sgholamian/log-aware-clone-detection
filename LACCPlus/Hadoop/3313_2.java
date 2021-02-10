//,temp,TestRouterClientRMService.java,238,247,temp,TestRouterRMAdminService.java,247,256
//,2
public class xxx {
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

};