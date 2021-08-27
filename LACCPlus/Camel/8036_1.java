//,temp,AtlasMapEndpointTest.java,161,167,temp,AtlasMapEndpointTest.java,152,157
//,3
public class xxx {
                @Override
                public Void answer(InvocationOnMock invocation) {
                    LOG.debug("setSourceDocument({}, {})", invocation.getArgument(0), invocation.getArgument(1));
                    assertEquals(sourceDocId, invocation.getArgument(0));
                    assertEquals("{test}", invocation.getArgument(1));
                    return null;
                }

};