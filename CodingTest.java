import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class CodingTest {
    static Map<Character, Node> map;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());

        map = new HashMap<>();

        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            char present = st.nextToken().charAt(0);
            char left = st.nextToken().charAt(0);
            char right = st.nextToken().charAt(0);
            Node node = new Node(present);
            if(left != '.'){
                node.leftChild = new Node(left);
            }
            if(right != '.'){
                node.rightChild = new Node(right);
            }
            map.put(node.present, node);
        }
        preOrder(map.get('A'));

    }
    // 전위
    static void preOrder(Node root){
        if(root == null) return;
        System.out.print(root.present);
        preOrder(map.get(root.leftChild.present));
        preOrder(map.get(root.rightChild.present));
    }
    // 중위
    static void inOrder(Node root){
        if(root == null) return;
        inOrder(root.leftChild);
        System.out.println(root.present);
        inOrder(root.rightChild);
    }
    // 후위
    static void postOrder(Node root){
        if(root == null) return;
        postOrder(root.leftChild);
        postOrder(root.rightChild);
        System.out.println(root.present);
    }
}
class Node{
    char present;
    Node leftChild;
    Node rightChild;

    Node(char present){
        this.present = present;
        leftChild = null;
        rightChild = null;
    }
}