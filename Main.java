import java.util.Arrays;

// 궁금증
// 두 가지 정렬 메서드가 있다.
// 첫 번째 메서드는 배열에 따라 1~10초로 시간이 달라진다.
// 두 번째 메서드는 어떤 배열이던지 5초라는 시간이 걸린다.
// 어떤 메서드가 더 효율적인 메서드라고 할 수 있는가?
// 단, 어떤 경우라도 한 가지 메서드만 사용할 수 있다.(경우에 따라 다른 메서드를 사용한다는 답변 금지)
// 일단 내 답변은 기존에 정렬이 꾸준히 이루어지면서 새로운 데이터가 추가 되는 경우에는 첫 번째 메서드로 할 것이고(기존 배열이 어느 정도 정렬되어 있을 경우 정렬 시간이 빨라진다는 가정 하에),
// 그렇지 않은 경우에는 안정적인 효율을 위해 두 번째 메서드를 사용할 것이다.
public class Main {
    // 하나의 배열과 두 인덱스를 인자로 받아 해당 인덱스 값들을 맞바꾸는 메소드
    static void swap(int[] array, int start, int end){
        int temp = array[start];
        array[start] = array[end];
        array[end] = temp;
    }
    // 두 배열이 정렬된 게 맞는지 확인
    // a는 정렬되지 않은 원초 배열, b는 정렬이 완료된 배열
    static boolean sortTest(int[] a, int[] b){
        int[] tmp = a.clone();
        Arrays.sort(tmp);
        return Arrays.equals(tmp, b);
    }
    // 테스트 케이스 생성기
    static int[] initCase(int n){
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = (int)Math.round(Math.random()*100);
        }
        return arr;
    }

    public static void main(String[] args) {
        // 테스트 케이스를 랜덤하게 생성
        int[] arr = initCase(10000);
        // 실행 시간을 알기 위함
        long startTime,endTime;

        // 버블정렬
        startTime = System.currentTimeMillis();
        bubbleSort(arr);
        endTime = System.currentTimeMillis();
        if(sortTest(arr,bubbleSort(arr))){
            System.out.println("정렬 성공");
            System.out.printf("버블 정렬의 총 실행 시간 : %dms 입니다.\n",endTime-startTime);
        } else{
            System.out.println("정렬 실패");
        }

        // 삽입정렬
        startTime = System.currentTimeMillis();
        insertSort(arr);
        endTime = System.currentTimeMillis();
        if(sortTest(arr,insertSort(arr))){
            System.out.println("정렬 성공");
            System.out.printf("삽입 정렬의 총 실행 시간 : %dms 입니다.\n",endTime-startTime);
        } else {
            System.out.println("정렬 실패");
        }
    }

    // 버블정렬
    static int[] bubbleSort(int[] array){
        int[] result = array.clone();
        for (int i = 0; i < result.length-1; i++) {
            for (int j = 0; j < result.length-1-i; j++) {
                if (result[j] > result[j + 1]) {
                    swap(result, j, j+1);
                }
            }
        }
        return result;
    }

    // 삽입정렬
    static int[] insertSort(int[] array){
        int[] result = array.clone();
        for (int i = 1; i < result.length; i++) {
            int key = result[i];
            int j = i - 1;
            // key보다 큰 값을 오른쪽으로 이동
            while (j >= 0 && result[j] > key) {
                swap(result, j+1, j);
                j--;
            }
        }
        return result;
    }
}