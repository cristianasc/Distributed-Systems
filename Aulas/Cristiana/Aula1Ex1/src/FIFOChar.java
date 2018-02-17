
public class FIFOChar {

	  private char[] array;
	  private int size;
	  private int head;  // proximo a sair (first)
	  private int tail;  // proximo entrar (last)
	  
	   @SuppressWarnings(value = "unchecked")
	  public FIFOChar(int capacity) {
	    array = new char[capacity];
	    size = 0;
	    head = 0;
	    tail = 0;
	  }

	  public void in(char v) {
	    assert !isFull() : "stack overflow";
	    array[tail] = v;
	    tail = next(tail);
	    size++;
	    assert !isEmpty();
	    assert invariant();
	  }

	  private int next(int ind) {
	    return (ind+1) % array.length;
	  }

	  public boolean invariant() {
	    return tail == (head+size) % array.length;
	  }

	  public void out() {
	    assert !isEmpty();
	    //array[head] = null;  // para primitivos, n„o seria necess·rio...
	    head = next(head);
	    size--;
	    assert !isFull();
	    assert invariant();
	  }

	  // devolve o primeiro elemento (o mais antigo, prÛximo a sair)
	  public char peek() {
	    assert !isEmpty();
	    return array[head];
	  }

	  public int size() { return size; }

	  public boolean isEmpty() { return size==0; }
	  
	  public boolean isFull() { return size==array.length; }

	  public void clear() {
	    //while (!isEmpty()) out();  // para primitivos, naı necess·rio-
	    size = 0;
	    assert isEmpty();
	  }
	  
	  public void print(){
			for (int i = 0; i <size() ; i++){
				System.out.print(" " + array[i]);		
			}	
		}
}