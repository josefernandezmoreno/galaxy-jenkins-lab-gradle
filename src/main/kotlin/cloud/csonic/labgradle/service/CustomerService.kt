package cloud.csonic.labgradle.service

import cloud.csonic.labgradle.excepions.CustomerNotFound
import cloud.csonic.labgradle.model.Customer
import org.springframework.stereotype.Service

interface CustomerService {
    fun findAll(): List<Customer>
    fun findById(id:Long): Customer
}

@Service
class CustomerServiceImpl: CustomerService{

    var list = listOf(
        Customer(1,"lastName01","name01"),
        Customer(2,"lastName02","name02")
    )

    override fun findAll() = list
    override fun findById(id:Long) = list.find { it.id==id } ?: throw CustomerNotFound("Cliente $id no encontrado")


}