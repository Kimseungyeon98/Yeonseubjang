import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    static int n;
    static int[] arr,dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        arr = new int[n];
        dp = new int[n];
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < n; i++) {
            lis(i);
        }

        int max = Integer.MIN_VALUE;
        for(int i=0; i<n; i++){
            max = Math.max(max, dp[i]);
        }
        System.out.println(Arrays.toString(dp));
        System.out.println(max);
    }
    static int lis(int n){
        if(dp[n] == 0){
            dp[n] = 1;

            for(int i=n-1; i>=0; i--){
                if(arr[i] < arr[n]) {
                    dp[n] = Math.max(dp[n], lis(i) + 1);
                }
            }
        }
        return dp[n];
    }
}