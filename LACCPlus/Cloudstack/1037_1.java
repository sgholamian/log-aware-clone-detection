//,temp,TaskMO.java,59,79,temp,VmwareHelper.java,674,700
//,3
public class xxx {
    public static String getTaskFailureInfo(VmwareContext context, ManagedObjectReference morTask) {
        StringBuffer sb = new StringBuffer();

        try {
            TaskInfo info = (TaskInfo)context.getVimClient().getDynamicProperty(morTask, "info");
            if (info != null) {
                LocalizedMethodFault fault = info.getError();
                if (fault != null) {
                    sb.append(fault.getLocalizedMessage()).append(" ");

                    if (fault.getFault() != null)
                        sb.append(fault.getFault().getClass().getName());
                }
            }
        } catch (Exception e) {
            s_logger.info("[ignored]"
                    + "error retrieving failure info for task : " + e.getLocalizedMessage());
        }

        return sb.toString();
    }

};