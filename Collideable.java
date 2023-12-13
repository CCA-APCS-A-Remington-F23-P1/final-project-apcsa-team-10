public interface Collideable<T>{
    boolean didCollideTop(T obj);
    boolean didCollideBottom(T obj);
    boolean didCollideLeft(T obj);
    boolean didCollideRight(T obj);
}