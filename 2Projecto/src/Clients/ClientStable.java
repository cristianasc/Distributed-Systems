package Clients;

import Stable.*;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * @author cristianacarvalho
 *
 */
public class ClientStable extends ClientSend implements IStable {

    public ClientStable(InetAddress address, int port) {
        super(address, port);
        System.out.printf("\nSTABLE CLIENT\n");
    }
    
    @Override
    public void proceedToStable(int horseID) {
        System.out.println("Proceed to stable - client Stable");
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(horseID);
        sendMessage(MsgType.PROCEEDTOSTABLE, param);
    }

    @Override
    public void summonHorsesToPaddock() {
        sendMessage(MsgType.SUMMONHORSESTOPADDOCK, null);    
    }

    public void close() {
        sendMessage(MsgType.CLOSE, null);
    }
}

    
