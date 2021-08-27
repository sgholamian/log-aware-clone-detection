//,temp,Log4JConfigView.java,158,199,temp,Log4JConfigView.java,56,85
//,3
public class xxx {
    @Override
    public void setLogLevel(String loggerName, String level) throws Exception {

        if (loggerName == null || loggerName.isEmpty()) {
            throw new IllegalArgumentException("Logger names cannot be null or empty strings");
        }

        if (level == null || level.isEmpty()) {
            throw new IllegalArgumentException("Level name cannot be null or empty strings");
        }

        ClassLoader cl = getClassLoader();

        if (!isLog4JAvailable(cl)) {
            return;
        }

        Class<?> loggerClass = getLoggerClass(cl);
        Class<?> levelClass = getLevelClass(cl);
        if (loggerClass == null || levelClass == null) {
            return;
        }

        String targetLevel = level.toUpperCase(Locale.US);
        Method getLogger = loggerClass.getMethod("getLogger", String.class);
        Method setLevel = loggerClass.getMethod("setLevel", levelClass);
        Method toLevel = levelClass.getMethod("toLevel", String.class);

        Object logger = getLogger.invoke(null, loggerName);
        if (logger != null) {
            Object newLevel = toLevel.invoke(null, targetLevel);

            // Check that the level conversion worked and that we got a level
            // that matches what was asked for.  A bad level name will result
            // in the lowest level value and we don't want to change unless we
            // matched what the user asked for.
            if (newLevel != null && newLevel.toString().equals(targetLevel)) {
                LOG.debug("Set level {} for logger: {}", level, loggerName);
                setLevel.invoke(logger, newLevel);
            }
        }
    }

};