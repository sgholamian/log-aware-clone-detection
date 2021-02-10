//,temp,TopCLI.java,583,595,temp,TopCLI.java,569,581
//,2
public class xxx {
  private void setTerminalWidth() throws IOException, InterruptedException {
    if (terminalWidth != -1) {
      return;
    }
    String[] command = { "tput", "cols" };
    String op = getCommandOutput(command).trim();
    try {
      terminalWidth = Integer.parseInt(op);
    } catch (NumberFormatException ne) {
      LOG.warn("Couldn't determine terminal width, setting to 80", ne);
      terminalWidth = 80;
    }
  }

};