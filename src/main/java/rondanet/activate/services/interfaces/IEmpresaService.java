package rondanet.activate.services.interfaces;

import common.rondanet.catalogo.core.exceptions.ServiceException;
import common.rondanet.catalogo.core.resources.*;
import rondanet.activate.entidades.Empaque;
import rondanet.activate.entidades.Empresa;
import rondanet.activate.entidades.Marca;
import rondanet.activate.entidades.Producto;
import rondanet.activate.exeptions.EmpresaException;
import rondanet.activate.exeptions.MarcaException;
import rondanet.activate.exeptions.ProductoException;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface IEmpresaService {

	/**
	 *
	 * Este método inserta o actualiza según sea el caso los {@link Producto}
	 * pasados por parámetros siguiendo la siguiente lógica de precedencia en las
	 * acciones a ejecutar:
	 * <p>
	 * <p>
	 * - Primero si se solicita se eliminan todos los {@link Producto}
	 * </p>
	 * <p>
	 * - Luego si se solicita se eliminan los {@link Producto} existentes
	 * </p>
	 * <p>
	 * - Finalmente si se solicita se actualizan los {@link Producto} existentes
	 * </p>
	 *
	 * </p>
	 *
	 *
	 * @param productos
	 * @param user
	 * @param updateExistent
	 * @param deleteExistent
	 * @param deleteAll
	 * @throws ServiceException
	 * @return
	 */
	List<ExcelProduct> InsertExcel(List<ExcelProduct> products, UsuarioPrincipal user, Boolean updateExistent,
			Boolean deleteExistent, Boolean deleteAll, boolean paraListaDeVenta) throws ServiceException;

	Empresa getDetalleEmpresa(String rutEmpresa) throws ServiceException;

	String guardarEmpresa(Empresa empresa) throws ServiceException;

	Integer adicionarMarca(String rutEmpresa, Integer tipo, String valor) throws ServiceException, MarcaException, EmpresaException;

	List<Marca> eliminarMarca(String rutEmpresa, Integer tipo, Integer id) throws ServiceException, MarcaException, EmpresaException;

	Producto guardarProducto(String rutEmpresa, String codigoEmpresa, Producto producto)
			throws ServiceException, ProductoException, EmpresaException;

	void eliminarProducto(String rutEmpresa, String gtinProducto)
			throws ServiceException, ProductoException, EmpresaException;

	String guardarImagenDelProducto(String rutEmpresa, Integer gtin, MultipartFile file) throws ServiceException;

	Empaque guardarEmpaqueDelProducto(String rutEmpresa, String codigoEmpresa, Empaque producto)
			throws ServiceException, ProductoException, EmpresaException;

	void eliminarEmpaqueDelProducto(String rutEmpresa, String idProducto, String idEmpaque)
			throws ServiceException, ProductoException, EmpresaException;

}
