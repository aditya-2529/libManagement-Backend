package com.project.library.Library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.project.library.Library.model.Employee;
import com.project.library.Library.repository.EmployeeRepository;

@SpringBootApplication
public class LibraryManagementApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementApplication.class, args);
	}

	@Autowired
	private EmployeeRepository empR;

	@Override
	public void run(String... args) throws Exception {
		Employee emp1 = new Employee();
		emp1.setFirstName("Adi");
		emp1.setLastName("Ranjan");
		emp1.setEmailId("ranjan.aditya@gmail.com");
		emp1.setId(111);
		empR.save(emp1);
	}

}
