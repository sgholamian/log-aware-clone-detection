//,temp,TopCLI.java,583,595,temp,TopCLI.java,569,581
//,2
public class xxx {
  private void setTerminalHeight() throws IOException, InterruptedException {
    if (terminalHeight != -1) {
      return;
    }
    String[] command = { "tput", "lines" };
    String op = getCommandOutput(command).trim();
    try {
      terminalHeight = Integer.parseInt(op);
    } catch (NumberFormatException ne) {
      LOG.warn("Couldn't determine terminal height, setting to 24", ne);
      terminalHeight = 24;
    }
  }

};