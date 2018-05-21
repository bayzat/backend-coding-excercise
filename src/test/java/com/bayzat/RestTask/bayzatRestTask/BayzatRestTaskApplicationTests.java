package com.bayzat.RestTask.bayzatRestTask;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.bayzat.entities.Company;
import com.bayzat.entities.Employee;
import com.bayzat.repositories.CompanyRepo;
import com.bayzat.repositories.EmployeeRepo;

/**
 * @author Alhaitham Tawalbeh This test is a brief test for some cases the other
 *         scenarios will be the same approximately
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class BayzatRestTaskApplicationTests {


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

	private String companyName = "bayzat";
	private String companyLocation = "UAE";

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

	private Company company;

	private List<Employee> employees = new ArrayList<>();

    @Autowired
	private EmployeeRepo employeeRepo;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
	private CompanyRepo companyRepo;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
		// this.companyRepo.deleteAllInBatch();
		// this.employeeRepo.deleteAllInBatch();

		this.company = (new Company());
		this.company.setAddress(companyLocation);
		this.company.setName(companyName);
		// this.company.setId(1L);
		this.company = companyRepo.saveAndFlush(this.company);


		this.employees
				.add(employeeRepo.saveAndFlush(new Employee(company, "Jone", "054444444", "Male", new Date(), 25000)));
		this.employees
				.add(employeeRepo.saveAndFlush(new Employee(company, "Jack", "054444444", "Male", new Date(), 30000)));
		company.setEmployees(this.employees);
    }

    @Test
    public void CompanyNotFound() throws Exception {
		mockMvc.perform(post("/666/employees/").content(this.json(new Employee(null, null, null, null, null, 0)))
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
	public void readCompanyEmployee() throws Exception {

        mockMvc.perform(get("/companies/" + this.company.getId()+ "/employees/"
                + this.employees.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is((this.employees.get(0).getId().intValue()))))
				.andExpect(jsonPath("$.name", is(this.employees.get(0).getName())))
				.andExpect(jsonPath("$.phoneNumber", is(this.employees.get(0).getPhoneNumber())))
				.andExpect(jsonPath("$.gender", is(this.employees.get(0).getGender())));
    }

	@Test
	public void readSingleCompany() throws Exception {
		mockMvc.perform(get("/companies/" + this.company.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is(this.company.getId().intValue())))
				.andExpect(jsonPath("$.name", is(this.company.getName())))
				.andExpect(jsonPath("$.address", is(this.company.getAddress())));

    }


	@Test
	public void createCompany() throws Exception {
		Company company = new Company(companyName, companyLocation);
		// company.setId(100L);
		String companyJson = json(company);

		this.mockMvc.perform(post("/companies")
                .contentType(contentType)
				.content(companyJson))
				.andExpect(status().isOk());
    }

	@Test
	public void createEmployee() throws Exception {

		String employeeJson = json(new Employee(company, "Sara", "054444444", "Female", new Date(), 40000));

		this.mockMvc.perform(post("/companies/1/employees").contentType(contentType).content(employeeJson))
				.andExpect(status().isOk());
	}

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
    }
}