package br.com.techtoy.techtoy.common;

import br.com.techtoy.techtoy.model.exceptions.ResourceBadRequest;

public class ChecaValores {

    public static void verificaValorInt(Integer numero){
        if(numero<=0){
            throw new ResourceBadRequest("Não pode ter valor zero ou negativo");
        }
    }

    public static void verificaValorDouble(Double numero){
        if(numero<=0){
            throw new ResourceBadRequest("Não pode ter valor zero ou negativo");
        }
    }
}
