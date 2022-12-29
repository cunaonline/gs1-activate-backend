package rondanet.activate.api.controller.interfaz;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import rondanet.activate.entidades.Empaque;
import rondanet.activate.entidades.Empresa;
import rondanet.activate.entidades.Producto;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import common.rondanet.catalogo.core.resources.Representacion;

import javax.ws.rs.WebApplicationException;
import java.util.List;

@Tag(name = "Empresa Activate", description = "Maneja la información de las empresa, crea Marcas, SubMarcas, adiciona nuevos Productos a la lista de Productos,"
		+ " Carga productos Masivamente mediante un Archivo en Formato Excel")
@RequestMapping("/empresa")
public interface IEmpresaController {

	@Operation(summary = "Obtiene la Informacion de la Empresa Loqueada.", description = "Obtiene la Informacion de la Empresa Loqueada.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Operación satisfactoria", content = @Content(schema = @Schema(implementation = Representacion.class))),
			@ApiResponse(responseCode = "400", description = "Error en la petición", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "409", description = "Ya existe el recurso", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "503", description = "Servidor no disponible", content = @Content(schema = @Schema(implementation = WebApplicationException.class))) })
	@GetMapping("/detalle")
	ResponseEntity<Representacion> detalleEmpresa(
			@Parameter(description = "Rut de la empresa a buscar") @RequestParam("rutEmpresa") String rutEmpresa);

	@Operation(summary = "Inserta una Marca O Submarca en la Empresa Loqueada.", description = "Inserta una Marca O Submarca en la Empresa Loqueada.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Operación satisfactoria", content = @Content(schema = @Schema(implementation = Representacion.class))),
			@ApiResponse(responseCode = "400", description = "Error en la petición", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "409", description = "Ya existe el recurso", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "503", description = "Servidor no disponible", content = @Content(schema = @Schema(implementation = WebApplicationException.class))) })
	@PostMapping("/{rutEmpresa}/marcas/{tipo}/adicionar/{valor}")
	ResponseEntity<Representacion> adicionarMarca(
			@Parameter(description = "Rut de la empresa a buscar") @PathVariable("rutEmpresa") String rutEmpresa,
			@Parameter(description = "Tipo") @PathVariable("tipo") Integer tipo,
			@Parameter(description = "Valor") @PathVariable("valor") String valor);

	@Operation(summary = "Elimina una Marca O Submarca en la Empresa Loqueada.", description = "Elimina una Marca O Submarca en la Empresa Loqueada.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Operación satisfactoria", content = @Content(schema = @Schema(implementation = Representacion.class))),
			@ApiResponse(responseCode = "400", description = "Error en la petición", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "409", description = "Ya existe el recurso", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "503", description = "Servidor no disponible", content = @Content(schema = @Schema(implementation = WebApplicationException.class))) })
	@PostMapping("/{rutEmpresa}/marcas/{tipo}/eliminar/{valor}")
	ResponseEntity<Representacion> eliminarMarca(
			@Parameter(description = "Rut de la empresa a buscar") @PathVariable("rutEmpresa") String rutEmpresa,
			@Parameter(description = "Tipo") @PathVariable("tipo") Integer tipo,
			@Parameter(description = "Id de Marca") @PathVariable("id") Integer id);

	@Operation(summary = "Crea la Empresa Activate.", description = "Crea la Empresa Activate.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Operación satisfactoria", content = @Content(schema = @Schema(implementation = Representacion.class))),
			@ApiResponse(responseCode = "400", description = "Error en la petición", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "409", description = "Ya existe el recurso", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "503", description = "Servidor no disponible", content = @Content(schema = @Schema(implementation = WebApplicationException.class))) })
	@PostMapping("/adicionar")
	ResponseEntity<Representacion> crearEmpresa(
			@Parameter(description = "Datos de empresa") @RequestBody Empresa empresa);

	@Operation(summary = "Genera un nuevo Producto y devuelve el producto nuevo", description = "Genera un nuevo Producto y devuelve el producto nuevo")
	@ApiResponses(value = {

			@ApiResponse(responseCode = "200", description = "Operación satisfactoria", content = @Content(schema = @Schema(implementation = Representacion.class))),
			@ApiResponse(responseCode = "400", description = "Error en la petición", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "409", description = "Ya existe el recurso", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "503", description = "Servidor no disponible", content = @Content(schema = @Schema(implementation = WebApplicationException.class))) })

	@PostMapping("/{rutEmpresa}/producto/adicionar")
	ResponseEntity<Representacion> crearProducto(
			@Parameter(description = "Rut de la empresa a buscar") @PathVariable("rutEmpresa") String rutEmpresa,
			@RequestBody Producto producto);

	@Operation(summary = "Elimina un Producto y devuelve un mensaje", description = "Elimina un Producto y devuelve un mensaje")
	@ApiResponses(value = {

			@ApiResponse(responseCode = "200", description = "Operación satisfactoria", content = @Content(schema = @Schema(implementation = Representacion.class))),
			@ApiResponse(responseCode = "400", description = "Error en la petición", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "409", description = "Ya existe el recurso", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "503", description = "Servidor no disponible", content = @Content(schema = @Schema(implementation = WebApplicationException.class))) })

	@PostMapping("/{rutEmpresa}/producto/{gtin}/actualizar")
	ResponseEntity<Representacion> actualizarProducto(
			@Parameter(description = "Rut de la empresa a buscar") @PathVariable("rutEmpresa") String rutEmpresa,
			@Parameter(description = "Identificador del Producto") @PathVariable("gtin") String gtin);

	@Operation(summary = "Adiciona la Imagen de un Porducto ya creado.", description = "Adiciona la Imagen de un Porducto ya creado.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Operación satisfactoria", content = @Content(schema = @Schema(implementation = Representacion.class))),
			@ApiResponse(responseCode = "400", description = "Error en la petición", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "409", description = "Ya existe el recurso", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "503", description = "Servidor no disponible", content = @Content(schema = @Schema(implementation = WebApplicationException.class))) })

	@PostMapping("/{rutEmpresa}/producto/{gtin}/imagen")
	ResponseEntity<Representacion> cargarImagenDelProducto(
			@Parameter(description = "Rut de la empresa a buscar") @PathVariable("rutEmpresa") String rutEmpresa,
			@Parameter(description = "Identificador del Producto") @PathVariable("rutEmpresa") Integer gtin,
			@Parameter(description = "Imagen  del Producto a cargar") @RequestParam("file") MultipartFile file);

	@Operation(summary = "Genera un nuevo Empaque y devuelve el Empaque nuevo", description = "Genera un nuevo Empaque y devuelve el Empaque nuevo")
	@ApiResponses(value = {

			@ApiResponse(responseCode = "200", description = "Operación satisfactoria", content = @Content(schema = @Schema(implementation = Representacion.class))),
			@ApiResponse(responseCode = "400", description = "Error en la petición", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "409", description = "Ya existe el recurso", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "503", description = "Servidor no disponible", content = @Content(schema = @Schema(implementation = WebApplicationException.class))) })

	@PostMapping("/{rutEmpresa}/producto/{gtin}/empaque/adicionar")
	ResponseEntity<Representacion> crearEmpaqueDelProducto(
			@Parameter(description = "Rut de la empresa a buscar") @PathVariable("rutEmpresa") String rutEmpresa,
			@Parameter(description = "Gtin del Producto buscar") @PathVariable("gtin") String gtin,
			@RequestBody Empaque empaque);

	@Operation(summary = "Elimina un Empaque para un Producto de una empresa", description = "Elimina un Empaque para un Producto de una empresa")
	@ApiResponses(value = {

			@ApiResponse(responseCode = "200", description = "Operación satisfactoria", content = @Content(schema = @Schema(implementation = Representacion.class))),
			@ApiResponse(responseCode = "400", description = "Error en la petición", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "409", description = "Ya existe el recurso", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "503", description = "Servidor no disponible", content = @Content(schema = @Schema(implementation = WebApplicationException.class))) })

	@PostMapping("/{rutEmpresa}/producto/{gtin13}/empaque/{gtin14}/eliminar")
	ResponseEntity<Representacion> eliminarEmpaqueDelProducto(
			@Parameter(description = "Rut de la empresa a buscar") @PathVariable("rutEmpresa") String rutEmpresa,
			@Parameter(description = "Gtin del Producto") @PathVariable("gtin13") String gtin13,
			@Parameter(description = "Gtin del Empaque") @PathVariable("gtin14") String gtin14);

	@Operation(summary = "Obtiene la Informacion de la Empresa Loqueada.", description = "Obtiene la Informacion de la Empresa Loqueada.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Operación satisfactoria", content = @Content(schema = @Schema(implementation = Representacion.class))),
			@ApiResponse(responseCode = "400", description = "Error en la petición", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "409", description = "Ya existe el recurso", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "503", description = "Servidor no disponible", content = @Content(schema = @Schema(implementation = WebApplicationException.class))) })

	@GetMapping("/productos")
	ResponseEntity<Representacion> productos(
			@Parameter(description = "Rut de la empresa a buscar") @RequestParam("rutEmpresa") String rutEmpresa);

	@Operation(summary = "Genera un nuevo Producto y devuelve el GTIN de ese producto nuevo", description = "Genera un nuevo Producto y devuelve el GTIN de ese producto nuevo")
	@ApiResponses(value = {

			@ApiResponse(responseCode = "200", description = "Operación satisfactoria", content = @Content(schema = @Schema(implementation = Representacion.class))),
			@ApiResponse(responseCode = "400", description = "Error en la petición", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "409", description = "Ya existe el recurso", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "503", description = "Servidor no disponible", content = @Content(schema = @Schema(implementation = WebApplicationException.class))) })

	@GetMapping("/producto/exportar")
	ResponseEntity<Representacion> exportarProducto(
			@Parameter(description = "Rut de la empresa a buscar") @RequestParam("rutEmpresa") String rutEmpresa,
			Producto producto);

	@Operation(summary = "Genera un nuevo Producto y devuelve el GTIN de ese producto nuevo", description = "Genera un nuevo Producto y devuelve el GTIN de ese producto nuevo")
	@ApiResponses(value = {

			@ApiResponse(responseCode = "200", description = "Operación satisfactoria", content = @Content(schema = @Schema(implementation = Representacion.class))),
			@ApiResponse(responseCode = "400", description = "Error en la petición", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "409", description = "Ya existe el recurso", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content(schema = @Schema(implementation = WebApplicationException.class))),
			@ApiResponse(responseCode = "503", description = "Servidor no disponible", content = @Content(schema = @Schema(implementation = WebApplicationException.class))) })

	@GetMapping("/producto/importar")
	ResponseEntity<Representacion> importarProducto(
			@Parameter(description = "Rut de la empresa a buscar") @RequestParam("rutEmpresa") String rutEmpresa,
			Producto producto);
}