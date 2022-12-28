package rondanet.activate.entidades;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import common.rondanet.catalogo.core.utils.serializer.CustomDateTimeDeserializer;
import common.rondanet.catalogo.core.utils.serializer.CustomDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Empresa Activate")
@Document(collection = "Empresa")
public class Empresa {

	@Id
	private String id;

	@Schema(description = "Identificador de afiliación con GS1.")
	private String gln;

	@Schema(description = "Lista de la empresa.")
	private Set<String> prefijos;

	@Schema(description = "Código interno de la empresa.")
	private String codigoInterno;

	@Schema(description = "Razón social de la empresa.")
	private String razonSocial;

	@Schema(description = "Nombre de la empresa.")
	private String nombre;

	@Schema(description = "Identificador de la empresa. Es único para cada empresa.", required = true)
	private String rut;
	@Schema(description = "Marcas creadas de productos.")
	private Set<Marca> marcas = new HashSet<>();

	@Schema(description = "SubMarcas creadas de productos.")
	private Set<Marca> subMarcas = new HashSet<>();

	@ArraySchema(arraySchema = @Schema(description = "Productos de la empresa"))
	private Set<Producto> productos = new HashSet<Producto>();

	@ArraySchema(arraySchema = @Schema(description = "Movimiento realizados sobre los productos por la empresa"))
	private Set<Actividad> actividades = new HashSet<Actividad>();

	@Schema(hidden = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date fechaCreacion;

	@Schema(hidden = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date fechaEdicion;

	public Empresa() {
		super();
	}

	public Empresa(String gln, Set<String> prefijos, String codigoInterno, String razonSocial, String nombre,
			String rut, Set<Marca> marcas, Set<Marca> subMarcas, Set<Producto> productos, Set<Actividad> actividades,
			Date fechaCreacion, Date fechaEdicion) {
		super();
		this.gln = gln;
		this.prefijos = prefijos;
		this.codigoInterno = codigoInterno;
		this.razonSocial = razonSocial;
		this.nombre = nombre;
		this.rut = rut;
		this.marcas = marcas;
		this.subMarcas = subMarcas;
		this.productos = productos;
		this.actividades = actividades;
		this.fechaCreacion = fechaCreacion;
		this.fechaEdicion = fechaEdicion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGln() {
		return gln;
	}

	public void setGln(String gln) {
		this.gln = gln;
	}

	public Set<String> getPrefijos() {
		return prefijos;
	}

	public void setPrefijos(Set<String> prefijos) {
		this.prefijos = prefijos;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaEdicion() {
		return fechaEdicion;
	}

	public void setFechaEdicion(Date fechaEdicion) {
		this.fechaEdicion = fechaEdicion;
	}

	public String getCodigoInterno() {
		return codigoInterno;
	}

	public void setCodigoInterno(String codigoInterno) {
		this.codigoInterno = codigoInterno;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public Set<Marca> getMarcas() {
		return marcas;
	}

	public void setMarcas(Set<Marca> marca) {
		this.marcas = marca;
	}

	public Set<Marca> getSubMarcas() {
		return subMarcas;
	}

	public void setSubMarcas(Set<Marca> subMarca) {
		this.subMarcas = subMarca;
	}

	public Set<Producto> getProductos() {
		return productos;
	}

	public void setProductos(Set<Producto> productos) {
		this.productos = productos;
	}

	public Set<Actividad> getActividades() {
		return actividades;
	}

	public void setActividades(Set<Actividad> actividades) {
		this.actividades = actividades;
	}
}
