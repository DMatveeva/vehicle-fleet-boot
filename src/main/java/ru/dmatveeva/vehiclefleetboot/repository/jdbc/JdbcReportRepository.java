package ru.dmatveeva.vehiclefleetboot.repository.jdbc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Repository
@Transactional(readOnly = true)
public class JdbcReportRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcReportRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Integer> getReportResultsForYear(int id, LocalDate start, LocalDate end) {
        Map<String, Integer> reportResults = new TreeMap<>(Comparator.reverseOrder());
        List<Map<String, Object>> queryResults = jdbcTemplate.queryForList("select y, cast (round(sum(dist)) as INTEGER) s " +
                "from( " +
                "select *, " +
                "       ST_Distance( " +
                "               t.position::geometry, " +
                "               t.lead::geometry, " +
                "               true) / 1000 dist " +
                "from( " +
                "SELECT position, visited, " +
                "       to_char(visited, 'yyyy') y, " +
                "       LEAD(position, 1) OVER (ORDER BY visited) AS lead " +
                "FROM vehicle_coordinates " +
                "where vehicle_id = ? " +
                "and visited >= ? and visited <= ?) t) t1 " +
                "group by y", id, start, end);
        for (Map m : queryResults) {
            reportResults.put((String) m.get("y"), (Integer) m.get("s"));
        }
        return reportResults;
    }

    public Map<String, Integer> getReportResultsForMonth(int id, LocalDate start, LocalDate end) {
        Map<String, Integer> reportResults = new TreeMap<>(Comparator.reverseOrder());
        List<Map<String, Object>> queryResults = jdbcTemplate.queryForList("select m, cast (round(sum(dist)) as INTEGER) s " +
                "from( " +
                "select *, " +
                "       ST_Distance( " +
                "               t.position::geometry, " +
                "               t.lead::geometry, " +
                "               true) / 1000 dist " +
                "from( " +
                "SELECT position, visited, " +
                "       to_char(visited, 'yyyy-MM') m, " +
                "       LEAD(position, 1) OVER (ORDER BY visited) AS lead " +
                "FROM vehicle_coordinates " +
                "where vehicle_id = ? " +
                "and visited >= ? and visited <= ?) t) t1 " +
                "group by m", id, start, end);
        for (Map m : queryResults) {
            reportResults.put((String) m.get("m"), (Integer) m.get("s"));
        }
        return reportResults;
    }

    public Map<String, Integer> getReportResultsForDay(int id, LocalDate start, LocalDate end) {
        Map<String, Integer> reportResults = new TreeMap<>(Comparator.reverseOrder());
        List<Map<String, Object>> queryResults = jdbcTemplate.queryForList("select d, cast (round(sum(dist)) as INTEGER) s " +
                "from( " +
                "select *, " +
                "       ST_Distance( " +
                "               t.position::geometry, " +
                "               t.lead::geometry, " +
                "               true) / 1000 dist " +
                "from( " +
                "SELECT position, visited, " +
                "       to_char(visited, 'yyyy-MM-dd') d, " +
                "       LEAD(position, 1) OVER (ORDER BY visited) AS lead " +
                "FROM vehicle_coordinates " +
                "where vehicle_id = ? " +
                "and visited >= ? and visited <= ?) t) t1 " +
                "group by d", id, start, end);
        for (Map m : queryResults) {
            reportResults.put((String) m.get("d"), (Integer) m.get("s"));
        }
        return reportResults;
    }
}

