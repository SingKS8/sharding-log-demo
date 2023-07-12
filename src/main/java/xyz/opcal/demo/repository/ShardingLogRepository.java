package xyz.opcal.demo.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import xyz.opcal.demo.entity.ShardingLogEntity;

public interface ShardingLogRepository extends JpaRepository<ShardingLogEntity, Long> {

	List<ShardingLogEntity> findByCreateDate(Date createDate);

	List<ShardingLogEntity> findByCreateDateBetween(Date start, Date end);

	Optional<ShardingLogEntity> findByIdAndCreateDate(Long id, Date createDate);
}
