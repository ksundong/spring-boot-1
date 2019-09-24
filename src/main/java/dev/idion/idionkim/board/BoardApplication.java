package dev.idion.idionkim.board;

import dev.idion.idionkim.board.resolver.UserArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
public class BoardApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}

	@Autowired
	private UserArgumentResolver userArgumentResolver;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(userArgumentResolver);
	}

//	@Bean
//	public CommandLineRunner runner(UserRepository userRepository
//			, BoardRepository boardRepository) throws Exception {
//		return (args) -> {
//			User user = userRepository.save(User.builder()
//				.name("havi")
//				.password("test")
//				.email("havi@gmail.com")
//				.createdDate(LocalDateTime.now())
//				.build());
//
//			IntStream.rangeClosed(1, 200).forEach(index ->
//					boardRepository.save(Board.builder()
//						.title("게시글" + index)
//						.subTitle("순서" + index)
//						.content("콘텐츠")
//						.boardType(BoardType.free)
//						.createdDate(LocalDateTime.now())
//						.updatedDate(LocalDateTime.now())
//						.user(user).build())
//			);
//		};
//	}

}
