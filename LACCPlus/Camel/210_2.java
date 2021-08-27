//,temp,ExecStartCmdHeaderTest.java,65,74,temp,AttachContainerCmdHeaderTest.java,77,86
//,2
public class xxx {
    @Override
    protected void setupMocks() {
        Mockito.when(dockerClient.attachContainerCmd(anyString())).thenReturn(mockObject);
        Mockito.when(mockObject.exec(any())).thenReturn(callback);
        try {
            Mockito.when(callback.awaitCompletion()).thenReturn(callback);
        } catch (InterruptedException e) {
            LOG.warn("Interrupted while setting up mocks", e);
        }
    }

};