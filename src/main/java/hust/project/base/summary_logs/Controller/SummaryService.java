package hust.project.base.summary_logs.Controller;

import hust.project.base.summary_logs.Model.AttendanceRecordRecord;

import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.time.Duration;

public class SummaryService implements SummaryRepository {
    private LocalTime parseTime(String timeString) {
        return LocalTime.parse(timeString);
    }
    public int calculateTotalSessions(List<AttendanceRecordRecord> records, String period) {
        Map<LocalDate, Integer> workDays = new HashMap<> ();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate periodDate = LocalDate.parse("01-" + period, monthYearFormatter);
        if (period.equals ("12-2023")) {
            System.out.println("done calculate ");
        }
        for (AttendanceRecordRecord record : records) {
            LocalDate recordDate = LocalDate.parse(record.getDate(), dateFormatter);
            if (recordDate.getMonth() == periodDate.getMonth() && recordDate.getYear() == periodDate.getYear()) {
                LocalTime recordTime = LocalTime.parse(record.getTime());
                workDays.putIfAbsent(recordDate, 0);

                if (isValidSession(recordTime, true)) {
                    workDays.put(recordDate, workDays.get(recordDate) | 1); // Set bit 0
                } else if (isValidSession(recordTime, false)) {
                    // Buổi chiều
                    workDays.put(recordDate, workDays.get(recordDate) | 2); // Set bit 1
                }
            }
        }

        int totalSessions = 0;
        for (int sessions : workDays.values()) {
            if (sessions == 3) { // Cả hai buổi làm việc
                totalSessions += 2;
            } else if (sessions > 0) { // Chỉ một buổi làm việc
                totalSessions += 1;
            }
        }
        return totalSessions;
    }


    private boolean isValidSession(LocalTime time, boolean isMorning) {
        LocalTime morningStart = LocalTime.of(7, 30); // 8h30 trừ 1 tiếng
        LocalTime morningEnd = LocalTime.of(12, 30); // Kết thúc buổi sáng
        LocalTime afternoonStart = LocalTime.of(12, 30); // Bắt đầu buổi chiều
        LocalTime afternoonEnd = LocalTime.of(18, 30); // 17h30 cộng 1 tiếng

        if (isMorning) {
            return !time.isBefore(morningStart) && !time.isAfter(morningEnd);
        } else {
            return !time.isBefore(afternoonStart) && !time.isAfter(afternoonEnd);
        }
    }

    public double calculateTotalLateHours(List<AttendanceRecordRecord> records, String period) {
        double totalLateMinutes = 0;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate periodDate = LocalDate.parse("01-" + period, monthYearFormatter);

        LocalTime morningStartTime = LocalTime.of(8, 30); // Giờ bắt đầu buổi sáng
        LocalTime afternoonStartTime = LocalTime.of(13, 30); // Giờ bắt đầu buổi chiều
        LocalTime maxMorningLateTime = LocalTime.of(9, 30); // Giờ tối đa đi muộn
        LocalTime maxAfternoonLateTime = LocalTime.of(14, 30); // Giờ tối đa đi muộn

        for (AttendanceRecordRecord record : records) {
            LocalDate recordDate = LocalDate.parse(record.getDate(), dateFormatter);
            if (recordDate.getMonth() == periodDate.getMonth() && recordDate.getYear() == periodDate.getYear()) {
                LocalTime recordTime = parseTime(record.getTime());
                if (recordTime.isAfter(morningStartTime) && recordTime.isBefore(maxMorningLateTime)){
                    // Đi muộn buổi sáng
                    Duration duration = Duration.between(morningStartTime, recordTime);
                    totalLateMinutes += duration.toMinutes();
//                    System.out.println(recordTime.toString() );
//                    System.out.println("Di muon sang ngay "+ recordDate.toString() +" thoi gian "+duration.toMinutes());
                } else if (recordTime.isAfter(afternoonStartTime)&& recordTime.isBefore(maxAfternoonLateTime)) {
                    // Đi muộn buổi chiều
                    Duration duration = Duration.between(afternoonStartTime, recordTime);
                    totalLateMinutes += duration.toMinutes();
//                    System.out.println(recordTime.toString());
//                    System.out.println(recordTime.toString());
//                    System.out.println("Di muon chieu ngay "+ recordDate.toString() +" thoi gian "+ duration.toMinutes());
                }
            }
        }

        return Math.round((totalLateMinutes / 60) * 100.0) / 100.0;
    }

    public double calculateTotalEarlyDepartures(List<AttendanceRecordRecord> records, String period) {
        double totalEarlyMinutes = 0;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate periodDate = LocalDate.parse("01-" + period, monthYearFormatter);

        LocalTime noonEndTime = LocalTime.of(12, 30); // Giờ kết thúc buổi sáng
        LocalTime eveningEndTime = LocalTime.of(17, 30); // Giờ kết thúc buổi chiều
        LocalTime minNoonEarlyTime = LocalTime.of(11, 30); // Giờ tối thiểu về sớm buổi sáng
        LocalTime minEveningEarlyTime = LocalTime.of(16, 30); // Giờ tối thiểu về sớm buổi chiều

        for (AttendanceRecordRecord record : records) {
            LocalDate recordDate = LocalDate.parse(record.getDate(), dateFormatter);
            if (recordDate.getMonth() == periodDate.getMonth() && recordDate.getYear() == periodDate.getYear()) {
                LocalTime recordTime = parseTime(record.getTime());
                if (recordTime.isBefore(noonEndTime) && recordTime.isAfter(minNoonEarlyTime)) {
                    // Về sớm buổi sáng
                    Duration duration = Duration.between(recordTime, noonEndTime);
                    totalEarlyMinutes += duration.toMinutes();
//                    System.out.println(recordTime.toString());
//                    System.out.println("Ve som sang ngay "+ recordDate.toString() +" thoi gian"+ duration.toMinutes());
                } else if (recordTime.isBefore(eveningEndTime) && recordTime.isAfter(minEveningEarlyTime)){
                    // Về sớm buổi chiều
                    Duration duration = Duration.between(recordTime, eveningEndTime);
                    totalEarlyMinutes += duration.toMinutes();
//                    System.out.println(recordTime.toString());
//                    System.out.println("Ve som chieu ngay "+ recordDate.toString() +" thoi gian "+ duration.toMinutes());
                }
            }
        }
        return  Math.round((totalEarlyMinutes / 60) * 100.0) / 100.0;
    }

}
