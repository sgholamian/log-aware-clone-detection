//,temp,sample_3314.java,2,9,temp,sample_6953.java,2,11
//,3
public class xxx {
public boolean finalizeStart(VirtualMachineProfile profile, long hostId, Commands cmds, ReservationContext context) {
CheckSshAnswer answer = (CheckSshAnswer)cmds.getAnswer("checkSsh");
if (!answer.getResult()) {


log.info("unable to ssh to the vm");
}
}

};