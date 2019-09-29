package dev.idion.idionkim.board.batch.jobs;

import dev.idion.idionkim.board.batch.domain.User;
import dev.idion.idionkim.board.batch.domain.enums.UserStatus;
import dev.idion.idionkim.board.batch.jobs.listener.InactiveIJobListener;
import dev.idion.idionkim.board.batch.jobs.listener.InactiveStepListener;
import dev.idion.idionkim.board.batch.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Configuration
public class InactiveUserJobConfig {

	private UserRepository userRepository;

	private final static int CHUNK_SIZE = 15;

	@Bean
	public Job inactiveUserJob(JobBuilderFactory jobBuilderFactory, InactiveIJobListener inactiveIJobListener, Flow inactiveJobFlow) {
		return jobBuilderFactory.get("inactiveUserJob")
				.preventRestart()
				.listener(inactiveIJobListener)
				.start(inactiveJobFlow)
				.end()
				.build();
	}

	@Bean
	public Flow inactiveJobFlow(Step inactiveJobStep) {
		FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("inactiveJobFlow");
		return flowBuilder
				.start(new InactiveJobExecutionDecider())
				.on(FlowExecutionStatus.FAILED.getName()).end()
				.on(FlowExecutionStatus.COMPLETED.getName()).to(inactiveJobStep)
				.end();
	}

	@Bean
	public Step inactiveJobStep(StepBuilderFactory stepBuilderFactory, ListItemReader<User> inactiveUserReader
			, InactiveStepListener inactiveStepListener, TaskExecutor taskExecutor) {
		return stepBuilderFactory.get("inactiveUserStep")
				.<User, User>chunk(CHUNK_SIZE)
				.reader(inactiveUserReader)
				.processor(inactiveUserProcessor())
				.writer(inactiveUserWriter())
				.listener(inactiveStepListener)
				.taskExecutor(taskExecutor)
				.throttleLimit(2)
				.build();
	}

	@Bean(destroyMethod = "")
	@StepScope
	public ListItemReader<User> inactiveUserReader(@Value("#{jobParameters[nowDate]}") Date nowDate, UserRepository userRepository) {
		LocalDateTime now = LocalDateTime.ofInstant(nowDate.toInstant(), ZoneId.systemDefault());
		List<User> inactiveUsers = userRepository.findByUpdatedDateBeforeAndStatusEquals(now.minusYears(1), UserStatus.ACTIVE);
		return new ListItemReader<>(inactiveUsers);
	}

	@Bean
	public TaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor("Batch_Task");
	}

	private ItemProcessor<User, User> inactiveUserProcessor() {
		return User::setInactive;
		/*
		return new ItemProcessor<User, User>() {

			@Override
			public User process(User user) throws Exception {
				return user.setInactive();
			}
		};
		 */
	}

	private ItemWriter<User> inactiveUserWriter() {
		return ((List<? extends User> users) -> userRepository.saveAll(users));
	}
}
