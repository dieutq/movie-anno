package movieannot.BasicModel;

public class Pair<K, V, E> {

    private K element0;
    private V element1;
    private E element2;

    public static <K, V, E> Pair<K, V, E> createPair(K element0, V element1, E element2) {
        return new Pair<K, V, E>(element0, element1, element2);
    }

    public Pair(K element0, V element1, E element2) {
        this.element0 = element0;
        this.element1 = element1;
        this.element2 = element2;
    }

    public K getElement0() {
        return element0;
    }

    public V getElement1() {
        return element1;
    }
    
    public E getElement2() {
        return element2;
    }

    public void setElement0(K element0) {
        if (element0 != null) {
            this.element0 = element0;
        }
        if (element0 == null) {
            this.element0 = null;
        }
    }

    public void setElement1(V element1) {
        if (element1 != null) {
            this.element1 = element1;
        }
        if (element1 == null) {
            this.element1 = null;
        }
    }
    
    public void setElement2(E element2) {
        if (element2 != null) {
            this.element2 = element2;
        }
        if (element2 == null) {
            this.element2 = null;
        }
    }

}
