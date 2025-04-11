package com.jgo.demo_graphql.domain.port;

import com.jgo.demo_graphql.domain.model.Customer;

import java.util.List;
import java.util.UUID;

/**
 * Puerto secundario (driven port) que define las operaciones de repositorio para Customer.
 * En la Arquitectura Hexagonal, este puerto pertenece al dominio y será implementado
 * por un adaptador en la capa de infraestructura.
 */
public interface CustomerRepository {
    
    /**
     * Busca un cliente por su identificador único
     * @param uuid Identificador único del cliente
     * @return Cliente encontrado o null si no existe
     */
    Customer findById(UUID uuid);
    
    /**
     * Busca un cliente por su email
     * @param email Email del cliente
     * @return Cliente encontrado o null si no existe
     */
    Customer findByEmail(String email);

    /**
     * Guarda un nuevo cliente
     * @param customer Cliente a guardar
     * @return Cliente guardado con posibles modificaciones (ej: IDs generados)
     */
    Customer save(Customer customer);
    
    /**
     * Actualiza un cliente existente
     * @param customer Cliente con los datos actualizados
     * @return Cliente actualizado
     */
    Customer update(Customer customer);
    
    /**
     * Obtiene todos los clientes
     * @return Lista de clientes
     */
    List<Customer> findAll();
    
    /**
     * Elimina un cliente por su identificador único
     * @param uuid Identificador único del cliente
     * @return true si se eliminó correctamente, false en caso contrario
     */
    Boolean deleteById(UUID uuid);
    
    /**
     * Elimina un cliente por su email
     * @param email Email del cliente
     * @return true si se eliminó correctamente, false en caso contrario
     */
    Boolean deleteByEmail(String email);

}
