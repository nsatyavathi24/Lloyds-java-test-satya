# Lloyds-java-test-satya

Assignment:
Your company has an existing system for employees to submit booking requests for meetings in the 
boardroom. Your employer has now asked you to implement a system for processing batches of 
booking requests.

1. Input
Your processing system must process input as text. The first line of the input paylod
represents the company office hours, in 24 hour clock format (ignore timezones), and the 
remainder of the input represents individual booking requests. Each booking request is in 
the following format:
[Request submission time, in YYYY-MM-DD HH:MM:SS format] [Employee id]
[Meeting start time, in YYYY-MM-DD HH:MM format] [Meeting duration in hours]

A sample text input follows, to be used in your solution:
0900 1730
2016-07-18 10:17:06 EMP001
2016-07-21 09:00 2
2016-07-18 12:34:56 EMP002
2016-07-21 09:00 2
2016-07-18 09:28:23 EMP003
2016-07-22 14:00 2
2016-07-18 11:23:45 EMP004
2016-07-22 16:00 1
2016-07-15 17:29:12 EMP005
2016-07-21 16:00 3
2. Output
Your system must output a list of bookings in a specific format; with booking grouped 
chronologically by day. For the sample input displayed above, your system must provide the 
following output
2011-03-21
09:00 11:00 EMP001
2011-03-22
14:00 16:00 EMP003
16:00 17:00 EMP004
Classification: Limited

3. Booking rules
- No part of a meeting may fall outside of office hours (9:00 to 18:00).
- Meetings may not overlap.
- The booking submission system only allows one submission at a time, so submission 
times are guaranteed to be unique.
- Booking must be processed in the chronological order in which they were submitted.
- The ordering of booking submissions in the supplied input is not guaranteed.
- 
4. Notes
- Although the system that you produce may open and parse a text file for input, this is 
not part of the requirements. As long as the input text is in the correct format, the 
method of input is up to the developer.
Please read the instructions carefully and get in touch with us before the test if you have any 
queries. 
The expected time to complete this test is 3 hours, but you can use more time if you need it to 
complete the task. Please make sure all code is checked at the end of the session and submit any 
extra documentation you would like to share

Instructions to the candidate:
1. Before starting the test, please follow the steps 2 to 3 to prepare your development 
environment for this test.
2. Set up a local git repo called Lloyds-java-test-<name> where name is your own name. 
3. Make sure you commit your codes as you progress.
4. Zip the git repo on completion, and or share it over Github.com
5. All the java classes must have unit tests associated.
6. We will check production-quality coding like naming conventions, coding style, sensible 
design and meaningful commenting.
