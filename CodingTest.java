import java.util.*;

public class CodingTest {
    // 노드 클래스 (각 노드는 index: 목적지, cost: 이동 비용을 가짐)
    static class Node implements Comparable<Node> {
        int index, cost;

        public Node(int index, int cost) {
            this.index = index;
            this.cost = cost;
        }

        // 우선순위 큐에서 비용(cost)이 작은 순으로 정렬되도록 설정
        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.cost, other.cost);
        }
    }

    // 그래프를 저장할 리스트 (각 노드마다 연결된 노드 정보를 저장)
    static List<List<Node>> graph = new ArrayList<>();

    // 최단 거리 배열 (각 노드까지의 최소 비용을 저장)
    static int[] dist;

    // 방문 여부를 체크하는 배열 (이미 방문한 노드는 재처리하지 않도록 함)
    static boolean[] visited;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 정점(n), 간선(m) 입력받기
        int n = sc.nextInt();
        int m = sc.nextInt();

        // 배열 초기화 (최단 거리는 처음에 무한대 값으로 설정)
        dist = new int[n + 1];
        visited = new boolean[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);

        // 그래프 리스트 초기화
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        // 간선 정보 입력받기 (출발 노드 s → 도착 노드 e, 비용 v)
        for (int i = 0; i < m; i++) {
            int s = sc.nextInt();
            int e = sc.nextInt();
            int v = sc.nextInt();
            graph.get(s).add(new Node(e, v)); // 방향 그래프이므로 한 방향만 추가
        }

        // 출발지(start), 도착지(end) 입력받기
        int start = sc.nextInt();
        int end = sc.nextInt();

        // 다익스트라 알고리즘 실행
        dijkstra(start);

        // 출발지에서 도착지까지의 최소 비용 출력
        System.out.println(dist[end]);
    }

    static void dijkstra(int start) {
        // 우선순위 큐 (비용이 작은 노드를 우선적으로 탐색)
        PriorityQueue<Node> pq = new PriorityQueue<>();

        // 시작 노드를 큐에 넣고, 최단 거리를 0으로 설정
        pq.add(new Node(start, 0));
        dist[start] = 0;

        // 큐가 빌 때까지 반복
        while (!pq.isEmpty()) {
            // 현재 최단 거리가 가장 짧은 노드 꺼내기
            Node now = pq.poll();
            int cur = now.index;

            // 이미 방문한 노드라면 무시
            if (visited[cur]) continue;
            visited[cur] = true; // 방문 처리

            // 현재 노드와 연결된 인접 노드들 탐색
            for (Node next : graph.get(cur)) {
                // 현재 노드를 거쳐 가는 경로가 기존 경로보다 짧다면 업데이트
                if (dist[next.index] > dist[cur] + next.cost) {
                    dist[next.index] = dist[cur] + next.cost;
                    // 갱신된 최단 거리 정보로 우선순위 큐에 추가
                    pq.add(new Node(next.index, dist[next.index]));
                }
            }
        }
    }
}
