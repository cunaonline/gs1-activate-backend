package rondanet.activate.services.implementacion;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import common.rondanet.catalogo.core.utils.poiji.ExcelRNCliente;
import common.rondanet.catalogo.core.utils.poiji.ExcelRNProducto;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import common.rondanet.catalogo.core.exceptions.ModelException;
import common.rondanet.catalogo.core.exceptions.ServiceException;
import common.rondanet.catalogo.core.resources.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.rondanet.catalogo.core.exceptions.ServiceException;
import rondanet.activate.configuracion.S3FileManager;
import rondanet.activate.db.EmpresaDAO;
import rondanet.activate.entidades.Actividad;
import rondanet.activate.entidades.Empaque;
import rondanet.activate.entidades.Empresa;
import rondanet.activate.entidades.Marca;
import rondanet.activate.entidades.Producto;
import rondanet.activate.exeptions.EmpresaException;
import rondanet.activate.exeptions.MarcaException;
import rondanet.activate.exeptions.ProductoException;
import rondanet.activate.services.interfaces.IEmpresaService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmpresaService implements IEmpresaService {
	Logger logger = LogManager.getLogger(EmpresaService.class);

	@Autowired
	private EmpresaDAO empresaDAO;

	private S3FileManager s3FileManager;

	public EmpresaService(EmpresaDAO empresaDAO) {
		this.empresaDAO = empresaDAO;
		this.s3FileManager = s3FileManager;
	}

	@Override
	public Empresa getDetalleEmpresa(String rutEmpresa) {
		Empresa empresa = empresaDAO.buscarEmpresa(rutEmpresa);
		return empresa;
	}

	@Override
	public String guardarEmpresa(Empresa empresa) {
		String mensaje = "Se obtuvo la información de la Empresa";
		// Busco la empresa
		Empresa empresaObtenida = empresaDAO.buscarEmpresa(empresa.getRut());
		if (empresaObtenida == null) {
			empresaDAO.guardarEmpresa(empresa);
			mensaje = "Se adicionó la información de la Empresa";
		}
		return mensaje;
	}

	@Override
	public Integer adicionarMarca(String rutEmpresa, Integer tipo, String valor)
			throws ServiceException, MarcaException, EmpresaException {
		Integer idMarca = 0;
		// Busco la empresa
		Empresa empresa = empresaDAO.buscarEmpresa(rutEmpresa);
		if (empresa != null) {
			if (tipo == 0) {
				// Obtengo las marcas de la Empresa
				Set<Marca> marcas = empresa.getMarcas();
				// Filtro para buscar si ya existe el elemento.
				List filtrado = marcas.stream().filter(m -> m.equals(valor)).collect(Collectors.toList());

				if (filtrado.size() == 0) {
					idMarca = obtenerMarcaMayorId(marcas);
					Marca nuevaMarca = new Marca(++idMarca, valor);
					marcas.add(nuevaMarca);
					empresa.setMarcas(marcas);
					empresaDAO.guardarEmpresa(empresa);
				} else {
					logger.log(Level.INFO, "La Marca ya esta Registrada");
					throw new MarcaException("La Marca ya esta Registrada");
				}

			} else {
				// Obtengo las marcas de la Empresa
				Set<Marca> subMarcas = empresa.getSubMarcas();
				// Filtro para buscar si ya existe el elemento.
				List filtrado = subMarcas.stream().filter(m -> m.equals(valor)).collect(Collectors.toList());
				if (filtrado.size() == 0) {
					System.out.println("No Encontre");
					idMarca = obtenerMarcaMayorId(subMarcas);
					Marca nuevaMarca = new Marca(++idMarca, valor);
					subMarcas.add(nuevaMarca);
					empresa.setSubMarcas(subMarcas);
					empresaDAO.guardarEmpresa(empresa);
				} else {
					logger.log(Level.INFO, "La SubMarca ya esta Registrada");
					throw new MarcaException("La SubMarca ya esta Registrada");
				}
			}

		} else {
			throw new EmpresaException("No Existe la Empresa");
		}
		return idMarca;
	}

	@Override
	public List<Marca> eliminarMarca(String rutEmpresa, Integer tipo, Integer id)
			throws ServiceException, MarcaException, EmpresaException {
		List<Marca> marcasRetorno = new ArrayList<Marca>();
		// Busco la empresa
		Empresa empresa = empresaDAO.buscarEmpresa(rutEmpresa);
		if (empresa != null) {
			if (tipo == 0) {
				// Obtengo las marcas de la Empresa
				Set<Marca> marcas = empresa.getMarcas();
				// Filtro para buscar si ya existe el elemento.
				Optional<Marca> marcaFiltrada = marcas.stream().filter(m -> m.getId() == id).findFirst();
				if (marcaFiltrada.isPresent()) {
					empresa.getMarcas().remove(marcaFiltrada.get());
					empresaDAO.guardarEmpresa(empresa);
					return empresa.getMarcas().stream().sorted(Comparator.comparing(Marca::getId)).toList();
				} else {
					logger.log(Level.INFO, "La Marca no existe");
					throw new MarcaException("La Marca no existe");
				}
			} else {
				// Obtengo las marcas de la Empresa
				Set<Marca> submarcas = empresa.getSubMarcas();
				// Filtro para buscar si ya existe el elemento.
				Optional<Marca> marcaFiltrada = submarcas.stream().filter(m -> m.getId() == id).findFirst();
				if (marcaFiltrada.isPresent()) {
					empresa.getSubMarcas().remove(marcaFiltrada.get());
					empresaDAO.guardarEmpresa(empresa);
					marcasRetorno = empresa.getSubMarcas().stream().sorted(Comparator.comparing(Marca::getId)).toList();
				} else {
					logger.log(Level.INFO, "La SubMarca no existe");
					throw new MarcaException("La SubMarca no existe");
				}
			}
		} else {
			throw new EmpresaException("No existe una Empresa para este identificador");
		}

		return marcasRetorno;
	}

	@Override
	public Producto guardarProducto(String rutEmpresa, String codigoEmpresa, Producto producto)
			throws ServiceException, ProductoException, EmpresaException {
		// Busco la empresa
		Empresa empresa = empresaDAO.buscarEmpresa(rutEmpresa);
		if (empresa != null) {
			// Genero Gtin
			String gtin = generarGtin13(codigoEmpresa, empresa.getProductos());
			producto.setGtin(gtin);
			Instant original = Instant.now();
			producto.setFechaCreacion(Date.from(original));
			producto.setFechaEdicion(Date.from(original));
			producto.setEmpaques(new HashSet<>());
			empresa.getProductos().add(producto);

			// Guardar Actividad Sobre el Producto
			Actividad actividad = new Actividad(producto.getFechaCreacion(), producto.getGtin(),
					getDescripcionDelProducto(producto), producto.getEstado(), producto.getUsuario());

			empresa.getActividades().add(actividad);

			empresaDAO.guardarEmpresa(empresa);
		} else {
			throw new EmpresaException("No existe una Empresa para este identificador");
		}

		return producto;
	}
	@Override
	public void eliminarProducto(String rutEmpresa, String gtin)
			throws ServiceException, ProductoException, EmpresaException {
		// Busco la empresa
		Empresa empresa = empresaDAO.buscarEmpresa(rutEmpresa);
		if (empresa != null) {
			Optional<Producto> producto = empresa.getProductos().stream().filter(x -> x.getGtin().equals(gtin))
					.findFirst();
			if (producto.isPresent()) {
				empresa.getProductos().remove(producto.get());
				empresaDAO.guardarEmpresa(empresa);
			} else {
				throw new ProductoException("No existe un Producto para este identificador");
			}
		} else {
			throw new EmpresaException("No existe una Empresa para este identificador");
		}
	}
	@Override
	public Empaque guardarEmpaqueDelProducto(String rutEmpresa, String idProducto, Empaque empaque)
			throws ServiceException, ProductoException, EmpresaException {
		// Busco la empresa
		Empresa empresa = empresaDAO.buscarEmpresa(rutEmpresa);
		if (empresa != null) {
			// Producto pro = empresaDAO.getProductoByEmpresa(rutEmpresa,idProducto);

			Optional<Producto> producto = empresa.getProductos().stream().filter(x -> x.getGtin().equals(idProducto))
					.findFirst();
			if (producto.isPresent()) {
				// Obtengo los Empaques
				Set<Empaque> empaques = producto.get().getEmpaques();

				// Genero Gtin del Empaque
				String gtin14 = generarGtin14(idProducto, empaques);
				empaque.setGtin(gtin14);
				Instant original = Instant.now();
				empaque.setFechaCreacion(Date.from(original));
				empaque.setFechaEdicion(Date.from(original));
				// Guardo el Empaque
				producto.get().getEmpaques().add(empaque);
				// Guardo el Producto
				empresa.getProductos().add(producto.get());
				empresaDAO.guardarEmpresa(empresa);
			} else {
				throw new ProductoException("No existe un Producto para este identificador");
			}
		} else {
			throw new EmpresaException("No existe una Empresa para este identificador");
		}

		return empaque;
	}
	@Override
	public void eliminarEmpaqueDelProducto(String rutEmpresa, String idProducto, String idEmpaque)
			throws ServiceException, ProductoException, EmpresaException {
		// Busco la empresa
		Empresa empresa = empresaDAO.buscarEmpresa(rutEmpresa);
		if (empresa != null) {
			Optional<Producto> producto = empresa.getProductos().stream().filter(x -> x.getGtin().equals(idProducto))
					.findFirst();
			if (producto.isPresent()) {
				// Obtengo los Empaques
				Optional<Empaque> empaque = producto.get().getEmpaques().stream().filter(x -> x.getGtin().equals(idProducto))
						.findFirst();
				if (empaque.isPresent()) {
					producto.get().getEmpaques().remove(empaque.get());
					empresa.getProductos().add(producto.get());
					empresaDAO.guardarEmpresa(empresa);
				}
			} else {
				throw new ProductoException("No existe un Producto para este identificador");
			}
		} else {
			throw new EmpresaException("No existe un Producto para este identificador");
		}
	}

	@Override
	public String guardarImagenDelProducto(String rutEmpresa, Integer gtin, MultipartFile file) throws ServiceException {

		String imageProducto = "210002150018/productos/img/67836066_7791290007543_26667d56-559e-4361-9451-b10418e773f2.jpg";

		return null;
	}

	private Integer obtenerMarcaMayorId(Set<Marca> listaMarcas) {

		Integer id = listaMarcas.stream().max(Comparator.comparingInt(Marca::getId)).get().getId();

		return id;
	}

	private String generarGtin13(String codigoEmpresa, Set<Producto> productos) throws ProductoException {

		String gtin = "";
		String paisCodigoEmpresa = "773" + codigoEmpresa;
		// Obtengo productos para pais-codigo
		List<String> productosObtenidosPaisCodigoEmpresa = productos.stream()
				.filter(m -> m.getGtin().toString().contains(paisCodigoEmpresa))
				.map(x -> x.getGtin().substring(0, x.getGtin().length() - 1)).sorted().collect(Collectors.toList());

		if (productosObtenidosPaisCodigoEmpresa.size() > 0) {
			// Obtener mayor Gtin Generado
			String mayorGtin = productosObtenidosPaisCodigoEmpresa.stream().max(Comparator.naturalOrder()).get();
			// Obtener numero del mayor Producto
			Integer numeroProducto = numeroProductoSinPaisCodigoEmpresa(mayorGtin, paisCodigoEmpresa);
			// Verificar si coinciden cantidad de productos con el mayor numero de Producto
			if (numeroProducto != productosObtenidosPaisCodigoEmpresa.size()) {
				// Recorro Gtin para encontrar hueco
				for (int i = 1; i <= productosObtenidosPaisCodigoEmpresa.size(); i++) {
					if (numeroProductoSinPaisCodigoEmpresa(productosObtenidosPaisCodigoEmpresa.get(i - 1),
							paisCodigoEmpresa) != i) {
						gtin = String.valueOf(Long.parseLong(productosObtenidosPaisCodigoEmpresa.get(i - 1)) - 1);
						break;
					}
				}
			} else if (numeroProducto == productosObtenidosPaisCodigoEmpresa.size()
					&& ((paisCodigoEmpresa.length() == 9 && (numeroProducto + 1) <= 999))
					|| (paisCodigoEmpresa.length() == 7 && (numeroProducto + 1) <= 99999)) {
				// Verifico que no supere el maximo de Producto
				gtin = String.valueOf(Long.parseLong(mayorGtin) + 1);

			} else {
				throw new ProductoException("No hay códigos disponibles para asignar");
			}
		} else {// Primer Producto
			if (paisCodigoEmpresa.length() == 9) {
				gtin = paisCodigoEmpresa + "001";
			} else {
				gtin = paisCodigoEmpresa + "00001";
			}
		}
		return generarDigitoVerificadorGtin(gtin);
	}

	private String generarGtin14(String gtinProducto, Set<Empaque> empaques) throws ProductoException {

		String gtin = "";
		// Obtengo productos para pais-codigo
		List<String> empaquesObtenidosDelProducto = empaques.stream()
				.filter(m -> m.getGtin().toString().contains(gtinProducto.substring(1, gtinProducto.length() - 1)))
				.map(x -> x.getGtin().substring(0, x.getGtin().length() - 1)).sorted().collect(Collectors.toList());

		if (empaquesObtenidosDelProducto.size() > 0) {
			// Obtener mayor Gtin Generado
			if (empaquesObtenidosDelProducto.size() != 10) {
				String mayorGtin = empaquesObtenidosDelProducto.stream().max(Comparator.naturalOrder()).get();
				gtin = String.valueOf(Long.parseLong(mayorGtin) + Long.parseLong("1000000000000"));
			} else {
				throw new ProductoException("Exede el numero de GTIN14 a Generar");
			}
		} else {
			gtin = "1" + gtinProducto.substring(0, gtinProducto.length() - 1);
		}
		return generarDigitoVerificadorGtin(gtin);
	}

	private Integer numeroProductoSinPaisCodigoEmpresa(String producto, String paisCodigoEmpresa) {

		return Integer.parseInt(String.valueOf(producto.substring(paisCodigoEmpresa.length(), producto.length())));
	}

	private String generarDigitoVerificadorGtin(String gtin) {
		Integer digitosPares = 0, digitoImpares = 0;
		Integer digitoVerificador = 0;
		// Recorro el Gtin para sumar los digitos
		for (int i = 1; i <= gtin.length(); i++) {
			if (i % 2 == 0) {
				digitosPares += Integer.parseInt(String.valueOf(gtin.charAt(gtin.length() - i))); // Almaceno posiciones
																									// Pares
			} else {
				digitoImpares += Integer.parseInt(String.valueOf(gtin.charAt(gtin.length() - i))); // Almaceno
																									// posiciones
																									// Impares
			}
		}
		// Sumo digitos
		Integer sumaDigitos = digitosPares + digitoImpares * 3;
		digitoVerificador = (((((sumaDigitos) / 10) + 1) * 10) - sumaDigitos) % 10;

		return gtin + digitoVerificador;
	}

	private String getDescripcionDelProducto(Producto producto) {

		return producto.getGtin() + " " + producto.getDescripcion() + " " + producto.getVariedad() + " "
				+ producto.getMarca() + " " + producto.getContenidoNeto().toString();
	}

	@Override
	public List<ExcelProduct> InsertExcel(List<ExcelProduct> products, UsuarioPrincipal user, Boolean updateExistent,
			Boolean deleteExistent, Boolean deleteAll, boolean paraListaDeVenta) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
