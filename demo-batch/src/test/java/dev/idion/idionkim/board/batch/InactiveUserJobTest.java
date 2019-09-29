package dev.idion.idionkim.board.batch;

import dev.idion.idionkim.board.batch.domain.enums.UserStatus;
import dev.idion.idionkim.board.batch.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InactiveUserJobTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void inactiveUserTransformTest() throws Exception {
		Date nowDate = new Date();
		JobExecution jobExecution = jobLauncherTestUtils.launchJob(
				new JobParametersBuilder().addDate("nowDate", nowDate).toJobParameters()
		);

		assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
		assertEquals(0, userRepository.findByUpdatedDateBeforeAndStatusEquals(
				LocalDateTime.now().minusYears(1), UserStatus.ACTIVE).size());
	}
}
