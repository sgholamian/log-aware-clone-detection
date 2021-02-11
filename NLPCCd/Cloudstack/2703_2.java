//,temp,sample_8553.java,2,13,temp,sample_8554.java,2,14
//,3
public class xxx {
private void processWorkWithRetryCount(int count, Step expectedStep) {
assertNotNull(processWorkMethod);
HaWorkVO work = new HaWorkVO(1l, VirtualMachine.Type.User, WorkType.Migration, Step.Scheduled, 1l, VirtualMachine.State.Running, count, 12345678l);
Mockito.doReturn(12345678l).when(highAvailabilityManagerSpy).migrate(work);
try {
processWorkMethod.invoke(highAvailabilityManagerSpy, work);
} catch (IllegalAccessException e) {
} catch (IllegalArgumentException e) {


log.info("ignored expected illegalargumentexception caught");
}
}

};