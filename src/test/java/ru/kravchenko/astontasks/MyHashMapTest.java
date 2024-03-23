package ru.kravchenko.astontasks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class MyHashMapTest {

    @Test
    void sizeTestEmptyMap() {
        MyHashMap<String, Integer> myHashMap = new MyHashMap<>();
        Assertions.assertEquals(0, myHashMap.size());
    }

    @Test
    void sizeTestFilledMap() {
        MyHashMap<String, Integer> myHashMap = fillMyHashMap();
        Assertions.assertEquals(10, myHashMap.size());
    }

    @Test
    void isEmptyTestEmptyMap() {
        MyHashMap<String, Integer> myHashMap = new MyHashMap<>();
        Assertions.assertTrue(myHashMap.isEmpty());
    }

    @Test
    void isEmptyTestFilledMap() {
        MyHashMap<String, Integer> myHashMap = fillMyHashMap();
        Assertions.assertFalse(myHashMap.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"key 2", "key 9"})
    void containsKeyTestExistingKey(String key) {
        MyHashMap<String, Integer> myHashMap = fillMyHashMap();
        Assertions.assertTrue(myHashMap.containsKey(key));
    }

    @Test
    void containsKeyTestAbsentKey() {
        MyHashMap<String, Integer> myHashMap = fillMyHashMap();
        Assertions.assertFalse(myHashMap.containsKey("key"));
    }

    @Test
    void containsKeyTestNullKey() {
        MyHashMap<String, Integer> myHashMap = fillMyHashMap();
        Assertions.assertFalse(myHashMap.containsKey(null));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 5})
    void containsValueTestExistingValue(Integer value) {
        MyHashMap<String, Integer> myHashMap = fillMyHashMap();
        Assertions.assertTrue(myHashMap.containsValue(value));
        Assertions.assertTrue(myHashMap.containsValue(value));
    }

    @Test
    void containsValueTestAbsentValue() {
        MyHashMap<String, Integer> myHashMap = fillMyHashMap();
        Assertions.assertFalse(myHashMap.containsValue(10));
    }

    @Test
    void getTestExistingKey() {
        MyHashMap<String, Integer> myHashMap = fillMyHashMap();
        Assertions.assertEquals(0, myHashMap.get("key 0"));
        Assertions.assertEquals(9, myHashMap.get("key 9"));
    }

    @Test
    void getTestAbsentKey() {
        MyHashMap<String, Integer> myHashMap = fillMyHashMap();
        Assertions.assertNull(myHashMap.get("key"));
    }

    @Test
    void getTestNullKey() {
        MyHashMap<String, Integer> myHashMap = fillMyHashMap();
        myHashMap.put(null, 10);
        Assertions.assertEquals(10, myHashMap.get(null));
    }

    @Test
    void getTestMillionNodes() {
        MyHashMap<String, Integer> myHashMap = fillMillionNodes();
        for (int i = 1; i <= 1000000; i++) {
            Assertions.assertEquals(i, myHashMap.get("key " + i));
        }
    }

    @Test
    void putTest() {
        MyHashMap<String, Integer> myHashMap = new MyHashMap<>();
        myHashMap.put("key", 125);
        Assertions.assertEquals(1, myHashMap.size());
        Assertions.assertEquals(125, myHashMap.get("key"));
    }

    @Test
    void putTestNullArgs(){
        MyHashMap<String, Integer> myHashMap = new MyHashMap<>();
        Assertions.assertEquals(300, myHashMap.put(null, 300));
        Assertions.assertNull(myHashMap.put(null, null));
        Assertions.assertNull(myHashMap.get(null));
    }

    @Test
    void putTestValueReturn(){
        MyHashMap<String, Integer> myHashMap = new MyHashMap<>();
        Assertions.assertEquals(100, myHashMap.put("key", 100));
        Assertions.assertNull(myHashMap.put("key", null));
    }

    @Test
    void putTestMillionNodes() {
        MyHashMap<String, Integer> myHashMap;
        myHashMap = fillMillionNodes();
        Assertions.assertEquals(1000000, myHashMap.size());
    }

    @Test
    void removeTest() {
        MyHashMap<String, Integer> myHashMap = fillMyHashMap();
        Assertions.assertTrue(myHashMap.containsKey("key 1") & myHashMap.containsValue(1));
        myHashMap.remove("key 1");
        Assertions.assertFalse(myHashMap.containsKey("key 1") & myHashMap.containsValue(1));
    }

    @Test
    void removeTestValueReturn() {
        MyHashMap<String, Integer> myHashMap = fillMyHashMap();
        Assertions.assertEquals(9, myHashMap.remove("key 9"));
        Assertions.assertNull(myHashMap.remove("key 10"));
    }

    @Test
    void removeTestMillionNodes() {
        MyHashMap<String, Integer> myHashMap = fillMillionNodes();
        for (int i = 1; i <= 500000; i++) {
            myHashMap.remove("key " + i);
        }
        Assertions.assertEquals(500000, myHashMap.size());
    }

    @Test
    void putAllTest() {
        MyHashMap<String, Integer> myHashMap = fillMyHashMap();

        Map<String, Integer> additionalMap = new HashMap<>();
        for (int i = 10; i < 15; i++) {
            additionalMap.put("key " + i, i);
        }

        Assertions.assertEquals(10, myHashMap.size());
        myHashMap.putAll(additionalMap);

        for (int i = 10; i < 15; i++) {
            Assertions.assertTrue(
                    myHashMap.containsKey("key " + i) & myHashMap.containsValue(i)
            );
        }
        Assertions.assertEquals(15, myHashMap.size());
    }

    @Test
    void clearTest() {
        MyHashMap<String, Integer> myHashMap = fillMyHashMap();
        Assertions.assertFalse(myHashMap.isEmpty());
        Assertions.assertEquals(10, myHashMap.size());
        myHashMap.clear();
        Assertions.assertTrue(myHashMap.isEmpty());
        Assertions.assertEquals(0, myHashMap.size());
    }

    @Test
    void keySetTest() {
        MyHashMap<String, Integer> myHashMap = fillMyHashMap();
        Set<?> keySet = myHashMap.keySet();
        Assertions.assertTrue(keySet.size() == myHashMap.size());
        for (int i = 0; i < 10; i++) {
            Assertions.assertTrue(keySet.contains("key " + i));
        }
        Assertions.assertFalse(keySet.contains("key 10"));
    }

    @Test
    void valuesTest() {
        MyHashMap<String, Integer> myHashMap = fillMyHashMap();
        Collection<?> values = myHashMap.values();
        Assertions.assertTrue(values.size() == myHashMap.size());
        for (int i = 0; i < 10; i++) {
            Assertions.assertTrue(values.contains(i));
        }
        Assertions.assertFalse(values.contains(10));
    }

    @Test
    void entrySetTest() {
        MyHashMap<String, Integer> myHashMap = fillMyHashMap();
        Set<?> entrySet = myHashMap.entrySet();
        Assertions.assertEquals(entrySet.size(), myHashMap.size());
    }

    @Test
    void equalsTestSameMap() {
        MyHashMap<String, Integer> myHashMap = fillMyHashMap();
        MyHashMap<String, Integer> sameLinkMyHashMap = myHashMap;
        Assertions.assertTrue(myHashMap.equals(sameLinkMyHashMap));
    }

    @Test
    void equalsTestEqualMap() {
        MyHashMap<String, Integer> myHashMap = fillMyHashMap();
        MyHashMap<String, Integer> equalMyHashMap = fillMyHashMap();
        Assertions.assertTrue(myHashMap.equals(equalMyHashMap));
    }

    @Test
    void equalsTestUnequalMap() {
        MyHashMap<String, Integer> myHashMap = fillMyHashMap();
        MyHashMap<String, Integer> unequalMyHashMap = fillMyHashMap();
        unequalMyHashMap.remove("key 0");
        Assertions.assertFalse(myHashMap.equals(unequalMyHashMap));
    }

    private static MyHashMap<String, Integer> fillMyHashMap() {
        MyHashMap<String, Integer> myHashMap = new MyHashMap<>();
        for (int i = 0; i < 10; i++) {
            myHashMap.put("key " + i, i);
        }
        return myHashMap;
    }

    private static MyHashMap<String, Integer> fillMillionNodes() {
        MyHashMap<String, Integer> myHashMap = new MyHashMap<>();
        for (int i = 1; i <= 1000000; i++) {
            myHashMap.put("key " + i, i);
        }
        return myHashMap;
    }
}