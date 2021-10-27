package com.example.redis.springbootrediscache.controller;


import com.example.redis.springbootrediscache.ResouceNotFoundException;
import com.example.redis.springbootrediscache.model.Employee;
import com.example.redis.springbootrediscache.repository.EmployeeRepository;
import com.example.redis.springbootrediscache.service.CacheEvictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CacheEvictService cacheEvictService;

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    /*@GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeRepository.findAll());
    }*/

    @GetMapping("employees")
    @Cacheable(value = "employees")
    public List<Employee> findEmployeeById() {
        return employeeRepository.findAll();
    }

    @PutMapping("employees/{employeeId}")
    @CachePut(value = "employees",key = "#employeeId")
    public Employee updateEmployee(@PathVariable(value = "employeeId") Integer employeeId,
                                                   @RequestBody Employee employeeDetails) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResouceNotFoundException("Employee not found for this id :: " + employeeId));
        employee.setName(employeeDetails.getName());
        System.out.println("Employee updating into database:: "+employeeId);
        final Employee updatedEmployee = employeeRepository.save(employee);
        return updatedEmployee;
    }

    @GetMapping("employees/evict")
    @CacheEvict(value = "employees", allEntries = true)
    public void evictCache() {
    }

    @GetMapping("employees/reload")
    public Employee reloadCache() {
        cacheEvictService.evictCache();
        return cacheEvictService.reloadCache();

    }
}
