
public class FIFOGeneric<T> {

  private T[] array;
  private int size;
  private int head;  // proximo a sair (first)
  private int tail;  // proximo entrar (last)
  
   @SuppressWarnings(value = "unchecked")
  public FIFOGeneric(int capacity) {
    array = (T[]) new Object[capacity];
    size = 0;
    head = 0;
    tail = 0;
  }

  public void in(T v) {
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
    array[head] = null;  // para primitivos, não seria necessário...
    head = next(head);
    size--;
    assert !isFull();
    assert invariant();
  }

  // devolve o primeiro elemento (o mais antigo, próximo a sair)
  public T peek() {
    assert !isEmpty();
    return array[head];
  }

  public int size() { return size; }

  public boolean isEmpty() { return size==0; }
  
  public boolean isFull() { return size==array.length; }

  public void clear() {
    //while (!isEmpty()) out();  // para primitivos, naõ necessário-
    size = 0;
    assert isEmpty();
  }
  
  public void print(){
		for (int i = 0; i <size() ; i++){
			System.out.print(" " + array[i]);		
		}	
	  }  

  // Teste da classe...
  public static void main(String[] args) {
	  FIFOGeneric<String> s = new FIFOGeneric<String>(args.length);
    
    assert s.isEmpty();
    
    String v;

    for(int i = 0; i < args.length; i++) {
      v = args[i];
      s.in(v);
    }
    assert s.isFull();

    while ( !s.isEmpty() ) {
      v = s.peek();
      s.out();
      System.out.println(v);
    }

    assert s.isEmpty();
  }
}
