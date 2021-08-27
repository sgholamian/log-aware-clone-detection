//,temp,PurgeCommandTest.java,317,367,temp,PurgeCommandTest.java,277,315
//,3
public class xxx {
    public void testPurgeCommandComplexSelector() throws Exception {
        try {
            PurgeCommand purgeCommand = new PurgeCommand();
            CommandContext context = new CommandContext();

            context.setFormatter(new CommandShellOutputFormatter(System.out));

            purgeCommand.setCommandContext(context);
            purgeCommand.setJmxUseLocal(true);

            List<String> tokens = new ArrayList<String>();
            tokens.add("--msgsel");
            tokens.add(MSG_SEL_COMPLEX);

            addMessages();
            validateCounts(MESSAGE_COUNT, MESSAGE_COUNT, MESSAGE_COUNT * 2);

            purgeCommand.execute(tokens);

            QueueBrowser withPropertyBrowser = requestServerSession.createBrowser(
                    theQueue, MSG_SEL_COMPLEX);
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