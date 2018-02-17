import java.util.*;

public class StackChar {
	   private int nElem;
	   private char[] stackArray;
	   private int top;
	   
	
	public StackChar (int nElem) {
		stackArray = new char[nElem];
        top = -1;
	}
	
	public void push(char elem) {
	      stackArray[++top] = elem;
	   }
	
	public char pop() {
	      return stackArray[top--];
	   }
	
	public char peek() {
	      return stackArray[top];
	   }
	
	public boolean isEmpty() {
	      return (top == -1);
	   }
	
	public boolean isFull() {
	      return (top == nElem - 1);
	   }

}
