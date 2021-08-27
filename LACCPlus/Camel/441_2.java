//,temp,AtlasMapEndpointTest.java,190,195,temp,AtlasMapEndpointTest.java,172,177
//,3
public class xxx {
            @Override
            public Void answer(InvocationOnMock invocation) {
                LOG.debug("setBody({})", invocation.getArgument(0).toString());
                assertEquals("<target/>", invocation.getArgument(0));
                return null;
            }

};