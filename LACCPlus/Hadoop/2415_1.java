//,temp,TestThrottledAsyncCheckerTimeout.java,173,209,temp,TestThrottledAsyncCheckerTimeout.java,79,127
//,3
public class xxx {
  @Test
  public void testTimeoutExceptionIsNotThrownForGoodDisk() throws Exception {
    LOG.info("Executing {}", testName.getMethodName());

    final DummyCheckable target = new DummyCheckable();
    final FakeTimer timer = new FakeTimer();
    ThrottledAsyncChecker<Boolean, Boolean> checker =
        new ThrottledAsyncChecker<>(timer, 0, DISK_CHECK_TIMEOUT,
            getExecutorService());

    final Optional<ListenableFuture<Boolean>> olf = checker
        .schedule(target, true);

    AtomicBoolean callbackResult = new AtomicBoolean(false);
    final Throwable[] throwable = new Throwable[1];

    assertTrue(olf.isPresent());
    Futures.addCallback(olf.get(), new FutureCallback<Boolean>() {
      @Override
      public void onSuccess(Boolean result) {
        callbackResult.set(true);
      }

      @Override
      public void onFailure(Throwable t) {
        throwable[0] = t;
        callbackResult.set(true);
      }
    });

    while (!callbackResult.get()) {
      // Wait for the callback
      Thread.sleep(DISK_CHECK_TIMEOUT);
    }

    assertTrue(throwable[0] == null);
  }

};