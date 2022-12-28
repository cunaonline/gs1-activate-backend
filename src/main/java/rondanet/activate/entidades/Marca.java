package rondanet.activate.entidades;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Marca de Producto")
public class Marca {

	@Schema(description = "Identificador de la Marca")
	private Integer id;

	@Schema(description = "Nombre de la Marca")
	private String nombre;

	public Marca() {
		super();
	}

	public Marca(Integer id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof String) {

			String tmpMarca = (String) obj;

			if (this.nombre.toUpperCase().equals(tmpMarca.toUpperCase())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
}
