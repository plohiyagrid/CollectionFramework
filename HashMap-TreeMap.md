# Java HashMap & TreeMap – Internal Working and Comparison

---

## What is HashMap in Java?

- **Part of** `java.util` package.
- Stores **key-value** pairs.
- Allows **one null key** and **multiple null values**.
- **Not thread-safe** — use `ConcurrentHashMap` if thread-safety is needed.

---

## Internal Working of `HashMap` (Java 8+)

### 1. Data Structure Used Internally
- Uses an **array of buckets**.
- Each bucket (bin) is either a **LinkedList** or a **Red-Black Tree**.
- Each bucket entry is a `Node<K, V>` storing:
  - Key, Value, Hash, Next pointer

```java
transient Node<K,V>[] table;
```

---

### 2. Insertion Process (`put` method)
1. **Compute Hash** → `hash = hash(key.hashCode())`
2. **Find Bucket Index** → `index = (n - 1) & hash`
3. **Check for Key / Handle Collisions**
   - Empty bucket → insert
   - Existing key → update value
   - Collision → add to LinkedList or treeify if threshold exceeded
4. **Rehashing** if load factor (default `0.75`) exceeded → resize & rehash

---

### 3. Collision Handling
- Occurs when different keys map to same index
- Handled by:
  - Linked List chaining (default)
  - Tree conversion if bucket has > 8 nodes AND array size ≥ 64

---

### 4. Retrieval Process (`get` method)
1. Compute hash
2. Locate bucket
3. Traverse list/tree
4. Compare with `equals()`
5. Return value

---

### 5. Resizing / Rehashing
- Triggered when `size > capacity × loadFactor`
- Creates larger array and rehashes entries

---

### 6. Treeification
- If bucket > 8 nodes and array size ≥ 64 → convert list to Red-Black Tree
- Improves lookup from O(n) → O(log n)

---

### Performance Summary

| Operation | Best Case | Worst (Pre-Java 8) | Worst (Java 8+) |
|-----------|-----------|--------------------|------------------|
| put/get   | O(1)      | O(n)               | O(log n)         |
| remove    | O(1)      | O(n)               | O(log n)         |

---

### Other Key Points
- HashMap is **not synchronized**
- Allows one null key
- Good `hashCode()` & `equals()` implementations help reduce collisions

```java
Map<String, String> map = new HashMap<>();
map.put("id", "123");
map.put("name", "Alice");
System.out.println(map.get("name")); // Alice
```

> **Note:** If two objects are equal, they must have the same `hashCode()` — but same `hashCode()` doesn’t guarantee equality.

---

## TreeMap vs HashMap

### Definition & Purpose

| Feature   | HashMap           | TreeMap                   |
|-----------|-------------------|----------------------------|
| Package   | `java.util.HashMap` | `java.util.TreeMap`         |
| Interface | Implements `Map`  | Implements `NavigableMap`  |

---

### Ordering

| Feature | HashMap             | TreeMap                        |
|--------|----------------------|--------------------------------|
| Order  | No guaranteed order  | Sorted order (natural or custom) |

---

### Performance

| Operation       | HashMap (Avg / Worst) | TreeMap     |
|------------------|-------------------------|-------------|
| put() / get()    | O(1) / O(n)             | O(log n)     |
| Sorted access    | Not supported           | Supported    |

---

### Null Handling

| Feature    | HashMap       | TreeMap (NPE on null key) |
|------------|----------------|-----------------------------|
| Null Key   | Allowed (one) | Not allowed                |
| Null Values| Allowed       | Allowed                    |

---

### Use-Case Recommendations

| When to Use                      | Map Type  |
|----------------------------------|-----------|
| Fast, unordered access           | HashMap   |
| Sorted key access or range queries | TreeMap   |

---

### Internal Implementation

| Feature           | HashMap                      | TreeMap             |
|-------------------|-------------------------------|----------------------|
| Data Structure    | Array + LinkedList / Tree    | Red-Black Tree       |
| Key Comparison    | `hashCode()` + `equals()`    | `compareTo()` / `Comparator` |

---

### Thread Safety

| Feature           | HashMap         | TreeMap                 |
|-------------------|------------------|--------------------------|
| Thread-safe?       | No               | No                       |
| Concurrent variant | `ConcurrentHashMap` | `ConcurrentSkipListMap` |

---

## Code Examples

```java
Map<String, Integer> hashMap = new HashMap<>();
hashMap.put("Banana", 2);
hashMap.put("Apple", 1);
hashMap.put("Cherry", 3);
System.out.println(hashMap); // Unordered

Map<String, Integer> treeMap = new TreeMap<>();
treeMap.put("Banana", 2);
treeMap.put("Apple", 1);
treeMap.put("Cherry", 3);
System.out.println(treeMap); // Sorted: Apple, Banana, Cherry
```

---

### Summary Table

| Feature             | HashMap         | TreeMap         |
|---------------------|------------------|------------------|
| Order               | Unordered        | Sorted           |
| Performance         | Faster (O(1))    | Slower (O(log n))|
| Null Key Support    | One null allowed | No null support  |
| Thread Safety       | Not safe         | Not safe         |
| Underlying Structure| Array + Tree     | Red-Black Tree   |
| Use Case            | Fast lookups     | Ordered needs    |

---

## When to Use Which?
- Use **HashMap** for fast access without ordering.
- Use **TreeMap** when sorted data or range queries matter.
