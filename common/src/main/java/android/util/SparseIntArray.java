package android.util;

public class SparseIntArray {
    private int[] mKeys;
    private int[] mValues;
    private int mSize;

    public SparseIntArray() {
        this(10);
    }

    public SparseIntArray(int initialCapacity) {
        this.mKeys = new int[initialCapacity];
        this.mValues = new int[initialCapacity];
        this.mSize = 0;
    }

    public int get(int key) {
        return this.get(key, 0);
    }

    public int get(int key, int valueIfKeyNotFound) {
        int i = binarySearch(this.mKeys, 0, this.mSize, key);
        return i < 0 ? valueIfKeyNotFound : this.mValues[i];
    }

    public int getClosestSmaller(int key) {
        int i = binarySearch(this.mKeys, 0, this.mSize, key);
        if (i < 0) {
            i = ~i;
            if (i > 0) {
                --i;
            }

            return this.mValues[i];
        } else {
            return this.mValues[i];
        }
    }

    public void delete(int key) {
        int i = binarySearch(this.mKeys, 0, this.mSize, key);
        if (i >= 0) {
            this.removeAt(i);
        }

    }

    public void removeAt(int index) {
        System.arraycopy(this.mKeys, index + 1, this.mKeys, index, this.mSize - (index + 1));
        System.arraycopy(this.mValues, index + 1, this.mValues, index, this.mSize - (index + 1));
        --this.mSize;
    }

    public void put(int key, int value) {
        int i = binarySearch(this.mKeys, 0, this.mSize, key);
        if (i >= 0) {
            this.mValues[i] = value;
        } else {
            i = ~i;
            if (this.mSize >= this.mKeys.length) {
                int n = Math.max(this.mSize + 1, this.mKeys.length * 2);
                int[] nkeys = new int[n];
                int[] nvalues = new int[n];
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
        return this.mSize;
    }

    public int keyAt(int index) {
        return this.mKeys[index];
    }

    public int valueAt(int index) {
        return this.mValues[index];
    }

    public int indexOfKey(int key) {
        return binarySearch(this.mKeys, 0, this.mSize, key);
    }

    public int indexOfValue(int value) {
        for(int i = 0; i < this.mSize; ++i) {
            if (this.mValues[i] == value) {
                return i;
            }
        }

        return -1;
    }

    public void clear() {
        this.mSize = 0;
    }

    public void append(int key, int value) {
        if (this.mSize != 0 && key <= this.mKeys[this.mSize - 1]) {
            this.put(key, value);
        } else {
            int pos = this.mSize;
            if (pos >= this.mKeys.length) {
                int n = Math.max(pos + 1, this.mKeys.length * 2);
                int[] nkeys = new int[n];
                int[] nvalues = new int[n];
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
}
