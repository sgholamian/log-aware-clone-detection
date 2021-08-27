//,temp,AtlasMapEndpointTest.java,161,167,temp,AtlasMapEndpointTest.java,152,157
//,3
public class xxx {
                @Override
                public Void answer(InvocationOnMock invocation) {
                    LOG.debug("setDefaultSourceDocument({})", invocation.getArgument(0).toString());
                    assertEquals("{test}", invocation.getArgument(0).toString());
                    return null;
                }

};