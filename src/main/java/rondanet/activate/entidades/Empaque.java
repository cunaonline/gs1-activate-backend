package rondanet.activate.entidades;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import common.rondanet.catalogo.core.utils.serializer.CustomDateTimeDeserializer;
import common.rondanet.catalogo.core.utils.serializer.CustomDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Información de Producto")
public class Empaque {

    @Schema(description = "Identificador del Empaque")
    private String gtin;

    @Schema(description = "Tipo de Empaque")
    private String empaque;
    
    @Schema(description = "Cantidad de unidades en el empaque")
    private String unidades;
    
    @Schema(hidden = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date fechaCreacion;

    @Schema(hidden = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date fechaEdicion;


    public Empaque() {
        super();
    }



	public Empaque(String gtin, String empaque, String unidades, Date fechaCreacion,
			Date fechaEdicion) {
		super();
		this.gtin = gtin;
		this.empaque = empaque;
		this.unidades = unidades;
		this.fechaCreacion = fechaCreacion;
		this.fechaEdicion = fechaEdicion;
	}



	public String getGtin() {
		return gtin;
	}


	public void setGtin(String gtin) {
		this.gtin = gtin;
	}

	public String getEmpaque() {
		return empaque;
	}

	public void setEmpaque(String empaque) {
		this.empaque = empaque;
	}

	public String getUnidades() {
		return unidades;
	}

	public void setUnidades(String unidades) {
		this.unidades = unidades;
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
}
