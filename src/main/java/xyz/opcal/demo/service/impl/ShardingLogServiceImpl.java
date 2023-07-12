package xyz.opcal.demo.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import xyz.opcal.demo.entity.ShardingLogEntity;
import xyz.opcal.demo.repository.ShardingLogRepository;
import xyz.opcal.demo.service.ShardingLogService;

@Slf4j
@Transactional
@Service
@AllArgsConstructor
public class ShardingLogServiceImpl implements ShardingLogService {

	private static final String YEAR_MONTH_FORMAT = "yyyyMM";

	private ShardingLogRepository shardingLogRepository;

	@Override
	public ShardingLogEntity save(ShardingLogEntity entity) {
		log.info("insert log in table sharding_log_{}", new SimpleDateFormat(YEAR_MONTH_FORMAT).format(entity.getCreateDate()));
		return shardingLogRepository.save(entity);
	}

	@Override
	public List<ShardingLogEntity> getShardingLog(Date createDate) {
		return shardingLogRepository.findByCreateDate(createDate);
	}

	@Override
	public List<ShardingLogEntity> getShardingLogInRange(Date start, Date end) {
		return shardingLogRepository.findByCreateDateBetween(start, end);
	}

	@Override
	public ShardingLogEntity getShardingLog(Long id, Date createDate) {
		return shardingLogRepository.findByIdAndCreateDate(id, createDate).orElse(null);
	}

}
