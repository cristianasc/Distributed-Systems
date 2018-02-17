import java.util.*;
public class StackObj {
	
   private int nElem;
   private Object[] stackArray;
   private int top;
	   
	public StackObj (int nElem) {
		stackArray = new Object[nElem];
        top = -1;
	}
	
	public void push(char elem) {
	      stackArray[++top] = elem;
	   }
	
	public Object pop() {
	      return stackArray[top--];
	   }
	
	public Object peek() {
	      return stackArray[top];
	   }
	
	public boolean isEmpty() {
	      return (top == -1);
	   }
	
	public boolean isFull() {
	      return (top == nElem - 1);
	   }

}
