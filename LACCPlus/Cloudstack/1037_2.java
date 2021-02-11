//,temp,TaskMO.java,59,79,temp,VmwareHelper.java,674,700
//,3
public class xxx {
    public static String getExceptionMessage(Throwable e, boolean printStack) {
        //TODO: in vim 5.1, exceptions do not have a base exception class, MethodFault becomes a FaultInfo that we can only get
        // from individual exception through getFaultInfo, so we have to use reflection here to get MethodFault information.
        try {
            Class<? extends Throwable> cls = e.getClass();
            Method mth = cls.getDeclaredMethod("getFaultInfo", (Class<?>)null);
            if (mth != null) {
                Object fault = mth.invoke(e, (Object[])null);
                if (fault instanceof MethodFault) {
                    final StringWriter writer = new StringWriter();
                    writer.append("Exception: " + fault.getClass().getName() + "\n");
                    writer.append("message: " + ((MethodFault)fault).getFaultMessage() + "\n");

                    if (printStack) {
                        writer.append("stack: ");
                        e.printStackTrace(new PrintWriter(writer));
                    }
                    return writer.toString();
                }
            }
        } catch (Exception ex) {
            s_logger.info("[ignored]"
                    + "failed toi get message for exception: " + e.getLocalizedMessage());
        }

        return ExceptionUtil.toString(e, printStack);
    }

};