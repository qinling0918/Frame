package android.util;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SparseArray<E> {
    private static final Object DELETED = new Object();
    private boolean mGarbage;
    private int[] mKeys;
    private Object[] mValues;
    private int mSize;

    public SparseArray() {
        this(10);
    }

    public SparseArray(int initialCapacity) {
        this.mGarbage = false;
        this.mKeys = new int[initialCapacity];
        this.mValues = new Object[initialCapacity];
        this.mSize = 0;
    }

    public E get(int key) {
        return this.get(key, null);
    }
    @SuppressWarnings("unchecked")
    public E get(int key, E valueIfKeyNotFound) {
        int i = binarySearch(this.mKeys, 0, this.mSize, key);
        return i >= 0 && this.mValues[i] != DELETED ? (E) this.mValues[i] : valueIfKeyNotFound;
    }

    public void delete(int key) {
        int i = binarySearch(this.mKeys, 0, this.mSize, key);
        if (i >= 0 && this.mValues[i] != DELETED) {
            this.mValues[i] = DELETED;
            this.mGarbage = true;
        }

    }

    public void remove(int key) {
        this.delete(key);
    }

    private void gc() {
        int n = this.mSize;
        int o = 0;
        int[] keys = this.mKeys;
        Object[] values = this.mValues;

        for(int i = 0; i < n; ++i) {
            Object val = values[i];
            if (val != DELETED) {
                if (i != o) {
                    keys[o] = keys[i];
                    values[o] = val;
                }

                ++o;
            }
        }

        this.mGarbage = false;
        this.mSize = o;
    }

    public void put(int key, E value) {
        int i = binarySearch(this.mKeys, 0, this.mSize, key);
        if (i >= 0) {
            this.mValues[i] = value;
        } else {
            i = ~i;
            if (i < this.mSize && this.mValues[i] == DELETED) {
                this.mKeys[i] = key;
                this.mValues[i] = value;
                return;
            }

            if (this.mGarbage && this.mSize >= this.mKeys.length) {
                this.gc();
                i = ~binarySearch(this.mKeys, 0, this.mSize, key);
            }

            if (this.mSize >= this.mKeys.length) {
                int n = Math.max(this.mSize + 1, this.mKeys.length * 2);
                int[] nkeys = new int[n];
                Object[] nvalues = new Object[n];
                System.arraycopy(this.mKeys, 0, nkeys, 0, this.mKeys.length);
                System.arraycopy(this.mValues, 0, nvalues, 0, this.mValues.length);
                this.mKeys = nkeys;
                this.mValues = nvalues;
            }

            if (this.mSize - i != 0) {
                System.arraycopy(this.mKeys, i, this.mKeys, i + 1, this.mSize - i);
                System.arraycopy(this.mValues, i, this.mValues, i + 1, this.mSize - i);
            }

            this.mKeys[i] = key;
            this.mValues[i] = value;
            ++this.mSize;
        }

    }

    public int size() {
        if (this.mGarbage) {
            this.gc();
        }

        return this.mSize;
    }

    public int keyAt(int index) {
        if (this.mGarbage) {
            this.gc();
        }

        return this.mKeys[index];
    }

    public E valueAt(int index) {
        if (this.mGarbage) {
            this.gc();
        }

        return (E) this.mValues[index];
    }

    public void setValueAt(int index, E value) {
        if (this.mGarbage) {
            this.gc();
        }

        this.mValues[index] = value;
    }

    public int indexOfKey(int key) {
        if (this.mGarbage) {
            this.gc();
        }

        return binarySearch(this.mKeys, 0, this.mSize, key);
    }

    public int indexOfValue(E value) {
        if (this.mGarbage) {
            this.gc();
        }

        for(int i = 0; i < this.mSize; ++i) {
            if (this.mValues[i] == value) {
                return i;
            }
        }

        return -1;
    }

    public void clear() {
        int n = this.mSize;
        Object[] values = this.mValues;

        for(int i = 0; i < n; ++i) {
            values[i] = null;
        }

        this.mSize = 0;
        this.mGarbage = false;
    }

    public void append(int key, E value) {
        if (this.mSize != 0 && key <= this.mKeys[this.mSize - 1]) {
            this.put(key, value);
        } else {
            if (this.mGarbage && this.mSize >= this.mKeys.length) {
                this.gc();
            }

            int pos = this.mSize;
            if (pos >= this.mKeys.length) {
                int n = Math.max(pos + 1, this.mKeys.length * 2);
                int[] nkeys = new int[n];
                Object[] nvalues = new Object[n];
                System.arraycopy(this.mKeys, 0, nkeys, 0, this.mKeys.length);
                System.arraycopy(this.mValues, 0, nvalues, 0, this.mValues.length);
                this.mKeys = nkeys;
                this.mValues = nvalues;
            }

            this.mKeys[pos] = key;
            this.mValues[pos] = value;
            this.mSize = pos + 1;
        }
    }

    public void ensureCapacity(int capacity) {
        if (this.mGarbage && this.mSize >= this.mKeys.length) {
            this.gc();
        }

        if (this.mKeys.length < capacity) {
            int[] nkeys = new int[capacity];
            Object[] nvalues = new Object[capacity];
            System.arraycopy(this.mKeys, 0, nkeys, 0, this.mKeys.length);
            System.arraycopy(this.mValues, 0, nvalues, 0, this.mValues.length);
            this.mKeys = nkeys;
            this.mValues = nvalues;
        }

    }

    private static int binarySearch(int[] a, int start, int len, int key) {
        int high = start + len;
        int low = start - 1;

        while(high - low > 1) {
            int guess = (high + low) / 2;
            if (a[guess] < key) {
                low = guess;
            } else {
                high = guess;
            }
        }

        if (high == start + len) {
            return ~(start + len);
        } else if (a[high] == key) {
            return high;
        } else {
            return ~high;
        }
    }
    @SuppressWarnings("unchecked")
    public List<E> getValues() {
        return (List<E>) Collections.unmodifiableList(Arrays.asList((Object[])this.mValues));
    }
}
