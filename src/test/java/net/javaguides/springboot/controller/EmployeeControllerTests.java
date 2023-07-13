package net.javaguides.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.service.EmployeeService;
import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;
    @Autowired
    private ObjectMapper objectMapper;

    //Junit test for createEmployee
    @DisplayName("Junit test for createEmployee")
    @Test
    void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Fany")
                .lastName("Jupsy")
                .email("ukl@gmail.com")
                .build();

        given(employeeService.saveEmployee(any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employee)));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName",
                        is(employee.getFirstName())))
        .andExpect(jsonPath("$.lastName",
                is(employee.getLastName())))
                .andExpect(jsonPath("$.email",
                        is(employee.getEmail())));
    }

    //Junit test for getAllEmployees
    @DisplayName("Junit test for getAllEmployees")
    @Test
    void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception {
        //given - precondition or setup
        List<Employee> employees = new ArrayList<>();
        employees.add(Employee.builder()
                .firstName("Fany")
                .lastName("Jupsy")
                .email("ukl@gmail.com")
                .build());

        employees.add(Employee.builder()
                .firstName("Fany2")
                .lastName("Jupsy2")
                .email("ukl2@gmail.com")
                .build());

        given(employeeService.getAllEmployees()).willReturn(employees);

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees"));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",
                        is(employees.size())));
    }

    //positive scenario - valid employee id
    //Junit test for GET employee by id REST API
    @DisplayName("Junit test for GET employee by id REST API")
    @Test
    void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception{
        //given - precondition or setup
        long employeeId = 1L;

        Employee employee = Employee.builder()
                .firstName("Fany")
                .lastName("Jupsy")
                .email("ukl@gmail.com")
                .build();

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}",employeeId));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName",
                        is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(employee.getLastName())))
                .andExpect(jsonPath("$.email",
                        is(employee.getEmail())));
    }

    //negative scenario - invalid employee id
    //Junit test for GET employee by id REST API
    @DisplayName("Junit test for GET employee by invalid id REST API")
    @Test
    void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception{
        //given - precondition or setup
        long employeeId = 1L;

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}",employeeId));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isNotFound());
    }

    //positive scenario - valid employee id
    //Junit test for update employee REST API
    @DisplayName("Junit test for update employee REST API")
    @Test
    void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdatedEmployeeObject() throws Exception{
        //given - precondition or setup
        long employeeId = 1L;

        Employee savedEmployee = Employee.builder()
                .firstName("Fany")
                .lastName("Jupsy")
                .email("ukl@gmail.com")
                .build();

        Employee updatedEmployee = Employee.builder()
                .firstName("Abba")
                .lastName("Jupsio")
                .email("baba@gmail.com")
                .build();

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(savedEmployee));
        given(employeeService.updateEmployee(any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updatedEmployee)));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName",
                        is(updatedEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(updatedEmployee.getLastName())))
                .andExpect(jsonPath("$.email",
                        is(updatedEmployee.getEmail())));
    }

    //negative scenario - invalid employee id
    //Junit test for update employee REST API
    @DisplayName("Junit test for GET employee by invalid id REST API")
    @Test
    void givenUpdatedEmployee_whenUpdateEmployee_thenReturn404() throws Exception{
        //given - precondition or setup
        long employeeId = 1L;

        Employee updatedEmployee = Employee.builder()
                .firstName("Abba")
                .lastName("Jupsio")
                .email("baba@gmail.com")
                .build();

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());
        given(employeeService.updateEmployee(any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));


        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updatedEmployee)));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isNotFound());
    }

    //Junit test for delete employee REST API
    @DisplayName("Junit test for delete employee REST API")
    @Test
    void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception {
        //given - precondition or setup
        long employeeId = 1L;
        willDoNothing().given(employeeService).deleteEmployee(employeeId);

        //when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", employeeId));

        //then - verify the output
        response.andDo(print())
                .andExpect(jsonPath("$", is("Employee deleted successfully")))
                .andExpect(status().isOk());
    }
}
