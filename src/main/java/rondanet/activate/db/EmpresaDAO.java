package rondanet.activate.db;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import common.rondanet.catalogo.core.dto.DetallesProductosCompartidosDTO;
import common.rondanet.catalogo.core.dto.EmpresaProductoTotalDTO;
import common.rondanet.catalogo.core.dto.EstadisticasProductosDTO;
import common.rondanet.catalogo.core.dto.ProductoEmpresaDTO;
//import common.rondanet.catalogo.core.entity.*;
import common.rondanet.catalogo.core.exceptions.ModelException;
import common.rondanet.catalogo.core.exceptions.ServiceException;
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

	public Producto getProductoByEmpresa(String rutEmpresa, String gtin) {
		
		Aggregation productoAggregation = Aggregation.newAggregation(
				match(Criteria.where("gtin").is(gtin)),
				Aggregation.sort(Sort.Direction.ASC, "gtin")
		);
		Query query = new Query();
		query.addCriteria(Criteria.where("gtin").is(gtin));
		List<Producto> producto = mongoOperations.find(query, Producto.class);
		//List<Producto> producto = mongoOperations.aggregate(productoAggregation, "Producto", Producto.class).getMappedResults();

		/*
		 * Criteria expression = new Criteria(); Criteria criteria = new Criteria();
		 * 
		 * NotificacionAcuse acuse = new NotificacionAcuse(id, idEmpresa);
		 * expression.andOperator(Criteria.where("eliminado").is(false),
		 * Criteria.where("susuarios").is(id),
		 * Criteria.where("sEmpresas").is(idEmpresa),
		 * Criteria.where("acuse").not().elemMatch(new Criteria()
		 * .andOperator(Criteria.where("usuarioId").is(id),
		 * Criteria.where("empresaId").is(idEmpresa)))); andExpression.add(expression);
		 * criteria.andOperator(andExpression.toArray(new
		 * Criteria[andExpression.size()])); Query query = new Query();
		 * query.addCriteria(criteria); query.with(sort);
		 * 
		 * List<Empresa> notificacionesProcesadas = mongoOperations.find(query,
		 * Empresa.class);
		 */

		logger.log(Level.INFO, "El método getProductoByEmpresa() de la clase EmpresaDAO fue ejecutado.");
		return null;
	}

	public void guardarEmpresa(Empresa empresa) {
		empresaRepository.save(empresa);
		logger.log(Level.INFO, "El método guardarEmpresa() de la clase EmpresaDAO fue ejecutado.");
	}
}
