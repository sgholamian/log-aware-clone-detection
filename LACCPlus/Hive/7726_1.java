//,temp,TaskExecutorService.java,875,912,temp,TaskExecutorService.java,632,667
//,3
public class xxx {
  @VisibleForTesting
  void finishableStateUpdated(TaskWrapper taskWrapper, boolean newFinishableState) {
    synchronized (lock) {
      LOG.debug("Fragment {} guaranteed state changed to {}; finishable {}, in wait queue {}, "
          + "in preemption queue {}", taskWrapper.getRequestId(), taskWrapper.isGuaranteed(),
          newFinishableState, taskWrapper.isInWaitQueue(), taskWrapper.isInPreemptionQueue());
      // Do the removal before we change the element, to avoid invalid queue ordering.
      if (newFinishableState && taskWrapper.isInPreemptionQueue() && taskWrapper.isGuaranteed()) {
        removeFromPreemptionQueue(taskWrapper);
      }
      if (taskWrapper.isInWaitQueue()) {
        // Re-order the wait queue. Note: we assume that noone will take our capacity based
        // on the fact that we are doing this under the epic lock. If the epic lock is removed,
        // we'd need to do the steps under the queue lock; we could pass in a f() to update state.
        boolean isRemoved = waitQueue.remove(taskWrapper);
        taskWrapper.updateCanFinishForPriority(newFinishableState);
        forceReinsertIntoQueue(taskWrapper, isRemoved);
      } else {
        // if speculative task, any finishable state change should re-order the queue as speculative tasks are always
        // not-guaranteed (re-order helps put non-finishable's ahead of finishable)
        if (!taskWrapper.isGuaranteed()) {
          removeFromPreemptionQueue(taskWrapper);
          taskWrapper.updateCanFinishForPriority(newFinishableState);
          addToPreemptionQueue(taskWrapper);
        } else {
          // if guaranteed task, if the finishable state changed to non-finishable and if the task doesn't exist
          // pre-emption queue, then add it so that it becomes candidate to kill
          taskWrapper.updateCanFinishForPriority(newFinishableState);
          if (!newFinishableState && !taskWrapper.isInPreemptionQueue()) {
            // No need to check guaranteed here; if it was false we would already be in the queue.
            addToPreemptionQueue(taskWrapper);
          }
        }
      }

      lock.notifyAll();
    }
  }

};