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

    private InetAddress address;        // Ip da maquina 
    private int port;                   // Porta da maquina

    /**
     * @param address Endereço IP
     * @param port Porta para envio das mensagens
     */
    public ClientSend(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    /**
     * Método que envia uma mensagem para o servidor
     *
     * @param type Tipo de mensagem a enviar
     * @return Resposta do mesmo tipo da mensagem enviada
     * @throws java.lang.ClassNotFoundException ClassNotFoundException 
     */
    protected Object sendMessage(MsgType type) throws ClassNotFoundException {
        return sendMessage(type, null);
    }

    /**
     * Método que envia uma mensagem para o servidor
     *
     * @param type Tipo de mensagem a enviar
     * @param param Parametros da mensagem do tipo type, quando existem
     *
     * @return Mensagem de resposta do servidor
     */
    protected Object sendMessage(MsgType type, ArrayList<Object> param) {
        boolean done = false;
        
        ObjectInputStream in;
        ObjectOutputStream out;
        Msg msgOut = new Msg();
        
        while (!done) {
            try {
                System.err.printf("\nA criar conexão com Servidor. Mensagem do tipo: " + type);
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
            } catch (ClassNotFoundException ex) {
            }
        }
        return msgOut;
    }
}
