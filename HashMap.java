import java.util.Iterator;
import java.util.Optional;

import javax.swing.text.html.parser.Entity;


public class HashMap<K, V> implements Iterable<Entity>{
    
    private static final int INIT_BUCKET_COUNT = 16;

    private Bucket[] buckets;
    class Entity{
        K key;
        V value;
        @Override
        public String toString(){
            return("Ключ "+key.toString()+" Значение: "+ value.toString());
        }
    }  
    
    public class NodeItr implements Iterator<Entity>{
            private int currentBucket = -1;
            private Node currentNode;

            public boolean hasNext() {
                if (currentNode != null){
                    return true;
                }
                else{
                    for (int i = currentBucket+1; i<buckets.length;i++){
                    if (buckets[i] != null){
                        //currentBucket = i;
                        //currentNode = buckets[currentBucket].head;
                        return true;
                    }}
                    return false;
                    
                }
            }

        public Entity next() {
            if (currentNode != null){
                Entity buf = currentNode.value;
                currentNode = currentNode.next;
                return buf;
            }
            else{
                for (int i = currentBucket+1; i<buckets.length;i++){
                if (buckets[i] != null){
                    currentBucket = i ;
                    Node buf = buckets[currentBucket].head;
                    currentNode = buf.next;
                    return buf.value;
                }}
                return null;
                
            }
        }
    }

    public class Node{
            public Node next;
            public Entity value;
        }

    public class Bucket<K,V>{
        public Node head;


        public V add(Entity entity){
            Node node = new Node();
            node.value = entity;

            if(head == null){
                head = node;
                return null;
            }

            Node current = head;
            while(true){
                if (current.value.key.equals(entity.key)){
                    V buf = (V)current.value.value;
                    current.value.value = entity.value;
                    return buf;
                }
                if (current.next != null){
                    current = current.next;
                }
                else{
                    current.next = node;
                    return null;
                }
            }
        }

        public V get(K key){
            Node node = head;
            while (node != null){
                if (node.value.key.equals(key)){
                    return (V)node.value.value;
                }
            }
            return null;
        }

        public V remove(K key){
            if (head == null)
                return null;
            if (head.value.key.equals(key)){
                V buf = (V)head.value.value;
                head = head.next;
                return buf;
            }
            else{
                Node node = head;
                while(node.next != null){
                    if (node.next.value.key.equals(key)){
                        V buf = (V)node.next.value.value;
                        node.next = node.next.next;
                        return buf;
                    }
                    node = node.next;
                }
                return null;
            }
        }

    }


    private int calculateBucketIndex(K key){
        int index = Math.abs(key.hashCode() % buckets.length);
        return index;
    }

    public V put(K key, V value){
        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null){
            bucket = new Bucket(); 
            buckets[index] = bucket; 
        }

        Entity entity = new Entity();
        entity.key = key;
        entity.value = value;

        return (V)bucket.add(entity);
    }

    public V get(K key){
        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null){
            return null;
        }
        return (V)bucket.get(key);
    }

    public V remove(K key){
        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null){
            return null;
        }
        return (V)bucket.remove(key);
    }

    public HashMap(){
        this(INIT_BUCKET_COUNT);
    }

    public HashMap(int initCount){
        buckets = new Bucket[initCount];
    }

    @Override
    public Iterator iterator() {
        return new NodeItr();
    }

    
}

