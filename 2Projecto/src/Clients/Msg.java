/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clients;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Miguel Maia
 */
public class Msg implements Serializable {

    private MsgType type;                   // Tipo de mensagem
    private ArrayList<Object> param;        // Array de parametros 

    /**
     * Método para retornar tipo da mensagem
     *
     * @return Tipo de mensagem
     */
    public MsgType getType() {
        return type;
    }

    /**
     * Método para atualizar tipo de mensagem
     *
     * @param type Tipo da mensagem a atualizar
     */
    public void setType(MsgType type) {
        this.type = type;
    }

    /**
     * Método que devolve um array de parametros necessários conforme o tipo de mensagem
     *
     * @return ArrayList de parametros
     */
    public ArrayList<Object> getParam() {
        return param;
    }

    /**
     * Método para atualizar um conjunto de parametros
     *
     * @param param Parametros a atualizar
     */
    public void setParam(ArrayList<Object> param) {
        this.param = param;
    }
}
