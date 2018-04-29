package Clients;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Classe que define o envio de uma mensagem entre cliente e servidor
 *
 * @author Miguel Maia
 */
public class ClientSend {

    private InetAddress address;        // Ip da maquina com a entidade que realiza a função pretendida
    private int port;                   // Porta da maquina com a entidade que realiza a função pretendida

    /**
     * Construtor da classe, recebe como parametros um endereço IP address e uma
     * porta port usada para o envio de mensagens
     *
     * @param address Endereço IP
     * @param port Porta para envio das mensagens
     */
    public ClientSend(InetAddress address, int port) {
        this.address = address;
        this.port = port;
        // System.out.printf("\nCRIOU REMOTESEND\n");
    }

    /**
     * Método que envia uma mensagem para o servidor
     *
     * @param type Tipo de mensagem a enviar, ou seja, função desejada
     * @return Resposta do mesmo tipo da mensagem enviada
     * @throws java.lang.ClassNotFoundException ClassNotFoundException 
     */
    protected Object sendMessage(MsgType type) throws ClassNotFoundException {
        return sendMessage(type, null);
    }

    /**
     * Método que envia uma mensagem para o servidor
     *
     * @param type Tipo de mensagem a enviar, ou seja, função desejada
     * @param param Parametros necessarios para mensagem do tipo type
     *
     * @return Mensagem de resposta do servidor
     */
    protected Object sendMessage(MsgType type, ArrayList<Object> param) {
        boolean done = false;
        //Socket skt= null;
        ObjectInputStream in;
        ObjectOutputStream out;
        Msg msgOut = new Msg();
        while (!done) {
            try {
                System.err.printf("\nCriar conexão com Servidor para enviar msg do tipo: " + type);
                Socket skt = new Socket(address, port);
                out = new ObjectOutputStream(skt.getOutputStream());
                in = new ObjectInputStream(skt.getInputStream());
                msgOut.setType(type);
                if (param == null) {
                    param = new ArrayList<>();
                }
                msgOut.setParam(param);
                out.writeObject(msgOut);
                out.flush();

                do {
                    msgOut = (Msg) in.readObject();
                } while (msgOut.getType() != type);

                out.close();
                in.close();
                skt.close();
                done = true;
            } catch (IOException e) {
                //System.err.printf("\nTentativa("+tryCount+" )para criar conexão com Servidor...");
            } catch (ClassNotFoundException ex) {
                System.err.printf("\nERRO GRAVE!");
            }
        }
        return msgOut;
    }
}
