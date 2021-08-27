//,temp,sample_5093.java,2,20,temp,sample_5092.java,2,20
//,3
public class xxx {
public void dummy_method(){
if (currentCounters.containsKey(desiredCounter)) {
long currentCounterValue = currentCounters.get(desiredCounter);
if (currentTrigger.apply(currentCounterValue)) {
String queryId = sessionState.getWmContext().getQueryId();
if (violatedSessions.containsKey(sessionState)) {
Trigger existingTrigger = violatedSessions.get(sessionState);
if (existingTrigger.getAction().getType().equals(Action.Type.MOVE_TO_POOL) && currentTrigger.getAction().getType().equals(Action.Type.KILL_QUERY)) {
currentTrigger.setViolationMsg("Trigger " + currentTrigger + " violated. Current value: " + currentCounterValue);
violatedSessions.put(sessionState, currentTrigger);
} else {


log.info("conflicting move triggers and choosing the first move trigger");
}
}
}
}
}

};