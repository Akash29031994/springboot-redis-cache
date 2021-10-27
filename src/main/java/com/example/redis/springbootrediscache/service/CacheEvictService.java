package com.example.redis.springbootrediscache.service;

import com.example.redis.springbootrediscache.ResouceNotFoundException;
import com.example.redis.springbootrediscache.model.Employee;
import com.example.redis.springbootrediscache.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class CacheEvictService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @CacheEvict(value = "employees", allEntries = true)
    public void evictCache() { }

    public Employee findEmployeeById(@PathVariable(value = "employeeId") Integer employeeId) {
        System.out.println("Employee fetching from database:: "+employeeId);
        return employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResouceNotFoundException("Employee not found" + employeeId));
    }

    @Cacheable(value = "employees",key = "1")
    public Employee reloadCache() {
        return findEmployeeById(1);
    }

}
