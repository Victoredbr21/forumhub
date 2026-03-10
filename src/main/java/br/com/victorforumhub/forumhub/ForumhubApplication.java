package br.com.victorforumhub.forumhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ForumhubApplication {

    public static void main(String[] args) {
        // TEMPORÁRIO — gera hash, copia e apaga depois
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
        SpringApplication.run(ForumhubApplication.class, args);
    }
}

