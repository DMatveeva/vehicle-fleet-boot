package ru.dmatveeva.vehiclefleetboot.web.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.dmatveeva.vehiclefleetboot.repository.jdbc.JdbcReportRepository;
import ru.dmatveeva.vehiclefleetboot.service.ReportService;
import ru.dmatveeva.vehiclefleetboot.to.MileageReport;
import ru.dmatveeva.vehiclefleetboot.to.Report;
import ru.dmatveeva.vehiclefleetboot.util.DateUtils;

import java.time.LocalDate;
import java.util.Map;

@Controller
@RequestMapping("/report")

public class ReportController {

    public final JdbcReportRepository reportRepository;
    public final ReportService reportService;

    public ReportController(JdbcReportRepository reportRepository, ReportService reportService) {
        this.reportRepository = reportRepository;
        this.reportService = reportService;
    }

    @PostMapping("/generate")
    public String generate(@RequestParam("type") String type,
                           @RequestParam("period") String period,
                           @RequestParam("start") String start,
                           @RequestParam("end") String end,
                           @RequestParam("vehicleId") String vehicleIdS,
                           Model model) {

        int vehicleId = Integer.parseInt(vehicleIdS);

        LocalDate newStart = reportService.getStart(period, DateUtils.getLocalDateFromString(start));
        LocalDate newEnd = reportService.getEnd(period, DateUtils.getLocalDateFromString(end));
        Map<String, Integer> reportResults = switch (period) {
            case "year" -> reportRepository.getReportResultsForYear(vehicleId, newStart, newEnd);
            case "month" -> reportRepository.getReportResultsForMonth(vehicleId, newStart, newEnd);
            case "day" -> reportRepository.getReportResultsForDay(vehicleId, newStart, newEnd);
            default -> throw new IllegalArgumentException("specify period : year, month, day)");
        };
        Report report = new MileageReport(reportResults, type, period, newStart, newEnd, vehicleId);
        report.setPeriodToValueKm(reportResults);

        model.addAttribute("reportResults", reportResults);
        return "report.html";
    }
}
