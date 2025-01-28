import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CodingTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] arr = new int[n+1][n+1];
        boolean[] visited = new boolean[n+1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(arr[i],Integer.MAX_VALUE);
        }
        for(int i=0; i<m; i++){
            int s = sc.nextInt();
            int e = sc.nextInt();
            int v = sc.nextInt();
            arr[s][e] = v;
            arr[e][s] = v;
        }
        int start = sc.nextInt();
        int end = sc.nextInt();

        // 입력 받은 값 확인 시작
        /*System.out.println("도시의 개수 : " + n);
        System.out.println("버스의 개수 : " + m);
        System.out.println("출발 도시의 번호, 도착 도시의 번호, 버스 비용");
        for (int i = 0; i < arr.length; i++) {
            System.out.println(Arrays.toString(arr[i]));
        }
        System.out.println("출발하고자 하는 도시의 번호 : " + start);
        System.out.println("도착하고자 하는 도시의 번호 : " + end);*/
        // 입력 받은 값 확인 끝

        // 알고리즘 구현 시작
        visited[start] = true;
        for(int i=1; i<n; i++){
            renew(arr, minValue(arr[start],visited), start);
            System.out.println("---------------" + i + "번째 시작 ---------------");
            for(int j=1; j<=n; j++){
                System.out.println(Arrays.toString(arr[j]));
            }
            System.out.println("---------------" + i + "번째 끝 ---------------");
        }
    }
    static void renew(int[][] arr, int x, int y){
        int tmp = arr[x][y];
        for(int i=1; i<arr.length; i++){
            if(i!=x&&i!=y&&arr[x][i]+tmp<arr[y][i]){
                arr[y][i] = arr[x][i]+tmp;
                arr[i][y] = arr[y][i];
            }
        }
    }
    // 한 배열의 최소값 중 방문하지 않은 인덱스를 반환
    static int minValue(int[] arr, boolean[] visited) {
        int min = Integer.MAX_VALUE;
        //최소값 찾기
        for(int i=1; i<arr.length; i++){
            if(arr[i]<min){
                min = arr[i];
            }
        }
        //최소값 중 방문하지 않은 인덱스를 반환하고 방문 처리
        for(int i=1; i<arr.length; i++){
            if(!visited[i] && arr[i]==min){
                visited[i] = true;
                return i;
            }
        }
        return -1;
    }
}


/*
* 도시의 개수(n)
* 버스의 개수(m)
* m+2
* 출발 도시의 번호, 도착 도시의 번호, 버스 비용
* 출발점의 도시번호, 도착점의 도시번호
* */