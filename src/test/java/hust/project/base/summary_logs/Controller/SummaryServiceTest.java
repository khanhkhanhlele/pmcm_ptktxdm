package hust.project.base.summary_logs.Controller;

import hust.project.base.employee_subsystem.IHRService;
import hust.project.base.summary_logs.Model.AttendanceRecordRecord;
import hust.project.base.summary_logs.Model.AttendanceRecordRepository;
import hust.project.base.summary_logs.Model.Summary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Nested
class SummaryServiceTest {
    private SummaryService summaryService;

    @BeforeEach
    void setUp() {

        // Initialize SummaryService with necessary dependencies
//         summaryService = new SummaryService(iHRService, attendanceRecordRepository);
    }

    //white box
    @Test
    public void testCalculateTotalLateHours_NoRecordsInPeriod_ShouldReturnZero_WhiteBox() {
        // Arrange
        IHRService mockIHRService = Mockito.mock(IHRService.class);
        AttendanceRecordRepository mockRepo = Mockito.mock(AttendanceRecordRepository.class);
        SummaryService service = new SummaryService(mockIHRService, mockRepo);

        List<AttendanceRecordRecord> records = Arrays.asList();

        // Act
        double totalLateHours = service.calculateTotalLateHours(records, "06-2023");

        // Assert
        assertEquals(0.0, totalLateHours, 0.01); // Expecting 0 hours of total late time
    }
    @Test
    public void testCalculateTotalLateHours_AllRecordsOnTime_ShouldReturnZero_WhiteBox() {
        // Arrange
        IHRService mockIHRService = Mockito.mock(IHRService.class);
        AttendanceRecordRepository mockRepo = Mockito.mock(AttendanceRecordRepository.class);
        SummaryService service = new SummaryService(mockIHRService, mockRepo);
        List<AttendanceRecordRecord> records = Arrays.asList(
                new AttendanceRecordRecord("1", "E01", "F01", "01-06-2023", "08:30"), // On time for morning
                new AttendanceRecordRecord("2", "E01", "F01", "01-06-2023", "13:30")  // On time for afternoon
        );

        // Act
        double totalLateHours = service.calculateTotalLateHours(records, "06-2023");

        // Assert
        assertEquals(0.0, totalLateHours, 0.01); // Expecting 0 hours of total late time
    }

    @Test
    public void testCalculateTotalLateHours_SomeOrAllRecordsLateInMorningSession_WhiteBox() {
        // Arrange
        IHRService mockIHRService = Mockito.mock(IHRService.class);
        AttendanceRecordRepository mockRepo = Mockito.mock(AttendanceRecordRepository.class);
        SummaryService service = new SummaryService(mockIHRService, mockRepo);

        String period = "06-2023";
        List<AttendanceRecordRecord> records = Arrays.asList(
                new AttendanceRecordRecord("1", "E01", "F01", "01-06-2023", "09:00"), // Late morning session (30 mins late)
                new AttendanceRecordRecord("2", "E01", "F01", "01-06-2023", "08:45"), // Late morning session (15 mins late)
                new AttendanceRecordRecord("3", "E01", "F01", "02-06-2023", "08:30"), // On time for morning
                new AttendanceRecordRecord("4", "E01", "F01", "03-06-2023", "09:20")  // Late morning session (50 mins late)
        );

        // Act
        double totalLateHours = service.calculateTotalLateHours(records, period);

        // Assert
        assertEquals(1.58, totalLateHours, 0.01); // Expecting 1.58 hours of total late time (95 minutes)
    }

    @Test
    public void testCalculateTotalLateHours_SomeOrAllRecordsLateInAfternoonSession_WhiteBox() {
        // Arrange
        IHRService mockIHRService = Mockito.mock(IHRService.class);
        AttendanceRecordRepository mockRepo = Mockito.mock(AttendanceRecordRepository.class);
        SummaryService service = new SummaryService(mockIHRService, mockRepo);

        String period = "06-2023";
        List<AttendanceRecordRecord> records = Arrays.asList(
                new AttendanceRecordRecord("1", "E01", "F01", "01-06-2023", "13:45"), // Late afternoon session (15 mins late)
                new AttendanceRecordRecord("2", "E01", "F01", "01-06-2023", "14:00"), // Late afternoon session (30 mins late)
                new AttendanceRecordRecord("3", "E01", "F01", "02-06-2023", "13:30"), // On time for afternoon
                new AttendanceRecordRecord("4", "E01", "F01", "03-06-2023", "14:10")  // Late afternoon session (40 mins late)
        );

        // Act
        double totalLateHours = service.calculateTotalLateHours(records, period);

        // Assert
        assertEquals(1.42, totalLateHours, 0.01); // Expecting 1.42 hours of total late time (85 minutes)
    }
    @Test
    public void testCalculateTotalLateHours_SomeRecordsLateInBoth_WhiteBox() {
        // Arrange
        IHRService mockIHRService = Mockito.mock(IHRService.class);
        AttendanceRecordRepository mockRepo = Mockito.mock(AttendanceRecordRepository.class);
        SummaryService service = new SummaryService(mockIHRService, mockRepo);

        String period = "06-2023";
        List<AttendanceRecordRecord> records = Arrays.asList(
                new AttendanceRecordRecord("1", "E01", "F01", "01-06-2023", "09:15"), // Late morning session (45 mins late)
                new AttendanceRecordRecord("2", "E01", "F01", "01-06-2023", "14:10"), // Late afternoon session (40 mins late)
                new AttendanceRecordRecord("3", "E01", "F01", "02-06-2023", "08:30"), // On time for morning
                new AttendanceRecordRecord("4", "E01", "F01", "02-06-2023", "13:30"), // On time for afternoon
                new AttendanceRecordRecord("5", "E01", "F01", "03-06-2023", "09:05"), // Late morning session (35 mins late)
                new AttendanceRecordRecord("6", "E01", "F01", "03-06-2023", "14:00")  // Late afternoon session (30 mins late)
        );

        // Act
        double totalLateHours = service.calculateTotalLateHours(records, period);

        // Assert
        assertEquals(2.5, totalLateHours, 0.01); // Expecting 2.5 hours of total late time (150 minutes)
    }

    //Blackbox
    @Test
    public void testCalculateTotalLateHours_NoRecords_BlackBox() {
        // Arrange
        IHRService mockIHRService = Mockito.mock(IHRService.class);
        AttendanceRecordRepository mockRepo = Mockito.mock(AttendanceRecordRepository.class);
        SummaryService service = new SummaryService(mockIHRService, mockRepo);
        List<AttendanceRecordRecord> records = Arrays.asList();

        // Act
        double totalLateHours = service.calculateTotalLateHours(records, "06-2023");

        // Assert
        assertEquals(0.0, totalLateHours, 0.01); // Expecting 0 hours of total late time
    }
    @Test
    public void testCalculateTotalLateHours_AllRecordsOnTime_BlackBox() {
        // Arrange
        IHRService mockIHRService = Mockito.mock(IHRService.class);
        AttendanceRecordRepository mockRepo = Mockito.mock(AttendanceRecordRepository.class);
        SummaryService service = new SummaryService(mockIHRService, mockRepo);
        List<AttendanceRecordRecord> records = Arrays.asList(
                new AttendanceRecordRecord("1", "E01", "F01", "01-06-2023", "08:30"), // On time for morning
                new AttendanceRecordRecord("2", "E01", "F01", "01-06-2023", "13:30")  // On time for afternoon
        );

        // Act
        double totalLateHours = service.calculateTotalLateHours(records, "06-2023");

        // Assert
        assertEquals(0.0, totalLateHours, 0.01); // Expecting 0 hours of total late time
    }
    @Test
    public void testCalculateTotalLateHours_RecordsLate_BlackBox() {
        // Arrange
        IHRService mockIHRService = Mockito.mock(IHRService.class);
        AttendanceRecordRepository mockRepo = Mockito.mock(AttendanceRecordRepository.class);
        SummaryService service = new SummaryService(mockIHRService, mockRepo);
        List<AttendanceRecordRecord> records = Arrays.asList(
                new AttendanceRecordRecord("1", "E01", "F01", "01-06-2023", "09:15"), // Late morning session (45 mins late)
                new AttendanceRecordRecord("2", "E01", "F01", "01-06-2023", "14:10")  // Late afternoon session (40 mins late)
        );

        // Act
        double totalLateHours = service.calculateTotalLateHours(records, "06-2023");

        // Assert
        assertEquals(1.42, totalLateHours, 0.01); // Expecting 1.42 hours of total late time
    }
    @Test
    public void testCalculateTotalLateHours_RecordsInAndOutOfPeriod_BlackBox() {
        // Arrange
        IHRService mockIHRService = Mockito.mock(IHRService.class);
        AttendanceRecordRepository mockRepo = Mockito.mock(AttendanceRecordRepository.class);
        SummaryService service = new SummaryService(mockIHRService, mockRepo);
        List<AttendanceRecordRecord> records = Arrays.asList(
                new AttendanceRecordRecord("1", "E01", "F01", "31-05-2023", "09:15"), // Out of period
                new AttendanceRecordRecord("2", "E01", "F01", "01-06-2023", "09:00"), // Late morning session (30 mins late)
                new AttendanceRecordRecord("3", "E01", "F01", "02-07-2023", "14:10")  // Out of period
        );

        // Act
        double totalLateHours = service.calculateTotalLateHours(records, "06-2023");

        // Assert
        assertEquals(0.5, totalLateHours, 0.01); // Expecting 0.5 hours of total late time
    }







}