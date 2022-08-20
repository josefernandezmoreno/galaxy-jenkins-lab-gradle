package cloud.csonic.labgradle.api

import cloud.csonic.labgradle.excepions.CustomerNotFound
import cloud.csonic.labgradle.model.Customer
import cloud.csonic.labgradle.service.CustomerService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.clearMocks
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(CustomersApi::class)
internal class CustomersApiTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var customerService: CustomerService

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
        clearMocks(customerService)
    }

    @Test
    fun `check mocks & objects`() {
        assertThat(mockMvc).isNotNull
        assertThat(customerService).isNotNull
    }

    @Test
    fun getAll() {

        // Preparing data
        val list = listOf(Customer(1,"lastName1","name1"),Customer(2,"lastName2","name2"))
        // Mocks & Stubs configuration
        every { customerService.findAll() } returns list

        // Business logic execution
        mockMvc.perform(get("/customers"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.customers", hasSize<Any>(2)))
            .andExpect(jsonPath("\$.customers[0].id").value(1))
            .andExpect(jsonPath("\$.customers[0].lastName").value("lastName1"))
            .andExpect(jsonPath("\$.customers[0].name").value("name1"))
            .andExpect(jsonPath("\$.customers[1].id").value(2))
            .andExpect(jsonPath("\$.customers[1].lastName").value("lastName2"))
            .andExpect(jsonPath("\$.customers[1].name").value("name2"))

        // Validating results
        // Validating mocks behaviour
        verify(exactly = 1) { customerService.findAll()}
        confirmVerified(customerService)
    }

    @Test
    fun getById() {

        // Preparing data
        val customer1 = Customer(1,"lastName1","name1")

        // Mocks & Stubs configuration

        every { customerService.findById(1) } returns customer1


        // Business logic execution
        mockMvc.perform(get("/customers/1"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        // Validating results

        // Validating mocks behaviour
        verify(exactly = 1) { customerService.findById(1) }
        confirmVerified(customerService)

    }

    @Test
    fun `customer didn't find`() {

        // Preparing data


        // Mocks & Stubs configuration

        every { customerService.findById(1) } throws CustomerNotFound("Cliente no encontrado")


        // Business logic execution
        mockMvc.perform(get("/customers/1"))
            .andExpect(status().isNotFound)
        // Validating results

        // Validating mocks behaviour
        verify(exactly = 1) { customerService.findById(1) }
        confirmVerified(customerService)

    }
}