package dev.idion.idionkim.board.batch.jobs;

import dev.idion.idionkim.board.batch.domain.User;
import dev.idion.idionkim.board.batch.domain.enums.UserStatus;
import dev.idion.idionkim.board.batch.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Configuration
public class InactiveUserJobConfig {

	private UserRepository userRepository;

	@Bean
	public Job inactiveUserJob(JobBuilderFactory jobBuilderFactory, Step inactiveJobStep) {
		return jobBuilderFactory.get("inactiveUserJob").preventRestart().start(inactiveJobStep).build();
	}

	@Bean
	public Step inactiveJobStep(StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory.get("inactiveUserStep").<User, User>chunk(10)
				.reader(inactiveUserReader())
				.processor(inactiveUserProcessor())
				.writer(inactiveUserWriter())
				.build();
	}

	@Bean
	@StepScope
	public ListItemReader<User> inactiveUserReader() {
		List<User> oldUsers = userRepository.findByUpdatedDateBeforeAndStatusEquals(
				LocalDateTime.now().minusYears(1), UserStatus.ACTIVE);
		return new ListItemReader<>(oldUsers);
	}

	public ItemProcessor<User, User> inactiveUserProcessor() {
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

	public ItemWriter<User> inactiveUserWriter() {
		return ((List<? extends User> users) -> userRepository.saveAll(users));
	}
}
