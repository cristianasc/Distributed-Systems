import java.util.*;

public class Palindroma {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		String poli = sc.nextLine();
		String reversePoli = "";
		
		/* StackChar 
		StackChar stack = new StackChar(poli.length());
		for (int i=0; i<poli.length(); i++) {
			stack.push(poli.charAt(i));
		}
		
		while (!stack.isEmpty()) { 
            reversePoli += stack.pop();
        }
		
		if (poli.equals(reversePoli))
            System.out.println("Palindrome.");
        else
            System.out.println("Isn't a palindrome.");
        */
		
		/* StackObj
		StackObj stack = new StackObj(poli.length());
		for (int i=0; i<poli.length(); i++) {
			stack.push(poli.charAt(i));
		}
		
		while (!stack.isEmpty()) { 
            reversePoli += stack.pop();
        }
		
		if (poli.equals(reversePoli))
            System.out.println("Palindrome.");
        else
            System.out.println("Isn't a palindrome.");
        */
		
		/* FIFOChar
		FIFOChar fifo = new FIFOChar(poli.length());
		for (int i=poli.length()-1; i>=0; i--) {
			fifo.in(poli.charAt(i));
		}
		
		while (!fifo.isEmpty()) { 
            reversePoli += fifo.peek();
            fifo.out();
        }
		
		if (poli.equals(reversePoli))
            System.out.println("Palindrome.");
        else
            System.out.println("Isn't a palindrome.");
        */
		
		/* FIFOObj */
		FIFOObj fifo = new FIFOObj(poli.length());
		for (int i=poli.length()-1; i>=0; i--) {
			fifo.in(poli.charAt(i));
		}
		
		while (!fifo.isEmpty()) { 
            reversePoli += fifo.peek();
            fifo.out();
        }
		
		if (poli.equals(reversePoli))
            System.out.println("Palindrome.");
        else
            System.out.println("Isn't a palindrome.");
        
	}

}
