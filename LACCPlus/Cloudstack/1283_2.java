//,temp,CitrixResourceBase.java,3239,3262,temp,CitrixGetVncPortCommandWrapper.java,40,57
//,3
public class xxx {
    @Override
    public Answer execute(final GetVncPortCommand command, final CitrixResourceBase citrixResourceBase) {
        final Connection conn = citrixResourceBase.getConnection();
        try {
            final Set<VM> vms = VM.getByNameLabel(conn, command.getName());
            if (vms.size() == 1) {
                String consoleurl;
                consoleurl = "consoleurl=" + citrixResourceBase.getVncUrl(conn, vms.iterator().next()) + "&" + "sessionref=" + conn.getSessionReference();
                return new GetVncPortAnswer(command, consoleurl, -1);
            } else {
                return new GetVncPortAnswer(command, "There are " + vms.size() + " VMs named " + command.getName());
            }
        } catch (final Exception e) {
            final String msg = "Unable to get vnc port due to " + e.toString();
            s_logger.warn(msg, e);
            return new GetVncPortAnswer(command, msg);
        }
    }

};