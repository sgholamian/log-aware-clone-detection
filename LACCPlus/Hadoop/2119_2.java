//,temp,HadoopLogsAnalyzer.java,1382,1529,temp,HadoopLogsAnalyzer.java,1235,1380
//,3
public class xxx {
  private void processMapAttemptLine(ParsedLine line) {
    String attemptID = line.get("TASK_ATTEMPT_ID");

    String taskID = line.get("TASKID");

    String status = line.get("TASK_STATUS");

    String attemptStartTime = line.get("START_TIME");
    String attemptFinishTime = line.get("FINISH_TIME");

    String hostName = line.get("HOSTNAME");

    String counters = line.get("COUNTERS");

    if (jobBeingTraced != null && taskID != null) {
      LoggedTask task = tasksInCurrentJob.get(taskID);

      if (task == null) {
        task = new LoggedTask();

        task.setTaskID(taskID);

        jobBeingTraced.getMapTasks().add(task);

        tasksInCurrentJob.put(taskID, task);
      }

      task.setTaskID(taskID);

      LoggedTaskAttempt attempt = attemptsInCurrentJob.get(attemptID);

      boolean attemptAlreadyExists = attempt != null;

      if (attempt == null) {
        attempt = new LoggedTaskAttempt();

        attempt.setAttemptID(attemptID);
      }

      if (!attemptAlreadyExists) {
        attemptsInCurrentJob.put(attemptID, attempt);
        task.getAttempts().add(attempt);
      }

      Pre21JobHistoryConstants.Values stat = null;

      try {
        stat =
            status == null ? null : Pre21JobHistoryConstants.Values
                .valueOf(status);
      } catch (IllegalArgumentException e) {
        LOG.error("A map attempt status you don't know about is \"" + status
            + "\".", e);
        stat = null;
      }

      incorporateCounters(attempt, counters);

      attempt.setResult(stat);

      if (attemptStartTime != null) {
        attempt.setStartTime(Long.parseLong(attemptStartTime));
      }

      if (attemptFinishTime != null) {
        attempt.setFinishTime(Long.parseLong(attemptFinishTime));
      }

      int distance = Integer.MAX_VALUE;

      if (hostName != null) {

        ParsedHost host = getAndRecordParsedHost(hostName);

        if (host != null) {
          attempt.setHostName(host.getNodeName(), host.getRackName());
          attempt.setLocation(host.makeLoggedLocation());
        } else {
          attempt.setHostName(hostName, null);
        }

        List<LoggedLocation> locs = task.getPreferredLocations();

        if (host != null && locs != null) {
          for (LoggedLocation loc : locs) {
            ParsedHost preferedLoc = new ParsedHost(loc);

            distance = Math.min(distance, preferedLoc.distance(host));
          }
        }

        mapperLocality.enter(distance);
      }

      distance = Math.min(distance, successfulMapAttemptTimes.length - 1);

      if (attempt.getStartTime() > 0 && attempt.getFinishTime() > 0) {
        long runtime = attempt.getFinishTime() - attempt.getStartTime();

        if (stat == Pre21JobHistoryConstants.Values.SUCCESS) {
          successfulMapAttemptTimes[distance].enter(runtime);
        }

        if (stat == Pre21JobHistoryConstants.Values.FAILED) {
          failedMapAttemptTimes[distance].enter(runtime);
        }
      }

      if (attemptID != null) {
        Matcher matcher = taskAttemptIDPattern.matcher(attemptID);

        if (matcher.matches()) {
          String attemptNumberString = matcher.group(1);

          if (attemptNumberString != null) {
            int attemptNumber = Integer.parseInt(attemptNumberString);

            successfulNthMapperAttempts.enter(attemptNumber);
          }
        }
      }
    }

    try {
      if (attemptStartTime != null) {
        long startTimeValue = Long.parseLong(attemptStartTime);

        if (startTimeValue != 0
            && startTimeValue + MAXIMUM_CLOCK_SKEW >= launchTimeCurrentJob) {
          taskAttemptStartTimes.put(attemptID, startTimeValue);
        } else {
          taskAttemptStartTimes.remove(attemptID);
        }
      } else if (status != null && attemptFinishTime != null) {
        long finishTime = Long.parseLong(attemptFinishTime);

        if (status.equals("SUCCESS")) {
          taskMapAttemptFinishTimes.put(attemptID, finishTime);
        }
      }
    } catch (NumberFormatException e) {
      LOG.warn(
          "HadoopLogsAnalyzer.processMapAttemptLine: bad numerical format, at line"
              + lineNumber + ".", e);
    }
  }

};