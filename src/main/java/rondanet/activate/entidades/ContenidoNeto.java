package rondanet.activate.entidades;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Contenido Neto")
public class ContenidoNeto {
      
    @Schema(description = "Cantidad del Producto")
    private String cantidad;

    @Schema(description = "Unidad  de medida.")
    private String unidad;

    
    public ContenidoNeto() {
        super();
    }


	public String getCantidad() {
		return cantidad;
	}


	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}


	public String getUnidad() {
		return unidad;
	}


	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}


	@Override
	public String toString() {
		return cantidad + " " + unidad ;
	}
	
}
