package com.lbg.meeting;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MeetingsSchedule {

	private LocalTime officeStartTime;

	private LocalTime officeEndTime;

	private Map<LocalDate, Set<Meeting>> meetings;

	public MeetingsSchedule(LocalTime officeStartTime,
			LocalTime officeEndTime, Map<LocalDate, Set<Meeting>> meetings) {
		this.officeStartTime = officeStartTime;
		this.officeEndTime = officeEndTime;
		this.meetings = meetings;
	}

	public LocalTime getOfficeStartTime() {
		return officeStartTime;
	}

	public LocalTime getOfficeEndTime() {
		return officeEndTime;
	}

	public Map<LocalDate, Set<Meeting>> getMeetings() {
		return meetings;
	}

}