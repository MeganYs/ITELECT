package com.example.demo.commandlinerunner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.repository.BorrowerRepository;

@Component 
public class BorrowCommandLineRunner implements CommandLineRunner {

	  @Autowired
	    private BorrowerRepository repository;   

	        
	        @Override
	        public void run(String... args) throws Exception {
	            System.out.println("All Borrowers in Database:");
	            repository.findAll().forEach(System.out::println);
	        }

	}

