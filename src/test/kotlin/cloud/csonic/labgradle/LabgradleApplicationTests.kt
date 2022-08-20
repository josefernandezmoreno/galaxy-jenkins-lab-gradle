package cloud.csonic.labgradle

import cloud.csonic.labgradle.api.CustomersApi

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.assertj.core.api.Assertions.assertThat

@SpringBootTest
class LabgradleApplicationTests(@Autowired val customersApi: CustomersApi){


	@Test
	fun contextLoads() {

		assertThat(customersApi).isNotNull
	}



}
