package com.lbg.meeting;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.*;

import static java.lang.Integer.parseInt;

public class MeetingScheduler {

	private DateTimeFormatter dateFormatter = DateTimeFormat
			.forPattern("yyyy-MM-dd");
	private DateTimeFormatter separatedTimeFormatter = DateTimeFormat
			.forPattern("HH:mm");

	/**
	 * 
	 * @param meetingRequest
	 * @return
	 * @throws InvalidBookingRequestException
	 */
	public MeetingsSchedule schedule(String meetingRequest) {
		try {
			if (meetingRequest == null || meetingRequest.isEmpty()) {
				System.err
						.println(" Employee has requested for booking is not a valid input");
				//return null;
			}

			String[] requestLines = meetingRequest.split("\n");

			String[] officeHoursTokens = requestLines[0].split(" ");
			LocalTime officeStartTime = new LocalTime(
					parseInt(officeHoursTokens[0].substring(0, 2)),
					parseInt(officeHoursTokens[0].substring(2, 4)));
			LocalTime officeEndime = new LocalTime(
					parseInt(officeHoursTokens[1].substring(0, 2)),
					parseInt(officeHoursTokens[1].substring(2, 4)));

			Map<LocalDate, Set<Meeting>> meetings = new HashMap<LocalDate, Set<Meeting>>();

			for (int i = 1; i < requestLines.length; i = i + 2) {

				String[] meetingSlotRequest = requestLines[i + 1].split(" ");
				LocalDate meetingDate = dateFormatter
						.parseLocalDate(meetingSlotRequest[0]);

				Meeting meeting = extractMeeting(requestLines[i],
						officeStartTime, officeEndime, meetingSlotRequest);
				if (meeting != null) {
					if (meetings.containsKey(meetingDate)) {
						meetings.get(meetingDate).remove(meeting);
						meetings.get(meetingDate).add(meeting);
					} else {
						Set<Meeting> meetingsForDay = new TreeSet<Meeting>();
						meetingsForDay.add(meeting);
						meetings.put(meetingDate, meetingsForDay);
					}
				}
			}

			return new MeetingsSchedule(officeStartTime, officeEndime,
					meetings);
		} catch (Exception e) {
			return null;
		}

	}

	private Meeting extractMeeting(String requestLine,
			LocalTime officeStartTime, LocalTime officeEndTime,
			String[] meetingSlotRequest) {
		String[] employeeRequest = requestLine.split(" ");
		String employeeId = employeeRequest[2];

		LocalTime meetingStartTime = separatedTimeFormatter
				.parseLocalTime(meetingSlotRequest[1]);
		LocalTime meetingEndTime = new LocalTime(
				meetingStartTime.getHourOfDay(),
				meetingStartTime.getMinuteOfHour())
				.plusHours(parseInt(meetingSlotRequest[2]));

		if (meetingTimeOutsideOfficeHours(officeStartTime, officeEndTime,
				meetingStartTime, meetingEndTime)) {
			System.err.println("EmployeeId:: " + employeeId
					+ " has requested booking which is outside office hour.");
			return null;
		} else {
			return new Meeting(employeeId, meetingStartTime, meetingEndTime);

		}
	}

	private boolean meetingTimeOutsideOfficeHours(LocalTime officeStartTime,
			LocalTime officeEndTime, LocalTime meetingStartTime,
			LocalTime meetingEndTime) {
		return meetingStartTime.isBefore(officeStartTime)
				|| meetingStartTime.isAfter(officeEndTime)
				|| meetingEndTime.isAfter(officeEndTime)
				|| meetingEndTime.isBefore(officeStartTime);
	}
}