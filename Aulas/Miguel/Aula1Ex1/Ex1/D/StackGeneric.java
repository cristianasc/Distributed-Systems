public class StackGeneric<T> {

  private T[] array;
  private int size;
  
  @SuppressWarnings(value = "unchecked")	
  public StackGeneric(int capacity) {
    array = (T[]) new Object[capacity];
    size = 0;
  }

  public void push(T v) {
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

  public T top() {
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

  // Teste da classe...
  public static void main(String[] args) {
	StackGeneric<String> s = new StackGeneric<String>(args.length);
    assert s.isEmpty();
    
    String v;

    for(int i = 0; i < args.length; i++) {
      v = args[i];
      s.push(v);
    }
    assert s.isFull();

    while ( !s.isEmpty() ) {
      v = s.top();
      s.pop();
      System.out.println(v);
    }

    assert s.isEmpty();
  }
}


