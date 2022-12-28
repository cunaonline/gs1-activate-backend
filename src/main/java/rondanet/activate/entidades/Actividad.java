package rondanet.activate.entidades;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import common.rondanet.catalogo.core.utils.serializer.CustomDateTimeDeserializer;
import common.rondanet.catalogo.core.utils.serializer.CustomDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import rondanet.activate.enums.EstadoProducto;

@Schema(description = "Actividad realizada por la empresa")
public class Actividad {

    
    @Schema(description = "Identificador del Producto")
    private String gtin;
    
    @Schema(description = "Descipcion de la Actividad")
    private String descripcion;

    @Schema(hidden = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date fecha;
    
    @Schema(description = "Estado de la Actividad")
    private EstadoProducto estado;

    @Schema(description = "Usuario que realiza la Actividad")
    private String usuario;

    public Actividad() {
        super();
    }

	public Actividad(Date fecha, String gtin, String descripcion, EstadoProducto estado, String usuario) {
		super();
		this.fecha = fecha;
		this.gtin = gtin;
		this.descripcion = descripcion;
		this.estado = estado;
		this.usuario = usuario;
	}

	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
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


	public EstadoProducto getEstado() {
		return estado;
	}


	public void setEstado(EstadoProducto estado) {
		this.estado = estado;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}
