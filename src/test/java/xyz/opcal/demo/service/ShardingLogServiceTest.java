package xyz.opcal.demo.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import xyz.opcal.demo.entity.ShardingLogEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ShardingLogServiceTest {

	private static final String[] LEVELS = new String[] { "TRACE", "DEBUG", "INFO", "WARN", "ERROR" };
	private static final Random random = new Random();

	static ShardingLogEntity random() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2023, random.nextInt(12), random.nextInt(29) + 1);
		Date createDate = Date.from(calendar.toInstant().truncatedTo(ChronoUnit.SECONDS));
		String log = "mock log " + System.currentTimeMillis() + " " + RandomStringUtils.randomAlphabetic(random.nextInt(30) + 10);
		return create(createDate, log, LEVELS[random.nextInt(LEVELS.length)]);
	}

	static ShardingLogEntity create(Date createDate, String log, String level) {
		ShardingLogEntity entity = new ShardingLogEntity();
		entity.setCreateDate(createDate);
		entity.setLog(log);
		entity.setLevel(level);
		return entity;
	}

	@Autowired
	ShardingLogService shardingLogService;

	@Test
	void save() {
		assertDoesNotThrow(() -> shardingLogService.save(random()));
	}

}
