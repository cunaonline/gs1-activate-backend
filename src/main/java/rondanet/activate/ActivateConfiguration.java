package rondanet.activate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import common.rondanet.catalogo.core.entity.Param;
import jakarta.annotation.PostConstruct;
import rondanet.activate.configuracion.DespliegueConfiguration;
import rondanet.activate.configuracion.Propiedades;
import rondanet.activate.configuracion.S3Config;



public class ActivateConfiguration {

	/*
	 * @Autowired IParamRepository paramRepository;
	 */

    private DespliegueConfiguration configuracionDespliegue = new DespliegueConfiguration();

    public DespliegueConfiguration getConfiguracionDespliegue() {
        return this.configuracionDespliegue;
    }

    public void setConfiguracionDespliegue(DespliegueConfiguration despliegue) {
        this.configuracionDespliegue = despliegue;
    }

    public List<String> empresasASincronizarHaciaAtrasSeleccion;


    public boolean sincronizarEmpresa(String rut) {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivateConfiguration)) return false;
        ActivateConfiguration that = (ActivateConfiguration) o;
        return Objects.equals(getConfiguracionDespliegue(), that.getConfiguracionDespliegue());
    }

    @PostConstruct
    public void init() {
        createBucketUrlParam();
        cargarConfiguracion();

    }

    public void cargarConfiguracion() {

        Param despliqueFrontend = null;//paramRepository.findByNombreAndEliminadoIsFalse(Propiedades.DW_DESPLIEGUE_FRONTEND).orElse(null);
        Param despliqueTerceros = null;//paramRepository.findByNombreAndEliminadoIsFalse(Propiedades.DW_DESPLIEGUE_TERCEROS).orElse(null);
        Param despliqueCors = null;//paramRepository.findByNombreAndEliminadoIsFalse(Propiedades.DW_DESPLIEGUE_CORS).orElse(null);
        Param despliqueBucket = null;//paramRepository.findByNombreAndEliminadoIsFalse(Propiedades.DW_DESPLIEGUE_BUCKET).orElse(null);
        Param despliqueBucketUrl = null;//paramRepository.findByNombreAndEliminadoIsFalse(Propiedades.DW_DESPLIEGUE_BUCKET_URL).orElse(null);

        Param s3Id = null;//paramRepository.findByNombreAndEliminadoIsFalse(Propiedades.DW_S3_S3Id).orElse(null);
        Param s3Api = null;//paramRepository.findByNombreAndEliminadoIsFalse(Propiedades.DW_S3_S3APIKEY).orElse(null);

      
        S3Config s3Config = new S3Config(s3Id.getValor(), s3Api.getValor());

        DespliegueConfiguration despliegueConfiguration = new DespliegueConfiguration(
                    Boolean.valueOf(despliqueFrontend.getValor()),
                    Boolean.valueOf(despliqueTerceros.getValor()),
                    despliqueCors.getValor(),
                    despliqueBucket.getValor(),
                    despliqueBucketUrl.getValor(),
                    s3Config);

        this.configuracionDespliegue = despliegueConfiguration;

		/*
		 * Optional<Param> optionalEmpresasASincronizarVisibilidad =
		 * this.paramRepository.findByNombreAndEliminadoIsFalse(Propiedades.
		 * RUT_DE_EMPRESAS_A_SINCRONIZAR_HACIA_ATRAS);
		 * if(!optionalEmpresasASincronizarVisibilidad.isPresent()){
		 * saveParam(Propiedades.RUT_DE_EMPRESAS_A_SINCRONIZAR_HACIA_ATRAS, ""); }
		 * 
		 * Optional<Param> optionalEmpresasASincronizarHaciaAtrasSeleccion =
		 * this.paramRepository.findByNombreAndEliminadoIsFalse(Propiedades.
		 * RUT_DE_EMPRESAS_A_SINCRONIZAR_HACIA_ATRAS_SELECCION);
		 * if(!optionalEmpresasASincronizarHaciaAtrasSeleccion.isPresent()){
		 * saveParam(Propiedades.RUT_DE_EMPRESAS_A_SINCRONIZAR_HACIA_ATRAS_SELECCION,
		 * "true"); }
		 */
    }

    public void saveParam(String nombre, String value){
        Param param = new Param();
        param.setNombre(nombre);
        param.setValor(value);
       // param = paramRepository.save(param);
        param.setSId(param.getId());
      //  paramRepository.save(param);
    }

    void createBucketUrlParam(){
        Optional<Param> optionalParamBucket = null;//= this.paramRepository.findByNombreAndEliminadoIsFalse(Propiedades.DW_DESPLIEGUE_BUCKET);
        Optional<Param> optionalParamBucketUrl= null; //= this.paramRepository.findByNombreAndEliminadoIsFalse(Propiedades.DW_DESPLIEGUE_BUCKET_URL);
        Param param = new Param();
        if (optionalParamBucket.isPresent()) {
            param.setNombre(Propiedades.DW_DESPLIEGUE_BUCKET_URL);
            if(optionalParamBucketUrl.isPresent()){
                param = optionalParamBucketUrl.get();
            } else {
                param.setFechaCreacion();
            }
            param.setFechaEdicion();
            if (optionalParamBucket.get().getValor().contains("desarrollo")) {
                param.setValor("https://s3.us-east-2.amazonaws.com/rondanet.recursos.desarrollo");
            } else if(optionalParamBucket.get().getValor().contains("test")){
                param.setValor("https://s3.us-east-2.amazonaws.com/rondanet.recursos.test");
            }  else if(optionalParamBucket.get().getValor().contains("sqa")){
                param.setValor("https://s3.us-east-2.amazonaws.com/rondanet.recursos.sqa");
            } else {
                param.setValor("https://s3.us-east-2.amazonaws.com/rondanet.recursos");
            }
           // param = paramRepository.save(param);
            param.setSId(param.getId());
           // paramRepository.save(param);
        }
    }


    @Override
    public int hashCode() {
        return Objects.hash(getConfiguracionDespliegue());
    }

    @Override
    public String toString() {
        return "ActivateConfiguration{" +
                ", configuracionDespliegue=" + configuracionDespliegue +
                '}';
    }

}
