package cloud.csonic.labgradle.api

import cloud.csonic.labgradle.api.dto.CustomerDto
import cloud.csonic.labgradle.api.dto.CustomersDto
import cloud.csonic.labgradle.excepions.CustomerNotFound
import cloud.csonic.labgradle.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.http.HttpResponse

@RestController
@RequestMapping(value = ["/customers"])
class CustomersApi(val customerService: CustomerService){

    @GetMapping
    fun getAll() = CustomersDto(customerService.findAll())

    @GetMapping("{id}")
    fun getById(@PathVariable("id") id:Long): ResponseEntity<CustomerDto> {
        return try {
            ResponseEntity.ok(CustomerDto(customerService.findById(id)))
        }catch (e:CustomerNotFound){
            ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }


}