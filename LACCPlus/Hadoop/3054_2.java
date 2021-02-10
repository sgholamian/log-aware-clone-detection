//,temp,LeaseManager.java,68,85,temp,DatanodeStateMachine.java,389,401
//,3
public class xxx {
  private Thread getCommandHandlerThread(Runnable processCommandQueue) {
    Thread handlerThread = new Thread(processCommandQueue);
    handlerThread.setDaemon(true);
    handlerThread.setName("Command processor thread");
    handlerThread.setUncaughtExceptionHandler((Thread t, Throwable e) -> {
      // Let us just restart this thread after logging a critical error.
      // if this thread is not running we cannot handle commands from SCM.
      LOG.error("Critical Error : Command processor thread encountered an " +
          "error. Thread: {}", t.toString(), e);
      getCommandHandlerThread(processCommandQueue).start();
    });
    return handlerThread;
  }

};