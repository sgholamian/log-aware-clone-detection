//,temp,EditLogTailer_after_fix.java,318,363,temp,EditLogTailer.java,318,363
//,2
public class xxx {
    private void doWork() {
      while (shouldRun) {
        try {
          // There's no point in triggering a log roll if the Standby hasn't
          // read any more transactions since the last time a roll was
          // triggered. 
          if (tooLongSinceLastLoad() &&
              lastRollTriggerTxId < lastLoadedTxnId) {
            triggerActiveLogRoll();
          }
          /**
           * Check again in case someone calls {@link EditLogTailer#stop} while
           * we're triggering an edit log roll, since ipc.Client catches and
           * ignores {@link InterruptedException} in a few places. This fixes
           * the bug described in HDFS-2823.
           */
          if (!shouldRun) {
            break;
          }
          // Prevent reading of name system while being modified. The full
          // name system lock will be acquired to further block even the block
          // state updates.
          namesystem.cpLockInterruptibly();
          try {
            doTailEdits();
          } finally {
            namesystem.cpUnlock();
          }
        } catch (EditLogInputException elie) {
          LOG.warn("Error while reading edits from disk. Will try again.", elie);
        } catch (InterruptedException ie) {
          // interrupter should have already set shouldRun to false
          continue;
        } catch (Throwable t) {
          LOG.fatal("Unknown error encountered while tailing edits. " +
              "Shutting down standby NN.", t);
          terminate(1, t);
        }

        try {
          Thread.sleep(sleepTimeMs);
        } catch (InterruptedException e) {
          LOG.warn("Edit log tailer interrupted", e);
        }
      }
    }

};