package xyz.opcal.demo.service;

import java.util.Date;
import java.util.List;

import xyz.opcal.demo.entity.ShardingLogEntity;

public interface ShardingLogService {

	ShardingLogEntity save(ShardingLogEntity entity);

	List<ShardingLogEntity> getShardingLog(Date createDate);

	ShardingLogEntity getShardingLog(Long id, Date createDate);

	List<ShardingLogEntity> getShardingLogInRange(Date start, Date end);
}
