
public class StackChar {

	  private char[] array;
	  private int size;
	  
	  @SuppressWarnings(value = "unchecked")	
	  public StackChar(int capacity) {
	    array = new char[capacity];
	    size = 0;
	  }

	  public void push(char v) {
	    assert !isFull() : "stack overflow";
	    array[size] = v;
	    size++;
	    assert !isEmpty() && top() == v;
	  }

	  public void pop() {
	    assert !isEmpty();
	    size--;
	    //array[size] = 0;  // para primitivos, não seria necessário...
	    assert !isFull();
	  }

	  public int size() { return size; }

	  public boolean isEmpty() { return size==0; }
	  
	  public boolean isFull() { return size==array.length; }

	  public char top() {
	    assert !isEmpty();
	    return array[size-1];
	  }

	  public void clear() {
	    //while (!isEmpty()) pop();  // para primitivos, naõ necessário-
	    size = 0;
	    assert isEmpty();
	  }
	  
	public void print(){
		for (int i = 0; i <size() ; i++){
			System.out.print(" " + array[i]);		
		}	
	}
}