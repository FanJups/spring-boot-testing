package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryITests {
    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setup(){
        employee = Employee.builder()
                .firstName("Fan")
                .lastName("Jups")
                .email("fjk@gmail.com")
                .build();

        employeeRepository.deleteAll();
    }

    //Junit test for save employee operation
    @DisplayName("Junit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){
        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Fan")
//                .lastName("Jups")
//                .email("fjk@gmail.com")
//                .build();
        //when - action or the behaviour that we are going to test
        Employee savedEmployee = employeeRepository.save(employee);
        //then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    //Junit test for get all employees operation
    @DisplayName("Junit test for get all employees operation")
    @Test
    public void givenEmployeeList_whenFindAll_thenEmployeeList(){
        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Fan")
//                .lastName("Jups")
//                .email("fjk@gmail.com")
//                .build();

        Employee employee1 = Employee.builder()
                .firstName("Fan1")
                .lastName("Jups1")
                .email("fjk1@gmail.com")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        //when - action or the behaviour that we are going to test
        List<Employee> employeeList = employeeRepository.findAll();

        //then - verify the output
        assertThat(employeeList).isNotNull().hasSize(2);
    }


    //Junit test for get employee by id operation
    @DisplayName("Junit test for get employee by id operation")
    @Test
    public void givenEmployeeId_whenFindById_thenReturnEmployeeObject() {
        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Fan")
//                .lastName("Jups")
//                .email("fjk@gmail.com")
//                .build();
        employeeRepository.save(employee);
        //when - action or the behaviour that we are going to test
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();
        //then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    //Junit test for get employee by email operation
    @DisplayName("Junit test for get employee by email operation")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {
        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Fan")
//                .lastName("Jups")
//                .email("fjk@gmail.com")
//                .build();
        employeeRepository.save(employee);
        //when - action or the behaviour that we are going to test
        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();
        //then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    //Junit test for update employee operation
    @DisplayName("Junit test for update employee operation")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Fan")
//                .lastName("Jups")
//                .email("fjk@gmail.com")
//                .build();
        employeeRepository.save(employee);

        //when - action or the behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("jsd@gdert.fr");
        savedEmployee.setFirstName("Fany");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        //then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("jsd@gdert.fr");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Fany");
    }

    //Junit test for delete employee operation
    @DisplayName("Junit test for delete employee operation")
    @Test
    public void givenEmployeeObject_whenDeleteEmployee_thenRemoveEmployee() {
        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Fan")
//                .lastName("Jups")
//                .email("fjk@gmail.com")
//                .build();
        employeeRepository.save(employee);

        //when - action or the behaviour that we are going to test
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> optionalEmployee = employeeRepository.findById(employee.getId());

        //then - verify the output
        assertThat(optionalEmployee).isEmpty();
    }

    //Junit test for custom query using JPQL with index params
    @DisplayName("Junit test for custom query using JPQL with index params")
    @Test
    void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject() {
        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Fan")
//                .lastName("Jups")
//                .email("fjk@gmail.com")
//                .build();
        employeeRepository.save(employee);

        String firstName = "Fan";
        String lastName = "Jups";

        //when - action or the behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findByJPQL(firstName,lastName);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    //Junit test for custom query using JPQL with named params
    @DisplayName("Junit test for custom query using JPQL with named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject() {
        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Fan")
//                .lastName("Jups")
//                .email("fjk@gmail.com")
//                .build();
        employeeRepository.save(employee);
        String firstName = "Fan";
        String lastName = "Jups";
        //when - action or the behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(firstName,lastName);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    //Junit test for custom query using Native SQL with index params
    @DisplayName("Junit test for custom query using Native SQL with index params")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject() {
        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Fan")
//                .lastName("Jups")
//                .email("fjk@gmail.com")
//                .build();
        employeeRepository.save(employee);

        String firstName = "Fan";
        String lastName = "Jups";

        //when - action or the behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(), employee.getLastName());

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    //Junit test for custom query using Native SQL with named params
    @DisplayName("Junit test for custom query using Native SQL with named params")
    @Test
    void givenFirstNameAndLastName_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject() {
        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Fan")
//                .lastName("Jups")
//                .email("fjk@gmail.com")
//                .build();
        employeeRepository.save(employee);

        String firstName = "Fan";
        String lastName = "Jups";

        //when - action or the behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findByNativeSQLNamedParams(employee.getFirstName(), employee.getLastName());

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }
}