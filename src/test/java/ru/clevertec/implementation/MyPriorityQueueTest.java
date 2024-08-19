package ru.clevertec.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.PriorityQueue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MyPriorityQueueTest {

    @Nested
    @DisplayName("tests with integer type")
    class IntegerTests {
        private MyPriorityQueue<Integer> queue;

        @BeforeEach
        void setUp() {
            queue = new MyPriorityQueue<>();
        }

        @Test
        void checkToList() {
            queue.add(5);
            queue.add(3);
            queue.add(4);

            assertThat(queue.toList()).containsExactly(3, 5, 4);
        }

        @Test
        void testAddAndPeek() {
            queue.add(6);
            queue.add(1);
            queue.add(3);
            queue.add(2);

            assertThat(queue.size()).isEqualTo(4);
            assertThat(queue.toList()).containsExactly(1, 2, 3, 6);

            assertThat(queue.peek()).isEqualTo(1);
            assertThat(queue.size()).isEqualTo(4);
        }

        @Test
        void testPool() {
            queue.add(5);
            queue.add(7);
            queue.add(3);
            queue.add(4);

            assertThat(queue.size()).isEqualTo(4);
            assertThat(queue.toList()).containsExactly(3, 4, 5, 7);

            assertThat(queue.pool()).isEqualTo(3);
            assertThat(queue.size()).isEqualTo(3);
            assertThat(queue.toList()).containsExactly(4, 7, 5);
        }

        @Test
        void testPoolAndEmpty() {
            queue.add(10);
            queue.add(3);

            assertThat(queue.size()).isEqualTo(2);
            assertThat(queue.toList()).containsExactly(3, 10);

            assertThat(queue.pool()).isEqualTo(3);
            assertThat(queue.pool()).isEqualTo(10);
            assertThat(queue.isEmpty()).isTrue();
        }

        @Test
        void shouldThrowExceptionWhenAddNull() {
            assertThrows(IllegalArgumentException.class, () -> queue.add(null));
        }
    }

    @Nested
    @DisplayName("tests with integer type and comparator")
    class IntegerWithComparatorTests {
        private MyPriorityQueue<Integer> queue;

        @BeforeEach
        void setUp() {
            Comparator<Integer> comparator = Integer::compare;
            queue = new MyPriorityQueue<>(comparator.reversed());
        }

        @Test
        void testAddAndPeek() {
            queue.add(10);
            queue.add(20);
            queue.add(15);

            assertThat(queue.size()).isEqualTo(3);
            assertThat(queue.toList()).containsExactly(20, 10, 15);

            assertThat(queue.peek()).isEqualTo(20);
            assertThat(queue.size()).isEqualTo(3);
        }

        @Test
        void testPool() {
            queue.add(5);
            queue.add(20);
            queue.add(30);
            queue.add(30);

            assertThat(queue.size()).isEqualTo(4);
            assertThat(queue.toList()).containsExactly(30, 30, 20, 5);

            assertThat(queue.pool()).isEqualTo(30);
            assertThat(queue.size()).isEqualTo(3);
            assertThat(queue.toList()).containsExactly(30, 5, 20);
        }
    }

    @Nested
    @DisplayName("tests with custom class with Comparable")
    class CustomClassTests {
        private PriorityQueue<Person> queue;

        @BeforeEach
        void setUp() {
            queue = new PriorityQueue<>();
        }

        @Test
        void testAddAndPeek() {
            Person person1 = new Person("Pavel", 19);
            Person person2 = new Person("Max", 21);
            Person person3 = new Person("Albert", 20);

            queue.add(person1);
            queue.add(person2);
            queue.add(person3);

            assertThat(queue.toArray()).containsExactly(person1, person2, person3);
            assertThat(queue.peek()).isEqualTo(person1);
            assertThat(queue.size()).isEqualTo(3);
        }

        @Test
        void testPool() {
            Person person1 = new Person("Pavel", 19);
            Person person2 = new Person("Ivan", 19);
            Person person3 = new Person("Darya", 20);

            queue.add(person1);
            queue.add(person2);
            queue.add(person3);

            assertThat(queue.toArray()).containsExactly(person1, person2, person3);
            assertThat(queue.poll()).isEqualTo(person1);
            assertThat(queue.size()).isEqualTo(2);
        }


        private record Person(String name, int age) implements Comparable<Person> {
            @Override
            public int compareTo(Person o) {
                return Integer.compare(this.age, o.age);
            }
        }
    }

    @Nested
    @DisplayName("tests without comparable")
    class ClassWithoutComparableTests {
        private record Person(String name, int age) {
        }

        private MyPriorityQueue<Person> queue;

        @Test
        void shouldThrowExceptionWhenClassWithoutComparable() {
            queue = new MyPriorityQueue<>();
            assertThrows(IllegalArgumentException.class, () -> queue.add(new Person("Ey", 12)));
        }
    }
}