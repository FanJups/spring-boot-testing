package net.javaguides.springboot.service;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.service.impl.EmployeeServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTests {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup(){
        //employeeRepository = Mockito.mock(EmployeeRepository.class);
        //employeeService = new EmployeeServiceImpl(employeeRepository);

        employee = Employee.builder()
                .id(1L)
                .firstName("Fan")
                .lastName("Jups")
                .email("fjk@gmail.com")
                .build();

    }

    //Junit test for saveEmployee method
    @DisplayName("Junit test for saveEmployee method")
    @Test
    void givenEmployeeObject_whenSaveEmployee_thenReturnSavedEmployee() {
        //given - precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());

        given(employeeRepository.save(employee)).willReturn(employee);

        //when - action or the behaviour that we are going to test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    //Junit test for saveEmployee method
    @DisplayName("Junit test for saveEmployee method which throws exception")
    @Test
    void givenEmployeeObject_whenSaveEmployee_thenThrowsException() {
        //given - precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

        //given(employeeRepository.save(employee)).willReturn(employee);

        //when - action or the behaviour that we are going to test
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {employeeService.saveEmployee(employee);});

        //then - verify the output
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    //Junit test for getAllEmployees method
    @DisplayName("Junit test for getAllEmployees method")
    @Test
    void givenEmployeesList_whenGetAllEmployees_thenReturnEmployees() {
        //given - precondition or setup
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Fany")
                .lastName("Jupsy")
                .email("ukl@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));

        //when - action or the behaviour that we are going to test
        List<Employee> employeeList = employeeService.getAllEmployees();

        //then - verify the output
        assertThat(employeeList).isNotNull()
                .hasSize(2);
    }

    //Junit test for getAllEmployees method
    @DisplayName("Junit test for getAllEmployees method (negative scenario)")
    @Test
    void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList() {
        //given - precondition or setup
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        //when - action or the behaviour that we are going to test
        List<Employee> employeeList = employeeService.getAllEmployees();

        //then - verify the output
        assertThat(employeeList).isNotNull().isEmpty();
    }

    //Junit test for getEmployeeById method
    @DisplayName("Junit test for getEmployeeById method")
    @Test
    void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
        //given - precondition or setup
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        //when - action or the behaviour that we are going to test
        Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).get();

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    //Junit test for updateEmployee
    @DisplayName("Junit test for updateEmployee")
    @Test
    void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        //given - precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("azws@we.fr");
        employee.setFirstName("Fera");

        //when - action or the behaviour that we are going to test
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        //then - verify the output
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Fera");
        assertThat(updatedEmployee.getEmail()).isEqualTo("azws@we.fr");
    }

    //Junit test for deleteEmployee
    @DisplayName("Junit test for deleteEmployee")
    @Test
    void givenEmployeeId_whenDeleteEmployee_thenNothing() {
        //given - precondition or setup
        long employeeId = 1L;
        willDoNothing().given(employeeRepository).deleteById(employeeId);

        //when - action or the behaviour that we are going to test
        employeeService.deleteEmployee(employeeId);
        //then - verify the output
        verify(employeeRepository,times(1)).deleteById(employeeId);
    }



}
