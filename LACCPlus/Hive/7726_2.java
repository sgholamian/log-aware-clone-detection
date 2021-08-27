//,temp,TaskExecutorService.java,875,912,temp,TaskExecutorService.java,632,667
//,3
public class xxx {
  @Override
  public boolean updateFragment(String fragmentId, boolean isGuaranteed) {
    synchronized (lock) {
      TaskWrapper taskWrapper = knownTasks.get(fragmentId);
      if (taskWrapper == null) {
        LOG.debug("Fragment not found {}", fragmentId);
        return false;
      }
      if (taskWrapper.isGuaranteed() == isGuaranteed) return true;

      LOG.debug("Fragment {} guaranteed state changed to {}; finishable {}, in wait queue {}, "
          + "in preemption queue {}", taskWrapper.getRequestId(), isGuaranteed,
          taskWrapper.canFinishForPriority(), taskWrapper.isInWaitQueue(),
          taskWrapper.isInPreemptionQueue());
      // Do the removal before we change the element, to avoid invalid queue ordering.
      if (isGuaranteed && taskWrapper.isInPreemptionQueue() && taskWrapper.canFinishForPriority()) {
        removeFromPreemptionQueue(taskWrapper);
      }
      if (taskWrapper.isInWaitQueue()) {
        // Re-order the wait queue. Note: we assume that noone will take our capacity based
        // on the fact that we are doing this under the epic lock. If the epic lock is removed,
        // we'd need to do the steps under the queue lock; we could pass in a f() to update state.
        boolean isRemoved = waitQueue.remove(taskWrapper);
        taskWrapper.updateIsGuaranteed(isGuaranteed);
        forceReinsertIntoQueue(taskWrapper, isRemoved);
      } else {
        taskWrapper.updateIsGuaranteed(isGuaranteed);
        if (!isGuaranteed && !taskWrapper.isInPreemptionQueue()) {
          // No need to check finishable here; if it was set it would already be in the queue.
          addToPreemptionQueue(taskWrapper);
        }
      }
      lock.notifyAll();
      return true;
    }
  }

};