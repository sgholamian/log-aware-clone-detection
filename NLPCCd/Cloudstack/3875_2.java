//,temp,sample_6954.java,2,12,temp,sample_6953.java,2,11
//,2
public class xxx {
public boolean finalizeStart(VirtualMachineProfile profile, long hostId, Commands cmds, ReservationContext context) {
CheckSshAnswer answer = (CheckSshAnswer)cmds.getAnswer("checkSsh");
if (answer == null || !answer.getResult()) {
if (answer != null) {


log.info("unable to ssh to the vm");
}
}
}

};