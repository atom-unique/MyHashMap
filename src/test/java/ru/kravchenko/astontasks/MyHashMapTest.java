package ru.kravchenko.astontasks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class MyHashMapTest {

    @Test
    void sizeTest() {
        MyHashMap<String, Integer> myHashMap = new MyHashMap<>();
        Assertions.assertEquals(0, myHashMap.size());

        for (int i = 0; i < 10; i++) {
            myHashMap.put("key " + i, i);
        }

        Assertions.assertEquals(10, myHashMap.size());
        myHashMap.remove("key 0");
        Assertions.assertEquals(9, myHashMap.size());
    }

    @Test
    void isEmptyTest() {
        MyHashMap<String, Integer> myHashMap = new MyHashMap<>();
        Assertions.assertTrue(myHashMap.isEmpty());
        myHashMap = fillMyHashMap();
        Assertions.assertFalse(myHashMap.isEmpty());
    }

    @Test
    void containsKeyTest() {
        MyHashMap<String, Integer> myHashMap = fillMyHashMap();
        Assertions.assertTrue(myHashMap.containsKey("key 2"));
        Assertions.assertTrue(myHashMap.containsKey("key 9"));
        Assertions.assertFalse(myHashMap.containsKey("key"));
        Assertions.assertFalse(myHashMap.containsKey(null));
    }

    @Test
    void containsValueTest() {
        MyHashMap<String, Integer> myHashMap = fillMyHashMap();
        Assertions.assertTrue(myHashMap.containsValue(0));
        Assertions.assertTrue(myHashMap.containsValue(5));
        Assertions.assertFalse(myHashMap.containsValue(10));
    }

    @Test
    void getTest() {
        MyHashMap<String, Integer> myHashMap = fillMyHashMap();
        Assertions.assertEquals(0, myHashMap.get("key 0"));
        Assertions.assertEquals(9, myHashMap.get("key 9"));
        Assertions.assertNull(myHashMap.get("key"));
        myHashMap.put(null, 10);
        Assertions.assertEquals(10, myHashMap.get(null));
    }

    @Test
    void millionNodesGetTest() {
        MyHashMap<String, Integer> myHashMap = fillMillionNodes();
        for (int i = 1; i <= 1000000; i++) {
            Assertions.assertEquals(i, myHashMap.get("key " + i));
        }
    }

    @Test
    void putTest() {
        MyHashMap<String, Integer> myHashMap = new MyHashMap<>();
        Assertions.assertNull(myHashMap.get("key"));
        Assertions.assertEquals(0, myHashMap.size());
        Assertions.assertEquals(125, myHashMap.put("key", 125));
        Assertions.assertEquals(125, myHashMap.get("key"));
        Assertions.assertEquals(1, myHashMap.size());
        Assertions.assertEquals(300, myHashMap.put(null, 300));
        Assertions.assertNull(myHashMap.put(null, null));
        Assertions.assertNull(myHashMap.get(null));
    }

    @Test
    void millionNodesPutTest() {
        MyHashMap<String, Integer> myHashMap = new MyHashMap<>();
        Assertions.assertNull(myHashMap.get("key 1"));
        Assertions.assertNull(myHashMap.get("key 1000000"));
        Assertions.assertEquals(0, myHashMap.size());

        for (int i = 1; i <= 1000000; i++) {
            myHashMap.put("key " + i, i);
        }

        Assertions.assertEquals(1, myHashMap.get("key 1"));
        Assertions.assertEquals(1000000, myHashMap.get("key 1000000"));
        Assertions.assertEquals(1000000, myHashMap.size());
    }

    @Test
    void removeTest() {
        MyHashMap<String, Integer> myHashMap = fillMyHashMap();
        Assertions.assertTrue(myHashMap.containsKey("key 1") & myHashMap.containsValue(1));
        myHashMap.remove("key 1");
        Assertions.assertFalse(myHashMap.containsKey("key 1") & myHashMap.containsValue(1));
        Assertions.assertEquals(9, myHashMap.remove("key 9"));
        Assertions.assertNull(myHashMap.remove("key 10"));
    }

    @Test
    void millionNodesRemoveTest() {
        MyHashMap<String, Integer> myHashMap = fillMillionNodes();
        Assertions.assertEquals(1000000, myHashMap.size());
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

        for (int i = 10; i < 15; i++) {
            Assertions.assertFalse(
                    myHashMap.containsKey("key " + i) & myHashMap.containsValue(i)
            );
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
    void testEqualsTest() {
        MyHashMap<String, Integer> myHashMap = fillMyHashMap();
        MyHashMap<String, Integer> sameLinkMyHashMap = myHashMap;
        MyHashMap<String, Integer> equalMyHashMap = fillMyHashMap();
        MyHashMap<String, Integer> unequalMyHashMap = fillMyHashMap();
        unequalMyHashMap.remove("key 0");

        Assertions.assertTrue(myHashMap.equals(sameLinkMyHashMap));
        Assertions.assertTrue(myHashMap.equals(equalMyHashMap));
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