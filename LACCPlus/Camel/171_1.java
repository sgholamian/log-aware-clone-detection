//,temp,PushImageCmdHeaderTest.java,73,82,temp,LogContainerCmdHeaderTest.java,80,89
//,2
public class xxx {
    @Override
    protected void setupMocks() {
        Mockito.when(dockerClient.pushImageCmd(anyString())).thenReturn(mockObject);
        Mockito.when(mockObject.exec(any())).thenReturn(callback);
        try {
            Mockito.when(callback.awaitCompletion()).thenReturn(callback);
        } catch (InterruptedException e) {
            LOG.warn("Interrupted while setting up mocks", e);
        }
    }

};