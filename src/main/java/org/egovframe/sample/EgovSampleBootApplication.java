package org.egovframe.sample;

import org.egovframe.sample.domain.Sample;
import org.egovframe.sample.repository.SampleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EgovSampleBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(EgovSampleBootApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(SampleRepository repository) {
        return args -> {
            repository.save(Sample.builder()
                .name("공지사항1")
                .description("첫 번째 공지사항입니다.")
                .useYn("Y")
                .regUser("admin")
                .build());

            repository.save(Sample.builder()
                .name("공지사항2")
                .description("두 번째 공지사항입니다.")
                .useYn("Y")
                .regUser("admin")
                .build());

            repository.save(Sample.builder()
                .name("이벤트1")
                .description("첫 번째 이벤트입니다.")
                .useYn("Y")
                .regUser("admin")
                .build());

            repository.save(Sample.builder()
                .name("이벤트2")
                .description("두 번째 이벤트입니다.")
                .useYn("N")
                .regUser("admin")
                .build());

            repository.save(Sample.builder()
                .name("FAQ1")
                .description("자주 묻는 질문1")
                .useYn("Y")
                .regUser("admin")
                .build());

            repository.save(Sample.builder()
                .name("FAQ2")
                .description("자주 묻는 질문2")
                .useYn("Y")
                .regUser("admin")
                .build());

            repository.save(Sample.builder()
                .name("뉴스1")
                .description("첫 번째 뉴스입니다.")
                .useYn("Y")
                .regUser("admin")
                .build());

            repository.save(Sample.builder()
                .name("뉴스2")
                .description("두 번째 뉴스입니다.")
                .useYn("N")
                .regUser("admin")
                .build());

            repository.save(Sample.builder()
                .name("안내1")
                .description("첫 번째 안내사항입니다.")
                .useYn("Y")
                .regUser("admin")
                .build());

            repository.save(Sample.builder()
                .name("안내2")
                .description("두 번째 안내사항입니다.")
                .useYn("Y")
                .regUser("admin")
                .build());
        };
    }
} 