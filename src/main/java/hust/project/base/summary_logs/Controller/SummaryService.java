package hust.project.base.summary_logs.Controller;

import hust.project.base.employee_subsystem.Department;
import hust.project.base.employee_subsystem.Employee;
import hust.project.base.employee_subsystem.IHRService;
import hust.project.base.summary_logs.Controller.SummaryRepository;
import hust.project.base.summary_logs.Model.AttendanceRecordEntity;
import hust.project.base.summary_logs.Model.AttendanceRecordRecord;
import hust.project.base.summary_logs.Model.AttendanceRecordRepository;
import hust.project.base.summary_logs.Model.Summary;

import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.time.Duration;

public class SummaryService implements SummaryRepository {
    private IHRService iHRService;
    private AttendanceRecordRepository attendanceRecordRepository;

    private HashMap<String, HashMap<String, Summary>> summaryMap;

    // Constructor
    public SummaryService(IHRService iHRService, AttendanceRecordRepository attendanceRecordRepository) {
        this.iHRService = iHRService;
        this.attendanceRecordRepository = attendanceRecordRepository;
    }


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
                } else if (recordTime.isAfter(afternoonStartTime)&& recordTime.isBefore(maxAfternoonLateTime)) {
                    // Đi muộn buổi chiều
                    Duration duration = Duration.between(afternoonStartTime, recordTime);
                    totalLateMinutes += duration.toMinutes();
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

    public List<Summary> updateTableForYear(String year) {
        List<Summary> summariesForYear = new ArrayList<>();

        for (Map<String, Summary> employeeSummaries : summaryMap.values()) {
            Summary yearlySummary = new Summary("", "", "", "", 0, 0.0, 0.0);
            int totalSessions = 0;
            double totalLateHours = 0.0;
            double totalEarlyDepartures = 0.0;

            for (String period : employeeSummaries.keySet()) {
                if (period.endsWith(year)) {
                    Summary summary = employeeSummaries.get(period);
                    totalSessions += summary.getTotalSessions();
                    totalLateHours += summary.getTotalLateHours();
                    totalEarlyDepartures += summary.getTotalEarlyDepartures();

                    // Thiết lập thông tin nhân viên và phòng ban cho tổng hợp năm
                    yearlySummary.setEmployeeName(summary.getEmployeeName());
                    yearlySummary.setEmployeeId(summary.getEmployeeId());
                    yearlySummary.setDepartmentName(summary.getDepartmentName());
                }
            }

            yearlySummary.setPeriod("Năm " + year);
            yearlySummary.setTotalSessions(totalSessions);
            yearlySummary.setTotalLateHours(Math.round((totalLateHours) * 100.0) / 100.0);
            yearlySummary.setTotalEarlyDepartures(Math.round((totalEarlyDepartures) * 100.0) / 100.0);


            summariesForYear.add(yearlySummary);
        }

        return summariesForYear;
    }

    public List<Summary> updateTableForQuarter(String quarter) {
        List<Summary> summariesForQuarter = new ArrayList<>();

        for (Map<String, Summary> employeeSummaries : summaryMap.values()) {
            Summary quarterlySummary = new Summary("", "", "", "", 0, 0.0, 0.0);
            int totalSessions = 0;
            double totalLateHours = 0.0;
            double totalEarlyDepartures = 0.0;

            for (String period : employeeSummaries.keySet()) {
                if (isPeriodInQuarter(period, quarter)) {
                    Summary summary = employeeSummaries.get(period);
                    totalSessions += summary.getTotalSessions();
                    totalLateHours += summary.getTotalLateHours();
                    totalEarlyDepartures += summary.getTotalEarlyDepartures();

                    // Thiết lập thông tin nhân viên và phòng ban cho tổng hợp quý
                    quarterlySummary.setEmployeeName(summary.getEmployeeName());
                    quarterlySummary.setEmployeeId(summary.getEmployeeId());
                    quarterlySummary.setDepartmentName(summary.getDepartmentName());
                }
            }

            quarterlySummary.setPeriod(quarter);
            quarterlySummary.setTotalSessions(totalSessions);
            quarterlySummary.setTotalLateHours(Math.round((totalLateHours) * 100.0) / 100.0);
            quarterlySummary.setTotalEarlyDepartures(Math.round((totalEarlyDepartures) * 100.0) / 100.0);
            summariesForQuarter.add(quarterlySummary);
        }

        return summariesForQuarter;
    }

    private boolean isPeriodInQuarter(String period, String quarter) {
        String[] periodParts = period.split("-");
        int periodMonth = Integer.parseInt(periodParts[0]);
        int periodYear = Integer.parseInt(periodParts[1]);

        String[] quarterParts = quarter.split(" - ");
        String quarterYear = quarterParts[1];
        int quarterNumber = Integer.parseInt(quarterParts[0].split(" ")[1]);

        int quarterStartMonth = (quarterNumber - 1) * 3 + 1; // Tháng bắt đầu của quý
        int quarterEndMonth = quarterStartMonth + 2; // Tháng kết thúc của quý

        return periodYear == Integer.parseInt(quarterYear) && periodMonth >= quarterStartMonth && periodMonth <= quarterEndMonth;
    }

    public HashMap<String, HashMap<String, Summary>> loadSummaryData() {
        this.summaryMap = new HashMap<>();
        List<Employee> allEmployees = iHRService.getAllEmployee();

        for (Employee employee : allEmployees) {
            String employeeId = employee.getEmployeeId();
            HashMap<String, Summary> employeeSummaries = new HashMap<>();

            for (String period : getAllPeriods()) {
                List<AttendanceRecordRecord> records = attendanceRecordRepository.getAttendanceRecordByEmployeeId(employeeId);
                int totalSessions = calculateTotalSessions(records, period);
                double totalLateHours = calculateTotalLateHours(records, period);
                double totalEarlyDepartures = calculateTotalEarlyDepartures(records, period);

                Department department = iHRService.getDepartmentById(employee.getDepartmentId());
                String departmentName = (department != null) ? department.getDepartmentName() : "Unknown";

                Summary summary = new Summary(employee.getName(), employeeId, departmentName, period, totalSessions, totalLateHours, totalEarlyDepartures);
                employeeSummaries.put(period, summary);
            }

            summaryMap.put(employeeId, employeeSummaries);
        }

        return summaryMap;
    }

    public String[] getAllPeriods() {
        LocalDate startDate = attendanceRecordRepository.getStartDateFromDB();
        LocalDate endDate = LocalDate.now();
        List<String> periods = new ArrayList<>();

        while (startDate.isBefore(endDate) || startDate.isEqual(endDate)) {
            periods.add(startDate.format(DateTimeFormatter.ofPattern("MM-yyyy")));
            startDate = startDate.plusMonths(1);
        }

        return periods.toArray(new String[0]);
    }


}
