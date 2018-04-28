package Clients;

import Stable.*;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Classe para envio de mensagens relacionados com o Stable, para o servidor.
 *
 * @author cristianacarvalho
 *
 */
public class ClientStable extends ClientSend implements IStable_Broker, IStable_Horses {

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
        System.out.printf("\nSTABLE CLIENT\n");
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
                System.err.println("SUMMON HGORESSSSSSS PADOOOOOOOOCCCKKKK");

        ArrayList<Object> param = new ArrayList<Object>();
        param.add(horseID);
        sendMessage(MsgType.PROCEEDTOSTABLE, param);
    }

    /**
     * Esta função vai reencaminhar os cavalos para o paddock.
     */
    @Override
    public void summonHorsesToPaddock() {
        sendMessage(MsgType.SUMMONHORSESTOPADDOCK, null);    
    }

    /**
     * Método para terminar a ligação e fechar a classe
     */
    public void close() {
        sendMessage(MsgType.CLOSE, null);
    }
}

    
