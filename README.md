# BoundedStack

## Concept

`BoundedStack` คือ ADT ที่เก็บ list ของข้อความ (String) ที่ผู้ใช้กรอกเข้ามา เหมือน stack ที่มี limit ว่าใส่ได้ไม่เกิน `capacity` ตัว (กำหนดตอนสร้าง) จะ push เข้าไปต่อท้าย แล้ว pop ออกจากท้ายก็ได้ (LIFO)

ตัวอย่างการใช้:
```java
BoundedStack b = new BoundedStack(2);
b.push("Bohemian Rhapsody");
b.push("Imagine");
b.size(); // 2
b.pop();  // "Imagine"
```

---

## AF (Abstraction Function)

```
AF(elements, capacity) = ลำดับข้อความตาม index ใน elements
```

พูดง่ายๆ คือ field `elements` (ArrayList) ตัวนี้แหละคือค่านามธรรมโดยตรง แค่เป็นลำดับของ string เช่น `["A", "B", "C"]` ก็คือลำดับข้อความ A, B, C นั่นเอง ส่วน `capacity` ก็คือค่าคงที่ที่บอก limit สูงสุดของ stack นี้ (กำหนดตอนสร้าง object แล้วไม่เปลี่ยนอีก)

---

## RI (Rep Invariant)

ต้อง**จริงทุกข้อ**ตลอดเวลา ไม่งั้น rep พัง:

- `elements` ห้าม null
- จำนวนสมาชิกใน `elements` ≤ `capacity`
- ห้ามมี element ไหนเป็น `null`
- ห้ามมี element ไหนเป็น `""` (string ว่าง)
- ห้ามมี element ซ้ำกัน (unique ทุกตัว)
- แต่ละตัวอักษรในแต่ละข้อความ ต้องเป็นแค่ตัวอักษร/ตัวเลข/เว้นวรรค ห้ามมีอักขระพิเศษ เช่น `@ # $ %`

เช็คด้วย `checkRep()` ทุกครั้งหลังสร้าง object หรือหลัง mutate (push/pop)

---

## Rep Exposure

- field ทุกตัวเป็น `private final` อยู่แล้ว เข้าจากข้างนอกตรงๆ ไม่ได้
- ตอนรับ list เข้ามาใน constructor (`BoundedStack(List<String> initial)`) → copy ใส่ ArrayList ใหม่ ไม่ได้เก็บ reference ของ list ที่ส่งเข้ามาตรงๆ ดังนั้นถ้าคนเรียกไปแก้ list เดิมทีหลัง จะไม่กระทบ rep ข้างใน
- ตอน return ใน `getElements()` → copy list ใหม่ทุกครั้งก่อน return เหมือนกัน ถ้าคนเอาไปแก้ (เช่น `.clear()`, `.add()`) ก็จะไม่กระทบตัวจริงข้างใน

สรุปคือ ทั้งขาเข้าขาออกมี defensive copy หมด ปลอดภัย

---

## Method แต่ละตัว

### Creator

**`BoundedStack(int capacity)`**
- ต้องการ: `capacity >= 0`
- ทำ: สร้าง stack เปล่าๆ ที่มี limit สูงสุด = `capacity`
- โยน: `IllegalArgumentException` ถ้า `capacity < 0`

**`BoundedStack(List<String> initial)`**
- ต้องการ: `initial` ห้าม null, ในนั้นห้ามมีตัวไหน null/ว่าง/ซ้ำ/มีอักขระพิเศษ
- ทำ: สร้าง stack ที่มีข้อมูลตาม `initial` (copy มา ไม่แชร์ reference), capacity ของ stack ใหม่ = ขนาดของ `initial`
- โยน: `IllegalArgumentException` ถ้าผิดเงื่อนไขข้อไหนก็ตามข้างบน

### Mutator

**`push(String information)` → boolean**
- ต้องการ: `information` ห้าม null, ห้ามว่าง, ห้ามมีอักขระพิเศษ (เช็คก่อนเลย ผ่านเงื่อนไขนี้ไม่ได้คือ throw ทันที)
- ทำ:
  - ถ้ายังไม่เต็ม และยังไม่มีข้อความนี้อยู่ → เพิ่มต่อท้าย, return `true`
  - ถ้าเต็มแล้ว หรือมีข้อความนี้อยู่แล้ว → ไม่ทำอะไร, return `false` (ไม่ throw นะ กรณีนี้แค่ false เฉยๆ)
- โยน: `IllegalArgumentException` ถ้า format ข้อความผิด (null/ว่าง/อักขระพิเศษ)

**`pop()` → String**
- ต้องการ: stack ต้องไม่ว่าง
- ทำ: เอาตัวสุดท้ายออกจาก list
- return: ตัวที่เพิ่งเอาออกไป
- โยน: `IndexOutOfBoundsException` ถ้า stack ว่างอยู่

### Observer (ดูอย่างเดียว ไม่แก้ rep)

**`size()` → int** — จำนวน element ตอนนี้

**`isEmpty()` → boolean** — stack ว่างอยู่ไหม

**`isFull()` → boolean** — stack เต็ม (`size == capacity`) ไหม

**`getCapacity()` → int** — ค่า capacity ที่กำหนดตอนสร้าง (คงที่ตลอดอายุ object)

**`peek()` → String** — ดูตัวสุดท้ายแบบไม่เอาออก ถ้า stack ว่างจะ throw `IllegalArgumentException`

**`getElements()` → List<String>** — คืน copy ของ list ทั้งหมดตามลำดับ (ไม่ใช่ตัวจริง)

**`contains(String information)` → boolean**
- ต้องการ: `information` ห้าม null, ห้ามว่าง, ห้ามมีอักขระพิเศษ
- return: มีข้อความนี้ใน stack ไหม
- โยน: `IllegalArgumentException` ถ้า null / ว่าง / มีอักขระพิเศษ

### Producer

**`shuffled()` → BoundedStack**
คืน `BoundedStack` object ใหม่ ที่มีข้อความชุดเดียวกันแต่สลับลำดับแบบสุ่ม (`Collections.shuffle`) ตัวเดิมไม่โดนแตะ

---

## ตาราง Exception

| เมธอด | throw เมื่อไหร่ | throw อะไร |
|---|---|---|
| `BoundedStack(int)` | capacity < 0 | `IllegalArgumentException` |
| `BoundedStack(List)` | null / มี null-ว่าง-ซ้ำ-อักขระพิเศษ | `IllegalArgumentException` |
| `push(String)` | information null / ว่าง / อักขระพิเศษ | `IllegalArgumentException` |
| `pop()` | stack ว่าง | `IndexOutOfBoundsException` |
| `peek()` | stack ว่าง | `IllegalArgumentException` |
| `contains(String)` | information null / ว่าง / อักขระพิเศษ | `IllegalArgumentException` |

> note: `push()` เวลาข้อความซ้ำหรือ stack เต็ม จะ**ไม่ throw** แค่ return false เฉยๆ คนละเคสกับตอน format ข้อความผิด อันนั้น throw

---

## การคอมไพล์และรัน

```bash
# คอมไพล์
javac BoundedStack.java BoundedStackTest.java

# รันชุดทดสอบ (ต้องเปิด assertion ด้วย -ea เพื่อให้ checkRep() ทำงาน)
java -ea BoundedStackTest
```

## โครงสร้างไฟล์

```
.
├── BoundedStack.java       # โครงสร้างข้อมูลหลัก (ADT)
├── BoundedStackTest.java   # ชุดทดสอบ
└── README.md
```
