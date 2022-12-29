package rondanet.activate.db;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

import common.rondanet.catalogo.core.dto.DetallesProductosCompartidosDTO;
import common.rondanet.catalogo.core.dto.EmpresaProductoTotalDTO;
import common.rondanet.catalogo.core.dto.EstadisticasProductosDTO;
import common.rondanet.catalogo.core.dto.ProductoEmpresaDTO;
//import common.rondanet.catalogo.core.entity.*;
import common.rondanet.catalogo.core.exceptions.ModelException;
import common.rondanet.catalogo.core.exceptions.ServiceException;
import rondanet.activate.entidades.Actividad;
import rondanet.activate.entidades.Empaque;
import rondanet.activate.entidades.Empresa;
import rondanet.activate.entidades.Producto;
import rondanet.activate.repository.IEmpresaRepository;
import common.rondanet.catalogo.core.resources.*;

import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Component
public class EmpresaDAO {

	Logger logger = LogManager.getLogger(EmpresaDAO.class);

	@Autowired
	IEmpresaRepository empresaRepository;

	private final MongoTemplate mongoTemplate;

	private final MongoOperations mongoOperations;

	public EmpresaDAO(MongoTemplate mongoTemplate, MongoOperations mongoOperations,
			IEmpresaRepository empresaRepository) {
		this.mongoTemplate = mongoTemplate;
		this.mongoOperations = mongoOperations;
		this.empresaRepository = empresaRepository;
	}

	public Empresa buscarEmpresa(String rutEmpresa) {
		Optional<Empresa> empresa = empresaRepository.findByRut(rutEmpresa);
		logger.log(Level.INFO, "El método findEmpresa() de la clase EmpresaDAO fue ejecutado.");
		return empresa.isPresent() ? empresa.get() : null;
	}

	public void guardarEmpresa(Empresa empresa) {
		empresaRepository.save(empresa);
		logger.log(Level.INFO, "El método guardarEmpresa() de la clase EmpresaDAO fue ejecutado.");
	}

	public List<Producto> getProductoDeEmpresaByGtin(String rutEmpresa, String gtin) {

		Aggregation productoAggregation = Aggregation.newAggregation(match(Criteria.where("rut").is(rutEmpresa)),
				unwind("productos"), match(Criteria.where("productos.gtin").is(gtin)), replaceRoot("productos"),
				Aggregation.sort(Sort.Direction.ASC, "gtin"));

		List<Producto> producto = mongoOperations.aggregate(productoAggregation, "Empresa", Producto.class)
				.getMappedResults();

		logger.log(Level.INFO, "El método getProductoByEmpresa() de la clase EmpresaDAO fue ejecutado.");

		return producto;
	}
	public List<Producto> getProductosDeEmpresa(String rutEmpresa) {

		Aggregation productoAggregation = Aggregation.newAggregation(match(Criteria.where("rut").is(rutEmpresa)),
				unwind("productos"), replaceRoot("productos"),
				Aggregation.sort(Sort.Direction.ASC, "gtin"));

		List<Producto> producto = mongoOperations.aggregate(productoAggregation, "Empresa", Producto.class)
				.getMappedResults();

		logger.log(Level.INFO, "El método getProductosByEmpresa() de la clase EmpresaDAO fue ejecutado.");

		return producto;
	}
	public void guardarProducto(String rutEmpresa, Producto producto) {
		// Guardar un Producto

		Query query = new Query();
		query.addCriteria(Criteria.where("rut").is(rutEmpresa));

		Update upda = new Update();
		upda.push("productos", producto);

		mongoOperations.updateFirst(query, upda, Empresa.class);

		logger.log(Level.INFO, "El método guardarProducto() de la clase EmpresaDAO fue ejecutado.");

	}

	public void actualizarProducto(String rutEmpresa, Producto producto) {
		// Actualizar Producto
		Query query = new Query();
		query.addCriteria(Criteria.where("rut").is(rutEmpresa).andOperator(Criteria.where("productos.gtin").is(producto.getGtin())));

		Update upda = new Update();
		upda.set("productos.$[]", producto);

		mongoOperations.updateFirst(query, upda, Empresa.class);

		logger.log(Level.INFO, "El método actualizarProducto() de la clase EmpresaDAO fue ejecutado.");
	}

	public void guardarEmpaqueDelProducto(String rutEmpresa, String gtin, Empaque empaque) {
		// Guardar un Empaque

		Query query = new Query();
		query.addCriteria(Criteria.where("rut").is(rutEmpresa).andOperator(Criteria.where("productos.gtin").is(gtin)));

		Update upda = new Update();
		upda.push("productos.empaques", empaque);

		mongoOperations.updateFirst(query, upda, Empresa.class);

		logger.log(Level.INFO, "El método guardarEmpaqueDelProducto() de la clase EmpresaDAO fue ejecutado.");
	}
	public void actualizarEmpaqueDelProducto(String rutEmpresa, String gtin, Empaque empaque) {
		// Guardar un Empaque

		Query query = new Query();
		query.addCriteria(Criteria.where("rut").is(rutEmpresa).andOperator(Criteria.where("productos.gtin").is(gtin),Criteria.where("productos.empaques.gtin").is(empaque.getGtin())));

		Update upda = new Update();
		upda.push("productos.empaques.$[]", empaque);

		mongoOperations.updateFirst(query, upda, Empresa.class);

		logger.log(Level.INFO, "El método actualizarEmpaqueDelProducto() de la clase EmpresaDAO fue ejecutado.");
	}	
	public void guardarActividadDelProducto(String rutEmpresa, Actividad actividad) {
		// Guardar un Empaque

		Query query = new Query();
		query.addCriteria(Criteria.where("rut").is(rutEmpresa));

		Update upda = new Update();
		upda.push("actividades", actividad);

		mongoOperations.updateFirst(query, upda, Empresa.class);

		logger.log(Level.INFO, "El método guardarActividadDelProducto() de la clase EmpresaDAO fue ejecutado.");
	}
}
