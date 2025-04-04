package com.example.demo.commandlinerunner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.repository.LoanRepository;

@Component
public class LoanCommandLineRunner implements CommandLineRunner {

	  @Autowired
	    private LoanRepository repository;   

	        
	        @Override
	        public void run(String... args) throws Exception {
	            System.out.println("All Loans in Database:");
	            repository.findAll().forEach(System.out::println);
	        }

	}

