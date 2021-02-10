//,temp,HadoopLogsAnalyzer.java,1382,1529,temp,HadoopLogsAnalyzer.java,1235,1380
//,3
public class xxx {
  private void processReduceAttemptLine(ParsedLine line) {
    String attemptID = line.get("TASK_ATTEMPT_ID");

    String taskID = line.get("TASKID");

    String status = line.get("TASK_STATUS");

    String attemptStartTime = line.get("START_TIME");
    String attemptFinishTime = line.get("FINISH_TIME");
    String attemptShuffleFinished = line.get("SHUFFLE_FINISHED");
    String attemptSortFinished = line.get("SORT_FINISHED");

    String counters = line.get("COUNTERS");

    String hostName = line.get("HOSTNAME");

    if (hostName != null && !hostNames.contains(hostName)) {
      hostNames.add(hostName);
    }

    if (jobBeingTraced != null && taskID != null) {
      LoggedTask task = tasksInCurrentJob.get(taskID);

      if (task == null) {
        task = new LoggedTask();

        task.setTaskID(taskID);

        jobBeingTraced.getReduceTasks().add(task);

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
        LOG.warn("A map attempt status you don't know about is \"" + status
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

      if (attemptShuffleFinished != null) {
        attempt.setShuffleFinished(Long.parseLong(attemptShuffleFinished));
      }

      if (attemptSortFinished != null) {
        attempt.setSortFinished(Long.parseLong(attemptSortFinished));
      }

      if (attempt.getStartTime() > 0 && attempt.getFinishTime() > 0) {
        long runtime = attempt.getFinishTime() - attempt.getStartTime();

        if (stat == Pre21JobHistoryConstants.Values.SUCCESS) {
          successfulReduceAttemptTimes.enter(runtime);
        }

        if (stat == Pre21JobHistoryConstants.Values.FAILED) {
          failedReduceAttemptTimes.enter(runtime);
        }
      }

      if (hostName != null) {
        ParsedHost host = getAndRecordParsedHost(hostName);
        if (host != null) {
          attempt.setHostName(host.getNodeName(), host.getRackName());
        } else {
          attempt.setHostName(hostName, null);
        }
      }

      if (attemptID != null) {
        Matcher matcher = taskAttemptIDPattern.matcher(attemptID);

        if (matcher.matches()) {
          String attemptNumberString = matcher.group(1);

          if (attemptNumberString != null) {
            int attemptNumber = Integer.parseInt(attemptNumberString);

            successfulNthReducerAttempts.enter(attemptNumber);
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
        }
      } else if (status != null && status.equals("SUCCESS")
          && attemptFinishTime != null) {
        long finishTime = Long.parseLong(attemptFinishTime);

        taskReduceAttemptFinishTimes.put(attemptID, finishTime);

        if (attemptShuffleFinished != null) {
          taskReduceAttemptShuffleEndTimes.put(attemptID, Long
              .parseLong(attemptShuffleFinished));
        }

        if (attemptSortFinished != null) {
          taskReduceAttemptSortEndTimes.put(attemptID, Long
              .parseLong(attemptSortFinished));
        }
      }
    } catch (NumberFormatException e) {
      LOG.error(
          "HadoopLogsAnalyzer.processReduceAttemptLine: bad numerical format, at line"
              + lineNumber + ".", e);
    }
  }

};