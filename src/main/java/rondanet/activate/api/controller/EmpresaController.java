package rondanet.activate.api.controller;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import rondanet.activate.api.controller.interfaz.IEmpresaController;
import rondanet.activate.entidades.Empaque;
import rondanet.activate.entidades.Empresa;
import rondanet.activate.entidades.Marca;
import rondanet.activate.entidades.Producto;
import rondanet.activate.exeptions.EmpresaException;
import rondanet.activate.exeptions.MarcaException;
import rondanet.activate.exeptions.ProductoException;
import rondanet.activate.services.interfaces.IEmpresaService;

import common.rondanet.catalogo.core.exceptions.ServiceException;
import common.rondanet.catalogo.core.resources.Representacion;

import javax.ws.rs.WebApplicationException;
import java.util.Arrays;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.badRequest;

@RestController
public class EmpresaController implements IEmpresaController {

	Logger logger = LogManager.getLogger(EmpresaController.class);

	private IEmpresaService empresaService;

	EmpresaController(IEmpresaService empresaService) {
		this.empresaService = empresaService;
	}

	@Override
	public ResponseEntity<Representacion> detalleEmpresa(@RequestParam("rutEmpresa") String rutEmpresa) {

		try {
			logger.log(Level.INFO, "El método detalleEmpresa() de la url /empresa/detalle fue ejecutado.");

			Empresa empresa = this.empresaService.getDetalleEmpresa(rutEmpresa);
			logger.log(Level.INFO, "El método detalleEmpresa() de la url /empresa/detalle fue ejecutado.");
			return ok(new Representacion<Empresa>(HttpStatus.OK.value(), empresa));
		} catch (ServiceException ex) {
			logger.log(Level.ERROR, "empresa controller @GetMapping(\"/empresa/detalle\") Error: " + ex.getMessage(),
					ExceptionUtils.getStackTrace(ex));
			throw new WebApplicationException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
		} catch (Exception ex) {
			logger.log(Level.ERROR, "empresa controller @GetMapping(\"/empresa/detalle\") Error: " + ex.getMessage(),
					ExceptionUtils.getStackTrace(ex));

			throw new WebApplicationException("Ocurrió un error inesperado, intente nuevamente - " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
	}

	@Override
	public ResponseEntity<Representacion> crearEmpresa(@RequestBody Empresa empresa) {

		try {
			logger.log(Level.INFO, "El método crearEmpresa() de la url /empresa/adicionar fue ejecutado.");

			String mensaje = this.empresaService.guardarEmpresa(empresa);
			logger.log(Level.INFO, "El método crearEmpresa() de la url /empresa/adicionar fue ejecutado.");
			return ok(new Representacion<String>(HttpStatus.OK.value(), mensaje));
		} catch (ServiceException ex) {
			logger.log(Level.ERROR, "empresa controller @PostMapping(\"/empresa/adicionar\") Error: " + ex.getMessage(),
					ExceptionUtils.getStackTrace(ex));
			throw new WebApplicationException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
		} catch (Exception ex) {
			logger.log(Level.ERROR, "empresa controller @PostMapping(\"/empresa/adicionar\") Error: " + ex.getMessage(),
					ExceptionUtils.getStackTrace(ex));

			throw new WebApplicationException("Ocurrió un error inesperado, intente nuevamente - " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
	}

	@Override
	public ResponseEntity<Representacion> adicionarMarca(String rutEmpresa, Integer tipo, String valor) {
		try {
			logger.log(Level.INFO, "El método adicionarMarca() de la url /empresa/marcas/adicionar fue ejecutado.");

			Integer id = this.empresaService.adicionarMarca(rutEmpresa, tipo, valor);
			logger.log(Level.INFO, "El método adicionarMarca() de la url /empresa/marcas/adicionar fue ejecutado.");
			return ok(new Representacion<Integer>(HttpStatus.OK.value(), id));
		} catch (ServiceException ex) {
			logger.log(Level.ERROR,
					"empresa controller @PostMapping(\"/empresa/marcas/adicionar\") Error: " + ex.getMessage(),
					ExceptionUtils.getStackTrace(ex));
			throw new WebApplicationException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
		} catch (EmpresaException | MarcaException ex) {
			logger.log(Level.ERROR, ex.getMessage(), ExceptionUtils.getStackTrace(ex));
			return ok(new Representacion<String>(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
		} catch (Exception ex) {
			logger.log(Level.ERROR,
					"empresa controller @PostMapping(\"/empresa/marcas/adicionar\") Error: " + ex.getMessage(),
					ExceptionUtils.getStackTrace(ex));

			throw new WebApplicationException("Ocurrió un error inesperado, intente nuevamente - " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
	}

	public ResponseEntity<Representacion> eliminarMarca(String rutEmpresa, Integer tipo, Integer id) {
		try {
			logger.log(Level.INFO, "El método eliminarMarca() de la url /empresa/marcas/eliminar fue ejecutado.");

			List<Marca> marcas = this.empresaService.eliminarMarca(rutEmpresa, tipo, id);
			logger.log(Level.INFO, "El método eliminarMarca() de la url /empresa/marcas/eliminar fue ejecutado.");
			return ok(new Representacion<List<Marca>>(HttpStatus.OK.value(), marcas));
		} catch (ServiceException ex) {
			logger.log(Level.ERROR,
					"empresa controller @PostMapping(\"/empresa/marcas/eliminar\") Error: " + ex.getMessage(),
					ExceptionUtils.getStackTrace(ex));
			throw new WebApplicationException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
		} catch (MarcaException ex) {
			logger.log(Level.ERROR, ex.getMessage(), ExceptionUtils.getStackTrace(ex));
			return ok(new Representacion<String>(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
		} catch (Exception ex) {
			logger.log(Level.ERROR,
					"empresa controller @PostMapping(\"/empresa/marcas/eliminar\") Error: " + ex.getMessage(),
					ExceptionUtils.getStackTrace(ex));

			throw new WebApplicationException("Ocurrió un error inesperado, intente nuevamente - " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
	}

	@Override
	public ResponseEntity<Representacion> crearProducto(String rutEmpresa, @RequestBody Producto producto) {
		try {
			logger.log(Level.INFO,
					"El método crearProducto() de la url /" + rutEmpresa + "/producto/adicionar fue ejecutado.");
            String usuario = "Hernan Sagastume";
			Producto nuevoProducto = this.empresaService.guardarProducto(usuario,rutEmpresa, "096192", producto);
			logger.log(Level.INFO,
					"El método crearProducto() de la url /" + rutEmpresa + "/producto/adicionar fue ejecutado.");
			return ok(new Representacion<Producto>(HttpStatus.OK.value(), nuevoProducto));
		} catch (ServiceException ex) {
			logger.log(Level.ERROR, "empresa controller @PostMapping(\"/" + rutEmpresa
					+ "/producto/adicionar\") Error: " + ex.getMessage(), ExceptionUtils.getStackTrace(ex));
			throw new WebApplicationException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
		} catch (ProductoException ex) {
			logger.log(Level.ERROR, ex.getMessage(), ExceptionUtils.getStackTrace(ex));
			return ok(new Representacion<String>(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
		} catch (Exception ex) {
			logger.log(Level.ERROR, "empresa controller @PostMapping(\"/" + rutEmpresa
					+ "/producto/adicionar\") Error: " + ex.getMessage(), ExceptionUtils.getStackTrace(ex));
			throw new WebApplicationException("Ocurrió un error inesperado, intente nuevamente - " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
	}


	@Override
	public ResponseEntity<Representacion> actualizarProducto(String rutEmpresa, String gtinProducto) {
		try {
			logger.log(Level.INFO,
					"El método crearProducto() de la url /" + rutEmpresa + "/producto/adicionar fue ejecutado.");
			 String usuario = "Hernan Sagastume";
			 this.empresaService.actualizarProducto(usuario,rutEmpresa, gtinProducto);
			logger.log(Level.INFO,
					"El método crearProducto() de la url /" + rutEmpresa + "/producto/adicionar fue ejecutado.");
			return ok(new Representacion<String>(HttpStatus.OK.value(), "Operación Exitosa"));
		} catch (ServiceException ex) {
			logger.log(Level.ERROR, "empresa controller @PostMapping(\"/" + rutEmpresa
					+ "/producto/adicionar\") Error: " + ex.getMessage(), ExceptionUtils.getStackTrace(ex));
			throw new WebApplicationException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
		} catch (ProductoException ex) {
			logger.log(Level.ERROR, ex.getMessage(), ExceptionUtils.getStackTrace(ex));
			return ok(new Representacion<String>(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
		} catch (Exception ex) {
			logger.log(Level.ERROR, "empresa controller @PostMapping(\"/" + rutEmpresa
					+ "/producto/adicionar\") Error: " + ex.getMessage(), ExceptionUtils.getStackTrace(ex));

			throw new WebApplicationException("Ocurrió un error inesperado, intente nuevamente - " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
	}

	@Override
	public ResponseEntity<Representacion> cargarImagenDelProducto(String rutEmpresa, Integer gtin,
			@RequestParam("foto") MultipartFile file) {
		try {
			logger.log(Level.INFO, "El método crearProducto() de la url /empresa/producto/adicionar fue ejecutado.");

			String mensaje = this.empresaService.guardarImagenDelProducto(rutEmpresa, gtin, file);
			logger.log(Level.INFO, "El método crearProducto() de la url /empresa/producto/adicionar fue ejecutado.");
			return ok(new Representacion<String>(HttpStatus.OK.value(), mensaje));
		} catch (ServiceException ex) {
			logger.log(Level.ERROR,
					"empresa controller @PostMapping(\"/empresa/producto/adicionar\") Error: " + ex.getMessage(),
					ExceptionUtils.getStackTrace(ex));
			throw new WebApplicationException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
		} catch (Exception ex) {
			logger.log(Level.ERROR,
					"empresa controller @PostMapping(\"/empresa/producto/adicionar\") Error: " + ex.getMessage(),
					ExceptionUtils.getStackTrace(ex));

			throw new WebApplicationException("Ocurrió un error inesperado, intente nuevamente - " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
	}

	@Override
	public ResponseEntity<Representacion> crearEmpaqueDelProducto(String rutEmpresa, String gtin,
			@RequestBody Empaque empaque) {
		try {
			logger.log(Level.INFO, "El método crearProducto() de la url /" + rutEmpresa + "/producto/" + gtin
					+ "/empaque/adicionar  fue ejecutado.");
			 String usuario = "Ariam Alvarez";
			Empaque empaqueNuevo = this.empresaService.guardarEmpaqueDelProducto(usuario,rutEmpresa, gtin, empaque);
			logger.log(Level.INFO, "El método crearProducto() de la url /" + rutEmpresa + "/producto/" + gtin
					+ "/empaque/adicionar fue ejecutado.");
			return ok(new Representacion<Empaque>(HttpStatus.OK.value(), empaqueNuevo));
		} catch (ProductoException | EmpresaException ex) {
			logger.log(Level.ERROR, ex.getMessage(), ExceptionUtils.getStackTrace(ex));
			return ok(new Representacion<String>(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
		} catch (ServiceException ex) {
			logger.log(Level.ERROR, "empresa controller @PostMapping(\"/" + rutEmpresa + "/producto/" + gtin
					+ "/empaque/adicionar\") Error: " + ex.getMessage(), ExceptionUtils.getStackTrace(ex));
			throw new WebApplicationException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
		} catch (Exception ex) {
			logger.log(Level.ERROR, "empresa controller @PostMapping(\"/" + rutEmpresa + "/producto/" + gtin
					+ "/empaque/adicionar\") Error: " + ex.getMessage(), ExceptionUtils.getStackTrace(ex));

			throw new WebApplicationException("Ocurrió un error inesperado, intente nuevamente - " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
	}

	
	@Override
	public ResponseEntity<Representacion> eliminarEmpaqueDelProducto(String rutEmpresa, String gtin13, String gtin14){
		try {
			logger.log(Level.INFO, "El método eliminarEmpaqueDelProducto() de la url /" + rutEmpresa + "/producto/" + gtin13
					+ "/empaque/"+ gtin14+"/eliminar  fue ejecutado.");
			 String usuario = "Hernan Sagastume";
			this.empresaService.eliminarEmpaqueDelProducto(usuario,rutEmpresa, gtin13, gtin14);
			logger.log(Level.INFO, "El método eliminarEmpaqueDelProducto() de la url /" + rutEmpresa + "/producto/" + gtin13
					+ "/empaque/"+ gtin14 +"/eliminar fue ejecutado.");
			return ok(new Representacion<String>(HttpStatus.OK.value(), "Operación Exitosa"));
		} catch (ProductoException | EmpresaException ex) {
			logger.log(Level.ERROR, ex.getMessage(), ExceptionUtils.getStackTrace(ex));
			return ok(new Representacion<String>(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
		} catch (ServiceException ex) {
			logger.log(Level.ERROR, "empresa controller @PostMapping(\"/" + rutEmpresa + "/producto/" + gtin13
					+ "/empaque/"+ gtin14+"/eliminar\") Error: " + ex.getMessage(), ExceptionUtils.getStackTrace(ex));
			throw new WebApplicationException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
		} catch (Exception ex) {
			logger.log(Level.ERROR, "empresa controller @PostMapping(\"/" + rutEmpresa + "/producto/" + gtin13
					+ "/empaque/"+ gtin14 +"/eliminar\") Error: " + ex.getMessage(), ExceptionUtils.getStackTrace(ex));

			throw new WebApplicationException("Ocurrió un error inesperado, intente nuevamente - " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
	}
	
	@Override
	public ResponseEntity<Representacion> exportarProducto(String rutEmpresa, Producto producto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Representacion> importarProducto(String rutEmpresa, Producto producto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Representacion> productos(String rutEmpresa) {
		// TODO Auto-generated method stub
		return null;
	}

}
