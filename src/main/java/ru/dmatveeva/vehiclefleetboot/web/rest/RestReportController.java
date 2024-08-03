package ru.dmatveeva.vehiclefleetboot.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dmatveeva.vehiclefleetboot.repository.jdbc.JdbcReportRepository;
import ru.dmatveeva.vehiclefleetboot.to.MileageReport;
import ru.dmatveeva.vehiclefleetboot.to.Report;
import ru.dmatveeva.vehiclefleetboot.util.DateUtils;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping(value = RestReportController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestReportController {

    static final String REST_URL = "/rest/report";

    @Autowired
    public JdbcReportRepository reportRepository;

    @PostMapping("/generate")
    public Report generate(@RequestBody MileageReport requestReport) {
        String period = requestReport.getPeriod();
        LocalDate newStart = DateUtils.getStartForReport(period, requestReport.getStart());
        LocalDate newEnd = DateUtils.getEndForReport(period, requestReport.getEnd());
        Map<String, Integer> reportResults = switch (period) {
            case "year" -> reportRepository.getReportResultsForYear(requestReport.getVehicleId(), newStart, newEnd);
            case "month" -> reportRepository.getReportResultsForMonth(requestReport.getVehicleId(), newStart, newEnd);
            case "day" -> reportRepository.getReportResultsForDay(requestReport.getVehicleId(), newStart, newEnd);
            default -> throw new IllegalArgumentException("specify period : year, month, day)");
        };
        Report report = new MileageReport(reportResults, requestReport.getType(), requestReport.getPeriod(),
                newStart, newEnd, requestReport.getVehicleId());
        report.setPeriodToValueKm(reportResults);
        return report;
    }
}