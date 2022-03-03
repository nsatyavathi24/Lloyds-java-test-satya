package com.lbg.meeting;
import org.joda.time.Interval;
import org.joda.time.LocalTime;


public class Meeting implements Comparable<Meeting> {

	private String employeeId;

	private LocalTime meetingStartTime;

	private LocalTime meetingEndTime;

	public Meeting(String employeeId, LocalTime startTime, LocalTime endTime) {
		this.employeeId = employeeId;
		this.meetingStartTime = startTime;
		this.meetingEndTime = endTime;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public LocalTime getStartTime() {
		return meetingStartTime;
	}

	public LocalTime getEndTime() {
		return meetingEndTime;
	}

	public int compareTo(Meeting that) {
		Interval meetingInterval = new Interval(meetingStartTime.toDateTimeToday(),
				meetingStartTime.toDateTimeToday());
		Interval toCompareMeetingInterval = new Interval(that.getStartTime()
				.toDateTimeToday(), that.getEndTime().toDateTimeToday());

		if (meetingInterval.overlaps(toCompareMeetingInterval)) {
			return 0;
		} else {
			return this.getStartTime().compareTo(that.getStartTime());
		}

	}
}
