//,temp,PurgeCommandTest.java,317,367,temp,PurgeCommandTest.java,277,315
//,3
public class xxx {
    public void testPurgeCommandComplexSQLSelector_AND() throws Exception {
        try {

            String one = "ID:mac.fritz.box:1213242.3231.1:1:1:100";
            String two = "\\*:100";
            try {
            if (one.matches(two))
                LOG.info("String matches.");
            else
                LOG.info("string does not match.");
            } catch (Exception ex) {
                LOG.error(ex.getMessage());
            }

            PurgeCommand purgeCommand = new PurgeCommand();
            CommandContext context = new CommandContext();

            context.setFormatter(new CommandShellOutputFormatter(System.out));

            purgeCommand.setCommandContext(context);
            purgeCommand.setJmxUseLocal(true);

            List<String> tokens = new ArrayList<String>();
            tokens.add("--msgsel");
            tokens.add(MSG_SEL_COMPLEX_SQL_AND);

            addMessages();
            validateCounts(MESSAGE_COUNT, MESSAGE_COUNT, MESSAGE_COUNT * 2);

            purgeCommand.execute(tokens);

            QueueBrowser withPropertyBrowser = requestServerSession.createBrowser(
                    theQueue, MSG_SEL_COMPLEX_SQL_AND);
            QueueBrowser allBrowser = requestServerSession.createBrowser(theQueue);

            int withCount = getMessageCount(withPropertyBrowser, "withProperty ");
            int allCount = getMessageCount(allBrowser, "allMessages ");

            withPropertyBrowser.close();
            allBrowser.close();

            assertEquals("Expected withCount to be " + "0" + " was "
                    + withCount, 0, withCount);
            assertEquals("Expected allCount to be " + MESSAGE_COUNT + " was "
                    + allCount, MESSAGE_COUNT, allCount);
            LOG.info("withCount = " + withCount + "\n allCount = " +
                    allCount + "\n  = " + "\n");
        } finally {
            purgeAllMessages();
        }
    }

};