SELECT count(share_no) as count, jyaq FROM ShareDB.share_results_20170301 group by jyaq union all
SELECT count(share_no) as count, jyaq FROM ShareDB.share_results_20170302 group by jyaq ;