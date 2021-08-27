//,temp,AtlasMapEndpointTest.java,190,195,temp,AtlasMapEndpointTest.java,172,177
//,3
public class xxx {
                @Override
                public Object answer(InvocationOnMock invocation) throws Throwable {
                    LOG.debug("getTargetDocument({})", invocation.getArgument(0).toString());
                    assertEquals(targetDocId, invocation.getArgument(0));
                    return "<target/>";
                }

};