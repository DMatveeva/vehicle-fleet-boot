package ru.dmatveeva.vehiclefleetboot.service;

import org.springframework.stereotype.Service;
import ru.dmatveeva.vehiclefleetboot.repository.jdbc.JdbcReportRepository;

import java.time.LocalDate;
import java.util.Map;

@Service
public class ReportService {

    JdbcReportRepository jdbcVehicleRepository;

    public ReportService(JdbcReportRepository jdbcVehicleRepository) {
        this.jdbcVehicleRepository = jdbcVehicleRepository;
    }

    public LocalDate getStart(String period, LocalDate start) {
        return switch (period) {
            case "year" -> LocalDate.of(start.getYear(), 1, 1);
            case "month" -> LocalDate.of(start.getYear(), start.getMonth(), 1);
            case "day" -> start;
            default -> throw new IllegalArgumentException("specify period : year, month, day)");
        };
    }

    public LocalDate getEnd(String period, LocalDate end) {
        LocalDate newEnd;
        switch (period) {
            case "year" -> newEnd = LocalDate.of(end.getYear(), 12, 31);
            case "month" -> {
                int lastDayOfMonth = end.getMonth().length(end.isLeapYear());
                newEnd = LocalDate.of(end.getYear(), end.getMonth(), lastDayOfMonth);
            }
            case "day" -> newEnd = end;
            default -> throw new IllegalArgumentException("specify period : year, month, day)");
        }
        return newEnd;
    }

    public Map<String, Integer> getReportResultsForYear(int vehicle_id, LocalDate start, LocalDate end) {
        return jdbcVehicleRepository.getReportResultsForYear(vehicle_id, start, end);
    }

    public Map<String, Integer> getReportResultsForMonth(int vehicle_id, LocalDate start, LocalDate end) {
        return jdbcVehicleRepository.getReportResultsForMonth(vehicle_id, start, end);
    }

    public Map<String, Integer> getReportResultsForDay(int vehicle_id, LocalDate start, LocalDate end) {
        return jdbcVehicleRepository.getReportResultsForDay(vehicle_id, start, end);
    }
}
