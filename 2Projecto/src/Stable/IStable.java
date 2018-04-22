package Stable;

/**
 *  Interface para estábulo, Stable. Tem 2 instâncias possiveis, estábulo local (StableLocal) 
 * ou cliente (RemoteStable) para servidor remoto(StableServer).
 * 
 * @author Tiago Marques
 */
public interface IStable {
    
    /**
     * Módulo para reencaminhar os cavalos para o estábulo. Recebe como
     * parâmetro um cavalo horseID. Um cavalo vai estar em wait enquanto o
     * manager não chamar todos os cavalos. Estes são acordados quando o manager
     * os chamar.
     *
     * @param horseID ID do cavalo
     */
     public void proceedToStable(int horseID);
     
     /**
     * Método para reencaminhar os cavalos para o paddock. Os cavalos são
     * acordados pelo manager e reencaminhados. Enquanto não estiverem os
     * cavalos todos o manager fica em wait.
     */
     public void callToPaddock();
     
     /**
     * Método para "fechar" a classe - Apenas toma valor na classe remota.
     */
     public void close();
}
