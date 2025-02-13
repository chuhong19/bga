package com.example.bga;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BgaApplication implements CommandLineRunner {

	@Value("${server.port}")
	public String serverPort;

	public static void main(String[] args) {
		SpringApplication.run(BgaApplication.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.println("🚀 Server is running on port " + serverPort);
	}

}
