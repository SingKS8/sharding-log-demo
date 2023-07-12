package xyz.opcal.demo.sharding.fixture;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateMonthlyShardingAlgorithm implements StandardShardingAlgorithm<Date> {

	private static final String YEAR_MONTH_FORMAT = "yyyyMM";

	private Properties props;

	public Properties getProps() {
		return props;
	}

	@Override
	public void init(Properties props) {
		this.props = props;
	}

	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {
		String yearMonth = getYearMonth(shardingValue.getValue());
		log.info("doSharding shardingValue [{}] yearMonth [{}].", shardingValue.getValue(), yearMonth);
		return availableTargetNames.stream().filter(tableName -> StringUtils.endsWith(tableName, yearMonth)).findAny()
				.orElseThrow(() -> new IllegalArgumentException("can not find any table match this month " + yearMonth));
	}

	@Override
	public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Date> shardingValue) {
		final Date lowerEndpoint = shardingValue.getValueRange().lowerEndpoint();
		final Date upperEndpoint = shardingValue.getValueRange().upperEndpoint();

		Date temp = lowerEndpoint;
		Set<String> suffixSet = new TreeSet<>();
		while (temp.before(upperEndpoint) || temp.equals(upperEndpoint)) {
			suffixSet.add(getYearMonth(temp));
			temp = DateUtils.addMonths(temp, 1);
		}
		log.info("doSharding range lowerEndpoint [{}] upperEndpoint [{}] all month suffix [{}]", lowerEndpoint, upperEndpoint, suffixSet);
		return availableTargetNames.stream().filter(tableName -> tableInRange(suffixSet, tableName)).collect(Collectors.toList());
	}

	private String getYearMonth(Date date) {
		return new SimpleDateFormat(YEAR_MONTH_FORMAT).format(date);
	}

	private boolean tableInRange(Set<String> suffixSet, String tableName) {
		return suffixSet.stream().anyMatch(suffix -> StringUtils.endsWith(tableName, suffix));
	}

}
