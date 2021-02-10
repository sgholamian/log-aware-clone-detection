//,temp,TestRouterClientRMService.java,238,247,temp,TestRouterRMAdminService.java,247,256
//,2
public class xxx {
              @Override
              public ClientRequestInterceptor run() throws Exception {
                RequestInterceptorChainWrapper wrapper =
                    getRouterClientRMService().getInterceptorChain();
                ClientRequestInterceptor interceptor =
                    wrapper.getRootInterceptor();
                Assert.assertNotNull(interceptor);
                LOG.info("init client interceptor success for user " + user);
                return interceptor;
              }

};