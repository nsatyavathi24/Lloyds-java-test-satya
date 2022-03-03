package com.lbg.meeting;

import static org.junit.Assert.assertEquals;

import org.joda.time.LocalDate;
import org.junit.Test;

public class TestMeetingSchedule{

	private String meetingRequest;

	private MeetingScheduler meetingScheduler;
	private MeetingScheduleOutputGenerator outputGenerator;

	@org.junit.Before
	public void setUp() throws Exception {
		meetingScheduler = new MeetingScheduler();
		outputGenerator = new MeetingScheduleOutputGenerator(new MeetingScheduler());

	}

	@Test
	public void shouldParseOfficeHours() {
		meetingRequest = "0900 1730\n";

		MeetingsSchedule bookings = meetingScheduler.schedule(meetingRequest);
		assertEquals(bookings.getOfficeStartTime().getHourOfDay(), 9);
		assertEquals(bookings.getOfficeStartTime().getMinuteOfHour(), 0);
		assertEquals(bookings.getOfficeEndTime().getHourOfDay(), 17);
		assertEquals(bookings.getOfficeEndTime().getMinuteOfHour(), 30);
	}

	@Test
	public void shouldParseMeetingRequest() {
		meetingRequest = "0900 1730\n" + "2016-07-18 10:17:06 EMP001\n"
				+ "2016-07-21 09:00 2\n";
		MeetingsSchedule bookings = meetingScheduler.schedule(meetingRequest);

		LocalDate meetingDate = new LocalDate(2016, 7, 21);

		assertEquals(1, bookings.getMeetings().get(meetingDate).size());
		Meeting meeting = bookings.getMeetings().get(meetingDate)
				.toArray(new Meeting[0])[0];
		assertEquals("EMP001", meeting.getEmployeeId());
		assertEquals(9, meeting.getStartTime().getHourOfDay());
		assertEquals(0, meeting.getStartTime().getMinuteOfHour());
		assertEquals(11, meeting.getEndTime().getHourOfDay());
		assertEquals(0, meeting.getEndTime().getMinuteOfHour());
	}

	@Test
	public void noPartOfMeetingMayFallOutsideOfficeHours() {
		meetingRequest = "0900 1730\n" + "2016-07-15 17:29:12 EMP005\n"
				+ "2016-07-21 16:00 3\n";
		MeetingsSchedule bookings = meetingScheduler.schedule(meetingRequest);

		assertEquals(0, bookings.getMeetings().size());

	}

	@Test
	public void shouldProcessMeetingsInChronologicalOrderOfSubmission() {
		meetingRequest = "0900 1730\n" + "2016-07-18 10:17:06 EMP001\n"
				+ "2016-07-21 09:00 2\n" + "2016-07-18 12:34:56 EMP002\n"
				+ "2016-07-21 09:00 2\n";

		MeetingsSchedule bookings = meetingScheduler.schedule(meetingRequest);

		LocalDate meetingDate = new LocalDate(2016, 7, 21);

		assertEquals(1, bookings.getMeetings().get(meetingDate).size());
		Meeting meeting = bookings.getMeetings().get(meetingDate)
				.toArray(new Meeting[0])[0];
		assertEquals("EMP002", meeting.getEmployeeId());
		assertEquals(9, meeting.getStartTime().getHourOfDay());
		assertEquals(0, meeting.getStartTime().getMinuteOfHour());
		assertEquals(11, meeting.getEndTime().getHourOfDay());
		assertEquals(0, meeting.getEndTime().getMinuteOfHour());
	}

	@Test
	public void shouldGroupMeetingsChronologically() {
		meetingRequest = "0900 1730\n" + "2016-07-18 10:17:06 EMP004\n"
				+ "2016-07-22 16:00 1\n" + "2016-07-18 09:28:23 EMP003\n"
				+ "2016-07-22 14:00 2\n";

		MeetingsSchedule bookings = meetingScheduler.schedule(meetingRequest);
		LocalDate meetingDate = new LocalDate(2016, 7, 22);

		assertEquals(1, bookings.getMeetings().size());
		assertEquals(2, bookings.getMeetings().get(meetingDate).size());
		Meeting[] meetings = bookings.getMeetings().get(meetingDate)
				.toArray(new Meeting[0]);

		assertEquals("EMP003", meetings[0].getEmployeeId());
		assertEquals(14, meetings[0].getStartTime().getHourOfDay());
		assertEquals(0, meetings[0].getStartTime().getMinuteOfHour());
		assertEquals(16, meetings[0].getEndTime().getHourOfDay());
		assertEquals(0, meetings[0].getEndTime().getMinuteOfHour());

		assertEquals("EMP004", meetings[1].getEmployeeId());
		assertEquals(16, meetings[1].getStartTime().getHourOfDay());
		assertEquals(0, meetings[1].getStartTime().getMinuteOfHour());
		assertEquals(17, meetings[1].getEndTime().getHourOfDay());
		assertEquals(0, meetings[1].getEndTime().getMinuteOfHour());
	}

	@Test
	public void meetingsShouldNotOverlap() {
		meetingRequest = "0900 1730\n" + "2016-07-18 10:17:06 EMP001\n"
				+ "2016-07-21 09:00 2\n" + "2016-07-18 12:34:56 EMP002\n"
				+ "2016-07-21 10:00 1\n";

		MeetingsSchedule bookings = meetingScheduler.schedule(meetingRequest);
		LocalDate meetingDate = new LocalDate(2016, 7, 21);

		assertEquals(1, bookings.getMeetings().size());
		assertEquals(1, bookings.getMeetings().get(meetingDate).size());
		Meeting[] meetings = bookings.getMeetings().get(meetingDate)
				.toArray(new Meeting[0]);
		assertEquals("EMP002", meetings[0].getEmployeeId());
		assertEquals(10, meetings[0].getStartTime().getHourOfDay());
		assertEquals(0, meetings[0].getStartTime().getMinuteOfHour());
		assertEquals(11, meetings[0].getEndTime().getHourOfDay());
		assertEquals(0, meetings[0].getEndTime().getMinuteOfHour());
	}

	@Test
	public void emptyInputDataShouldEndWithNull() {
		meetingRequest = null;
		MeetingsSchedule bookings = meetingScheduler.schedule(meetingRequest);
		assertEquals(null, bookings);
	}

	@Test
	public void invalidInputDataShouldEndWithNull() {
		meetingRequest = "0900 1730\n" + "2016-07-18 10:17:06 EMP001\n";
		MeetingsSchedule bookings = meetingScheduler.schedule(meetingRequest);
		
		assertEquals(null, bookings);
	}

	
	 @Test
	public void shouldPrintMeetingSchedule() {
		String meetingRequest = "0900 1730\n" + "2016-07-18 10:17:06 EMP001\n"
				+ "2016-07-21 09:00 2\n" + "2016-07-18 12:34:56 EMP002\n"
				+ "2016-07-21 09:00 2\n" + "2016-07-18 09:28:23 EMP003\n"
				+ "2016-07-22 14:00 2\n" + "2016-07-18 10:17:06 EMP004\n"
				+ "2016-07-22 16:00 1\n" + "2016-07-15 17:29:12 EMP005\n"
				+ "2016-07-21 16:00 3\n";

		String actualoutput = outputGenerator.print(meetingRequest);

		String expectedOutput = "2016-07-21\n" + "09:00 11:00 EMP001\n"
				 + "2016-07-22\n" + "14:00 16:00 EMP003\n"
				 + "16:00 17:00 EMP004\n";

		assertEquals(actualoutput, expectedOutput);

	} 


}
