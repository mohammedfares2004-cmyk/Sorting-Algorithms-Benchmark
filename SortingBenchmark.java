import java.util.Arrays;
import java.util.Random;
// الطالب : محمد عادل فارس

public class SortingBenchmark {
    static final int N = 100000;
//--------- اﻟﺨﻮارزﻣﯿﺎت ---------- //   
 
    static void selectionSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < a.length; j++)
                if (a[j] < a[min]) min = j;
            swap(a, i, min);
        }
    }
    static void insertionSort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int key = a[i], j = i;
            while (j > 0 && a[j - 1] > key) {
                a[j] = a[j - 1];
                j--;
            }
            a[j] = key;
        }
    }
    static void bubbleSort(int[] a) {
        boolean swapped = true;
        for (int i = 0; swapped && i < a.length - 1; i++) {
            swapped = false;
            for (int j = 0; j < a.length - 1 - i; j++)
                if (a[j] > a[j + 1]) {
                    swap(a, j, j + 1);
                    swapped = true;
                }
        }
    }
    static void mergeSort(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int mid = (lo + hi) / 2;
        mergeSort(a, lo, mid);
        mergeSort(a, mid + 1, hi);
        int[] tmp = new int[hi - lo + 1];
        int i = lo, j = mid + 1, k = 0;
        while (i <= mid && j <= hi) tmp[k++] = a[i] <= a[j] ? a[i++] : a[j++];
        while (i <= mid) tmp[k++] = a[i++];
        while (j <= hi) tmp[k++] = a[j++];
        System.arraycopy(tmp, 0, a, lo, tmp.length);
    }
    // آﺧﺮ ﻋﻨﺼﺮ = )pivot( اﻟﻤﺤﻮ

    static void quickSort(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int pivot = a[hi], p = lo;
        for (int j = lo; j < hi; j++)
            if (a[j] < pivot) swap(a, p++, j);
        swap(a, p, hi);
        quickSort(a, lo, p - 1);
        quickSort(a, p + 1, hi);
    }
    static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
//--------- اﻟﺒﯿﺎﻧﺎت ---------- //   
 
    static int[] random() {
        Random r = new Random(1);
        int[] a = new int[N];
        for (int i = 0; i < N; i++) a[i] = r.nextInt(N);
        return a;
    }
    static int[] sorted() {
        int[] a = new int[N];
        for (int i = 0; i < N; i++) a[i] = i;
        return a;
    }
    static int[] reversed() {
        int[] a = new int[N];
        for (int i = 0; i < N; i++) a[i] = N - i;
        return a;
    }
    // ﻣﺮﺗﺒﺔ 80%: ﻧﻐﯿّﺮ 20% ﻣﻦ اﻟﺨﺎﻧﺎت ﺑﻘﯿﻢ ﻋﺸﻮاﺋﯿ

    static int[] partial() {
        Random r = new Random(2);
        int[] a = sorted();
        for (int i = 0; i < N / 5; i++) a[r.nextInt(N)] = r.nextInt(N);
        return a;
    }
//--------- اﻟﻘﯿﺎس ---------- //   
 
    static long time(int algo, int[] original) {
        int[] a = Arrays.copyOf(original, original.length);
        long start = System.nanoTime();
        switch (algo) {
            case 0: selectionSort(a); break;
            case 1: insertionSort(a); break;
            case 2: bubbleSort(a); break;
            case 3: mergeSort(a, 0, a.length - 1); break;
            case 4: quickSort(a, 0, a.length - 1); break;
        }
        long end = System.nanoTime();
        return (end - start) / 1000;
    }
    static void run() {
        String[] names = {"Selection Sort", "Insertion Sort", "Bubble Sort", "Merge Sort", "Quick Sort"};
        int[][] data = {random(), reversed(), partial(), sorted()};
        System.out.println("Please wait...\n");
        System.out.printf("%-16s%16s%16s%16s%16s%n", "Algorithm", "Random", "Reversed", "Partial", "Sorted");
        for (int i = 0; i < names.length; i++) {
            System.out.printf("%-16s", names[i]);
            for (int[] d : data)
                System.out.printf("%13d µs", time(i, d));
            System.out.println();
        }
    }
    public static void main(String[] args) throws Exception {
        // Stack ﻛﺒﯿﺮ ﻷن Quick Sort ﻋﻠﻰ ﺑﯿﺎﻧﺎت ﻣﺮﺗﺒﺔ ﯾﻌﻤﻞ ~100,000 اﺳﺘﺪﻋﺎء ﻣﺘﺪاﺧﻞ
        Thread t = new Thread(null, SortingBenchmark::run, "run", 512L * 1024 * 1024);
        t.start();
        t.join();
    }
}