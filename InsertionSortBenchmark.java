import java.util.Arrays;
import java.util.Random;

/*
 
 * الخوارزمية : الترتيب بالإدراج (Insertion Sort)
 * الطالب     : محمد عادل فارس
 * رقم القيد  : 3166091
 * حجم البيانات: N = 100,000
 * حالات الاختبار: غير مرتبة (Random) ومرتبة عكسياً (Reversely Sorted)
 */
public class InsertionSortBenchmark {

    // حجم المصفوفة الثابت المطلوب في النشاط
    static final int N = 100_000;

    // خوارزمية الترتيب بالإدراج:
    // تكبّر الجهة المرتبة بإدراج العنصر التالي من الجهة غير المرتبة في مكانه الصحيح
    static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];            // العنصر المراد إدراجه
            int j = i - 1;

            // إزاحة العناصر الأكبر من key خطوة واحدة إلى اليمين
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }

            arr[j + 1] = key;            // وضع العنصر في مكانه الصحيح
        }
    }

    // التأكد من أن المصفوفة مرتبة تصاعدياً
    static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) {
                return false;
            }
        }
        return true;
    }

    // قياس زمن الترتيب فقط (بالميكروثانية) على نسخة مستقلة من البيانات الأصلية
    static long measureMicros(int[] original) {
        // 1. أخذ نسخة مستقلة لتبدأ كل خوارزمية من نفس البيانات
        int[] testArray = Arrays.copyOf(original, original.length);

        // 2. تسجيل وقت البداية بالنانوثانية
        long startTime = System.nanoTime();

        // 3. استدعاء خوارزمية الترتيب
        insertionSort(testArray);

        // 4. تسجيل وقت النهاية بالنانوثانية
        long endTime = System.nanoTime();

        // التحقق من أن الترتيب صحيح فعلاً
        if (!isSorted(testArray)) {
            throw new IllegalStateException("Sorting failed!");
        }

        // 5. تحويل الزمن من نانوثانية إلى ميكروثانية
        return (endTime - startTime) / 1000;
    }

    public static void main(String[] args) {

        // الحالة 1: مصفوفة غير مرتبة (أعداد عشوائية)
        // seed ثابت ليكون التوليد نفسه في كل تشغيل
        int[] mohammedRandomData = new int[N];
        Random rand = new Random(2026);
        for (int i = 0; i < N; i++) {
            mohammedRandomData[i] = rand.nextInt(1_000_000);
        }

        // الحالة 2: مصفوفة مرتبة عكسياً (من الأكبر إلى الأصغر)
        int[] mohammedReverseData = new int[N];
        for (int i = 0; i < N; i++) {
            mohammedReverseData[i] = N - i;
        }

        // تشغيل تجريبي على مصفوفة صغيرة لتهيئة الـ JVM (لقياس أدق وعادل)
        measureMicros(Arrays.copyOf(mohammedRandomData, 2000));

        long randomTime = measureMicros(mohammedRandomData);
        long reverseTime = measureMicros(mohammedReverseData);

        // طباعة جدول النتائج في الـ Console
        System.out.println("==============================================");
        System.out.println(" Insertion Sort Benchmark  |  N = " + String.format("%,d", N));
        System.out.println(" Student: Mohammed Adel Fares  |  Reg. No.: 3166091");
        System.out.println("==============================================");
        System.out.printf("%-18s | %-18s%n", "Scenario", "Time (µs)");
        System.out.println("-———————————————");
        System.out.printf("%-18s | %,d µs%n", "Random", randomTime);
        System.out.printf("%-18s | %,d µs%n", "Reversely Sorted", reverseTime);
        System.out.println("==============================================");
    }
}