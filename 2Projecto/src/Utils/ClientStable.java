package Utils;

import Stable.IStable;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Classe para envio de mensagens relacionados com o Stable, para o servidor.
 *
 * @author Tiago Marques
 *
 */
public class ClientStable extends ClientSend implements IStable {

    /**
     * Construtor da classe, que vai criar um Stable remoto. Recebe como
     * parametros um endereço IP address e uma porta port usada para o envio de
     * mensagens
     *
     * @param address Endereço IP
     * @param port Porta para envio das mensagens
     */
    public ClientStable(InetAddress address, int port) {
        super(address, port);
        System.out.printf("\nCRIOU STABLE REMOTE\n");
    }

    /**
     * Método para chamar a função proceedToStable no servidor, com o envio dos
     * parametros adequados. Esta função vai simular o comportamento de um
     * cavalo horseID no estábulo.
     *
     * @param horseID ID do cavalo
     */
    @Override
    public void proceedToStable(int horseID) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(horseID);
        sendMessage(MsgType.PROCEEDTOSTABLE, param);
    }

    /**
     * Método para chamar a função callToPaddock no servidor. Esta função vai
     * reencaminhar os cavalos para o paddock.
     */
    @Override
    public void callToPaddock() {
        sendMessage(MsgType.CALLTOPADDOCK, null);
    }

    /**
     * Método para terminar a ligação e fechar a classe
     */
    public void close() {
        sendMessage(MsgType.CLOSE, null);
    }
}
