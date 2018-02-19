import java.util.Scanner;

public class PalindromeObj {
	
	public static void main(String[] args) {

	    Scanner sc = new Scanner(System.in);

	    String palindrome = sc.nextLine();
	    
	    StackCharObj stack = new StackCharObj(palindrome.length());
	    
	    FIFOCharObj fifo = new FIFOCharObj(palindrome.length());
	    
	    for (int i = 0; i < palindrome.length(); i++) {
			stack.push(palindrome.charAt(i));
			fifo.in(palindrome.charAt(i));
		}
	    
	    stack.print();
	    System.out.print("\n");
	    fifo.print();
	    
	    while (!stack.isEmpty()){
			assert !fifo.isEmpty();
			if ((char) stack.top() == (char) fifo.peek() ){
				fifo.out();
				stack.pop();	
			}
			else{
				System.out.println("\nNão é... ");	
				System.exit(0);
			}			
		}
	    
	    System.out.println("\n Sim ");
	}
}
