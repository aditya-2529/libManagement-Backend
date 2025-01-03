package com.project.library.Library.controller;

import com.project.library.Library.exception.ResourceNotFoundException;
import com.project.library.Library.model.Employee;
import com.project.library.Library.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @GetMapping
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }
    @GetMapping("{userName}")
    public ResponseEntity<Employee> getUserName(@PathVariable String userName){
        Employee employee = employeeRepository.un(userName)
        .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with userName:" + userName));
        return ResponseEntity.ok(employee);
    }
    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id,@RequestBody Employee employeeDetails) {
        Employee updateEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));

        updateEmployee.setName(employeeDetails.getName());
        updateEmployee.setPassword(employeeDetails.getPassword());
        updateEmployee.setUserName(employeeDetails.getUserName());

        employeeRepository.save(updateEmployee);

        return ResponseEntity.ok(updateEmployee);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable long id){

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));

        employeeRepository.delete(employee);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
