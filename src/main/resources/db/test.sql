select y, round(sum(dist))
from(
select *,
       ST_Distance(
               t.position::geometry,
               t.lead::geometry,
               true) / 1000 dist
from(
SELECT position, visited,
       to_char(visited, 'yyyy') y,
       LEAD(position, 1) OVER (ORDER BY visited) AS lead
FROM vehicle_coordinates
where vehicle_id = 100005) t) t1
group by y


