package ru.kravchenko.astontasks;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The class describes my own implementation of HashMap.
 *
 * @param <K> key type.
 * @param <V> value type.
 * @author Kravchenko Yury
 * @version 2.0.1
 */
public class MyHashMap<K, V> extends AbstractMap<K, V> implements Map<K, V> {

    /**
     * Load factor by default.
     */
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * Initial length of the array by default.
     */
    private static final int DEFAULT_LENGTH = 16;

    /**
     * Array expansion coefficient.
     */
    public static final int EXPANSION_COEFFICIENT = 2;

    /**
     * Array length.
     */
    private int arrayLength;

    /**
     * Quantity of stored items.
     */
    private int size;

    /**
     * Load factor.
     */
    private final float loadFactor;

    /**
     * Array of nodes.
     */
    private Node<K, V>[] nodesArray;

    /**
     * Constructor with parameters by default.
     */
    public MyHashMap() {
        this(DEFAULT_LENGTH, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Constructor receives custom length of the array.
     *
     * @param arrayLength length of the array.
     */
    public MyHashMap(int arrayLength) {
        this(arrayLength, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Constructor receives custom load factor of the map.
     *
     * @param loadFactor load factor of the map.
     */
    public MyHashMap(float loadFactor) {
        this(DEFAULT_LENGTH, loadFactor);
    }

    /**
     * Constructor receives custom length of the array and load factor of the map.
     *
     * @param arrayLength length of the array.
     * @param loadFactor  load factor of the map.
     */
    public MyHashMap(int arrayLength, float loadFactor) {
        if (arrayLength <= 0 || loadFactor <= 0) {
            throw new IllegalArgumentException("The parameters must be positive.");
        }
        this.arrayLength = arrayLength;
        this.loadFactor = loadFactor;
        this.nodesArray = new Node[arrayLength];
    }

    /**
     * Method returns quantity of stored items.
     *
     * @return quantity of stored items.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Method checks if the map is empty.
     *
     * @return is map empty.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Method checks if the key is presented in the map.
     *
     * @param key key whose presence in this map is to be checked.
     * @return is key presented.
     */
    @Override
    public boolean containsKey(Object key) {
        return keySet().stream().anyMatch(presentKey -> presentKey.equals(key));
    }

    /**
     * Method checks if the value is presented in the map.
     *
     * @param value value whose presence in this map is to be checked.
     * @return is value presented.
     */
    @Override
    public boolean containsValue(Object value) {
        return values().stream().anyMatch(presentValue -> presentValue.equals(value));
    }

    /**
     * Method returns value, associated with key from the map.
     *
     * @param key the key whose associated value is to be returned.
     * @return value, associated with the key.
     */
    @Override
    public V get(Object key) {
        int hash = hashCode(key);
        int index = getIndex(hash, arrayLength);
        Node<K, V> node = nodesArray[index];
        if (node == null) {
            return null;
        }
        K nodeKey = node.key;
        if (key == null && nodeKey == null) {
            return node.value;
        }
        while (node.next != null) {
            if (hash == node.hashCode) {
                if (key != null && key.equals(node.getKey())) {
                    return node.value;
                }
            } else {
                node = node.next;
            }
        }
        if (key != null && key.equals(node.getKey())) {
            return node.value;
        }
        return null;
    }

    /**
     * Method adds key and value, associated with the key, to the map.
     *
     * @param key   key with which the specified value is to be associated.
     * @param value value to be associated with the specified key.
     * @return added value, associated with the key.
     */
    @Override
    public V put(K key, V value) {
        int hash = hashCode(key);
        int index = getIndex(hash, arrayLength);
        Node<K, V> node = nodesArray[index];
        if (node == null) {
            node = new Node<>(key, value, hash, null);
            nodesArray[index] = node;
            size++;
            increaseSize();
        } else if (key != null && node.key != null) {
            while (node.next != null) {
                if (key.equals(node.getKey())) {
                    node.setValue(value);
                }
                node = node.next;
            }
            if (key.equals(node.getKey())) {
                node.setValue(value);
            } else {
                node.next = new Node<>(key, value, hash, null);
                size++;
                increaseSize();
            }
        } else {
            nodesArray[index] = new Node<>(key, value, hash, null);
        }
        return value;
    }

    /**
     * Method removes key and associated value from the map.
     *
     * @param key key whose mapping is to be removed from the map.
     * @return removed value.
     */
    @Override
    public V remove(Object key) {
        int hash = hashCode(key);
        int index = getIndex(hash, arrayLength);
        if (nodesArray[index] != null) {
            Node<K, V> node = nodesArray[index];
            Node<K, V> prevNode = null;
            if (node.key == null) {
                node = nodesArray[index];
                nodesArray[index] = null;
                size--;
                return node.value;
            }
            while (node != null) {
                if (hash == node.hashCode && node.key.equals(key)) {
                    if (prevNode == null) {
                        node = nodesArray[index];
                        nodesArray[index] = nodesArray[index].next;
                        size--;
                        return node.value;
                    } else {
                        prevNode.next = node.next;
                        size--;
                        return node.value;
                    }
                }
                prevNode = node;
                node = node.next;
            }
        }
        return null;
    }

    /**
     * Method adds all elements from obtained map to this map.
     *
     * @param map mappings to be stored in this map.
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Method clears the map.
     */
    @Override
    public void clear() {
        nodesArray = new Node[DEFAULT_LENGTH];
        size = 0;
    }

    /**
     * Method returns set of contained keys.
     *
     * @return set of contained keys.
     */
    @Override
    public Set<K> keySet() {
        return entrySet().stream().map(Entry::getKey).collect(Collectors.toSet());
    }

    /**
     * Method returns set of contained values.
     *
     * @return set of contained values.
     */
    @Override
    public Collection<V> values() {
        return entrySet().stream().map(Entry::getValue).collect(Collectors.toSet());
    }

    /**
     * Method returns set of contained entries.
     *
     * @return set of contained entries.
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new HashSet<>();
        for (Node<K, V> node : nodesArray) {
            while (node != null) {
                set.add(node);
                node = node.next;
            }
        }
        return set;
    }

    /**
     * Method compares obtained object to this map.
     *
     * @param object object to be compared for equality to this map.
     * @return is obtained object equal to this map.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof MyHashMap<?, ?> myHashMap)) return false;
        if (!super.equals(object)) return false;
        return arrayLength == myHashMap.arrayLength
                && size == myHashMap.size
                && Float.compare(loadFactor, myHashMap.loadFactor) == 0
                && Arrays.equals(nodesArray, myHashMap.nodesArray);
    }

    public Node<K, V>[] getNodesArray() {
        return nodesArray;
    }

    /**
     * Method returns the hash code for obtained key.
     *
     * @param key obtained key for which hash code should be produced.
     * @return hash code for the key.
     */
    private int hashCode(Object key) {
        return key == null ? 0 : key.hashCode();
    }

    /**
     * Method returns the element index by hash code and length of the array.
     *
     * @param hash   hash code for the element key.
     * @param length length of the array.
     * @return the element index.
     */
    private int getIndex(int hash, int length) {
        return Math.abs(hash) % length;
    }

    /**
     * Method checks if array must be extended and if so (load factor is exceeded), extends the array.
     */
    private void increaseSize() {
        if (arrayLength * loadFactor < size) {
            int newArrayLength = arrayLength * EXPANSION_COEFFICIENT;
            Node<K, V>[] newNodesArray = new Node[newArrayLength];
            Set<Entry<K, V>> entrySet = entrySet();
            for (Entry<K, V> entry : entrySet) {
                Node<K, V> nodeFromEntry = (Node<K, V>) entry;
                nodeFromEntry.next = null;
                int newIndex = getIndex(nodeFromEntry.hashCode, newArrayLength);
                Node<K, V> node = newNodesArray[newIndex];
                if (node == null) {
                    newNodesArray[newIndex] = nodeFromEntry;
                } else {
                    while (node.next != null) {
                        node = node.next;
                    }
                    node.next = nodeFromEntry;
                }
            }
            nodesArray = newNodesArray;
            arrayLength = newArrayLength;
        }
    }

    /**
     * The class describes node object that stores the key and value in an array.
     *
     * @param <K> key type.
     * @param <V> value type.
     * @author Kravchenko Yury
     * @version 1.0.7
     */
    private class Node<K, V> implements Map.Entry<K, V> {

        /**
         * The field that stores the key.
         */
        private final K key;

        /**
         * The field that stores the value.
         */
        private V value;

        /**
         * Link to the next node.
         */
        private Node<K, V> next;

        /**
         * Hash code for the key.
         */
        private final int hashCode;

        /**
         * Constructor of the node object.
         *
         * @param key      key with which the value is associated.
         * @param value    value associated with the specified key.
         * @param hashCode hash code for the key.
         * @param next     link to the next node.
         */
        private Node(K key, V value, int hashCode, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.hashCode = hashCode;
            this.next = next;
        }

        /**
         * Method returns stored key.
         *
         * @return stored key.
         */
        @Override
        public K getKey() {
            return key;
        }

        /**
         * Method returns stored value.
         *
         * @return stored value.
         */
        @Override
        public V getValue() {
            return value;
        }

        /**
         * Method sets new value to the node.
         *
         * @param value new value to be stored in this node.
         * @return new stored value.
         */
        @Override
        public V setValue(V value) {
            this.value = value;
            return value;
        }

        /**
         * Method compares for equality obtained object with the map node.
         *
         * @param object object to be compared for equality with this map node.
         * @return are the node and object equal.
         */
        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof Map.Entry<?, ?>)) return false;
            Node<?, ?> node = (Node<?, ?>) object;
            return hashCode == node.hashCode
                    && Objects.equals(key, node.key)
                    && Objects.equals(value, node.value)
                    && Objects.equals(next, node.next);
        }

        /**
         * Method returns hash code calculated for the node.
         *
         * @return hash code for the node.
         */
        @Override
        public int hashCode() {
            return Objects.hash(key, value, next, hashCode);
        }

        /**
         * Method returns the string representation of the node.
         *
         * @return string representation of the node.
         */
        @Override
        public String toString() {
            return key + " = " + value;
        }
    }
}