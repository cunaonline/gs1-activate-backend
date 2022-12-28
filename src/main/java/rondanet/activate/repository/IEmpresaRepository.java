package rondanet.activate.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import rondanet.activate.entidades.Empresa;
import rondanet.activate.entidades.Producto;

public interface IEmpresaRepository extends MongoRepository<Empresa, String>{

	public Optional<Empresa> findByRut(String rut);
	
}
