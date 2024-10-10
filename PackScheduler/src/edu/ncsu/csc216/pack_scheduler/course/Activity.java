package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Abstract class representing an academic activity with a title, meeting days,
 * start time, and end time. This class serves as a base for specific types of
 * activities like courses and events.
 * 
 * @author Dania Swelam
 * 
 */
public abstract class Activity implements Conflict {

	/** Course's title. */
	private String title;
	/** Course's meeting days */
	private String meetingDays;
	/** Course's starting time */
	private int startTime;
	/** Course's ending time */
	private int endTime;
	/** Constant integer for maximum value for the hour in a 24 hour format */
	private static final int UPPER_HOUR = 24;
	/** Constant integer for maximum value of minute in a time. */
	private static final int UPPER_MINUTE = 60;

	/**
	 * Represents an activity with a title, meeting days, start time, and end time.
	 * 
	 * @param title       the title of the activity
	 * @param meetingDays the days on which the activity meets
	 * @param startTime   the start time of the activity
	 * @param endTime     the end time of the activity
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
		super();
		setTitle(title);
		setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}

	/**
	 * Abstract method to provide a short version of the array of information for
	 * display in the GUI. The short display array is used to populate the rows of
	 * the course catalog and student schedule.
	 *
	 * @return a String array representing the short version of the display
	 *         information
	 */
	public abstract String[] getShortDisplayArray();

	/**
	 * Abstract method to provide a long version of the array of information for
	 * display in the GUI. The full display array is used to display the final
	 * schedule.
	 *
	 * @return a String array representing the long version of the display
	 *         information
	 */
	public abstract String[] getLongDisplayArray();

	/**
	 * Checks if the given activity is a duplicate of the current activity instance.
	 *
	 * @param activity the activity to compare for duplication.
	 * @return true if the provided activity is a duplicate, false otherwise.
	 */
	public abstract boolean isDuplicate(Activity activity);

	/**
	 * Returns the title of a given Course
	 * 
	 * @return the title of Course
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the course. The title must not be null or an empty string.
	 * 
	 * @param title The title to set for the course.
	 * @throws IllegalArgumentException If the provided title is null or an empty
	 *                                  string.
	 */
	public void setTitle(String title) {
		if (title == null || title.isEmpty()) {
			throw new IllegalArgumentException("Invalid title.");
		}
		this.title = title;
	}

	/**
	 * Sets the meeting days and times for the course.
	 * 
	 * @param meetingDays The meeting days for the course as a series of characters.
	 * @param startTime   The starting time for the course.
	 * @param endTime     The ending time for the course.
	 * @throws IllegalArgumentException If meetingDays is null or empty, or if the
	 *                                  combination of meeting days and times is
	 *                                  invalid.
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		if (meetingDays == null || meetingDays.isEmpty()) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		int startHour = startTime / 100;
		int startMin = startTime % 100;
		int endHour = endTime / 100;
		int endMin = endTime % 100;

		if (startHour < 0 || startHour > 23 || startMin < 0 || startMin > 59) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		if (endHour < 0 || endHour > 23 || endMin < 0 || endMin > 59) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		if (endTime < startTime) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		this.meetingDays = meetingDays;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * Returns a string representation of the meeting details of the course. If the
	 * meeting days are arranged, the string "Arranged" is returned. Otherwise, it
	 * includes the days, start time, and end time.
	 * 
	 * @return A string representing the meeting details of the course.
	 */
	public String getMeetingString() {
		if ("A".equals(meetingDays)) {
			return "Arranged";
		} else {
			String daysString = getMeetingDays();
			String startTimeString = getTimeString(startTime);
			String endTimeString = getTimeString(endTime);
			return daysString + " " + startTimeString + "-" + endTimeString;
		}

	}

	/**
	 * Converts a time value to a formatted string representation in 12-hour clock
	 * format.
	 * 
	 * @param time The time value in HHMM format.
	 * @return A string representation of the time in 12-hour clock format (e.g.,
	 *         "2:30 PM").
	 * @throws IllegalArgumentException If the provided time value is invalid.
	 */
	private String getTimeString(int time) {
		int hour = time / 100;
		int minute = time % 100;

		if (hour < 0 || hour >= UPPER_HOUR || minute < 0 || minute >= UPPER_MINUTE) {
			throw new IllegalArgumentException("Invalid time value.");
		}

		hour = (hour == 0) ? 12 : (hour > 12) ? hour - 12 : hour;

		String minuteString = minute < 10 ? "0" + minute : String.valueOf(minute);

		String period = time < 1200 ? "AM" : "PM";

		return hour + ":" + minuteString + period;

	}

	/**
	 * Returns the meeting days of a given Course
	 * 
	 * @return the meeting days of Course
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Returns the start time of a given Course
	 * 
	 * @return the start time of a given Course
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Returns the end time of a given Course
	 * 
	 * @return the end time of a given Course
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Compares the current activity with the provided @possibleConflictingActivity
	 * to determine if there is any conflict. If a conflict is detected,
	 * a @ConflictException is thrown to indicate the conflict. If there is no
	 * conflict, this method returns normally without throwing any exceptions.
	 * 
	 * @param possibleConflictingActivity the activity to check for conflicts with
	 * @throws ConflictException if a conflict is detected between the current
	 *                           activity and the provided activity
	 */
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {

		int possibleStartTime = possibleConflictingActivity.getStartTime();
		int possibleEndTime = possibleConflictingActivity.getEndTime();
		int thisStartTime = this.getStartTime();
		int thisEndTime = this.getEndTime();
		
		if (getMeetingDays().equals("A") && possibleConflictingActivity.getMeetingDays().equals("A")) {

	        return;
	    }

		for (char day : possibleConflictingActivity.getMeetingDays().toCharArray()) {

			if (this.getMeetingDays().contains(String.valueOf(day))) {

				if (thisStartTime <= possibleStartTime && thisEndTime >= possibleEndTime) {
					throw new ConflictException();

				} else if (possibleStartTime <= thisStartTime && possibleEndTime >= thisEndTime) {
					throw new ConflictException();

				} else if (thisStartTime <= possibleStartTime && thisEndTime >= possibleStartTime) {
					throw new ConflictException();

				} else if (possibleStartTime <= thisStartTime && possibleEndTime >= thisStartTime) {
					throw new ConflictException();
				}
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + meetingDays.hashCode();
		result = prime * result + startTime;
		result = prime * result + title.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true;
	    }
	    if (obj == null || getClass() != obj.getClass()) {
	        return false;
	    }
	    Activity other = (Activity) obj;
	    return endTime == other.endTime &&
	           startTime == other.startTime &&
	           meetingDays.equals(other.meetingDays) &&
	           title.equals(other.title);
	}
	
}