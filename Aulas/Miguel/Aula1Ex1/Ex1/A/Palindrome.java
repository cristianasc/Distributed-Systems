import java.util.Scanner;

public class Palindrome {
	
	public static void main(String[] args) {

	    Scanner sc = new Scanner(System.in);

	    String palindrome = sc.nextLine();
	    
	    StackChar stack = new StackChar(palindrome.length());
	    
	    FIFOChar fifo = new FIFOChar(palindrome.length());
	    
	    for (int i = 0; i < palindrome.length(); i++) {
			stack.push(palindrome.charAt(i));
			fifo.in(palindrome.charAt(i));
		}
	    
	    stack.print();
	    System.out.print("\n");
	    fifo.print();
	    
	    while (!stack.isEmpty()){
			assert !fifo.isEmpty();
			if (stack.top() == fifo.peek() ){
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
