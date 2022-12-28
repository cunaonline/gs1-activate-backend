package rondanet.activate.entidades;

import java.util.Date;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import common.rondanet.catalogo.core.utils.serializer.CustomDateTimeDeserializer;
import common.rondanet.catalogo.core.utils.serializer.CustomDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import rondanet.activate.enums.EstadoProducto;

@Schema(description = "Información de Producto")
public class Producto {

    @Schema(description = "Identificador del Producto")
    private String gtin;

    @Schema(description = "Catidad de cajas")
    private String descripcion;

    @Schema(description = "Marca")
    private String marca;

    @Schema(description = "Sub-Marca")
    private String subMarca;

    @Schema(description = "Variedad")
    private String variedad;

    @Schema(description = "Contenido Neto")
    private ContenidoNeto contenidoNeto;
    
    @Schema(description = "Mercado Objetivo")
    private Set<String> mercadoObjetivo;

    @Schema(description = "Categoría global de Producto")
    private Integer gpc;
    
    @Schema(description = "Estado")
    private EstadoProducto estado;
    
    @Schema(description = "Empaques del Producto")
    private Set<Empaque> empaques;

    @Schema(description = "Imagen del Producto")
    private String foto;
    
    
    @Schema(hidden = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date fechaCreacion;

    @Schema(hidden = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date fechaEdicion;
    
    @Schema(description = "Estado Eliminado")
    private Boolean eliminado;
    
    @Schema(description = "Usuario que esta logueado")
    private String usuario;
    
    public Producto() {
        super();
    }

	public Producto(String gtin, String descripcion, String marca, String subMarca, String variedad,
			ContenidoNeto contenidoNeto, Set<String> mercadoObjetivo, Integer gpc, EstadoProducto estado,
			Set<Empaque> empaques, String foto, Date fechaCreacion, Date fechaEdicion, Boolean eliminado,
			String usuario) {
		super();
		this.gtin = gtin;
		this.descripcion = descripcion;
		this.marca = marca;
		this.subMarca = subMarca;
		this.variedad = variedad;
		this.contenidoNeto = contenidoNeto;
		this.mercadoObjetivo = mercadoObjetivo;
		this.gpc = gpc;
		this.estado = estado;
		this.empaques = empaques;
		this.foto = foto;
		this.fechaCreacion = fechaCreacion;
		this.fechaEdicion = fechaEdicion;
		this.eliminado = eliminado;
		this.usuario = usuario;
	}


	public String getGtin() {
		return gtin;
	}

	public void setGtin(String gtin) {
		this.gtin = gtin;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getSubMarca() {
		return subMarca;
	}

	public void setSubMarca(String subMarca) {
		this.subMarca = subMarca;
	}

	public String getVariedad() {
		return variedad;
	}

	public void setVariedad(String variedad) {
		this.variedad = variedad;
	}

	public ContenidoNeto getContenidoNeto() {
		return contenidoNeto;
	}

	public void setContenidoNeto(ContenidoNeto contenidoNeto) {
		this.contenidoNeto = contenidoNeto;
	}

	public Set<String> getMercadoObjetivo() {
		return mercadoObjetivo;
	}

	public void setMercadoObjetivo(Set<String> mercadoObjetivo) {
		this.mercadoObjetivo = mercadoObjetivo;
	}

	public Integer getGpc() {
		return gpc;
	}

	public void setGpc(Integer gpc) {
		this.gpc = gpc;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public EstadoProducto getEstado() {
		return estado;
	}

	public void setEstado(EstadoProducto estado) {
		this.estado = estado;
	}

	public Set<Empaque> getEmpaques() {
		return empaques;
	}

	public void setEmpaques(Set<Empaque> empaques) {
		this.empaques = empaques;
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
	
	public Boolean getEliminado() {
		return eliminado;
	}

	public void setEliminado(Boolean eliminado) {
		this.eliminado = eliminado;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
}
