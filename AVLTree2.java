import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class TreeNode {
    int value, height;
    TreeNode left, right;

    TreeNode(int item) {
        value = item;
        height = 1;
        left = right = null;
    }
}

class AVLTree {
    TreeNode root;

    int height(TreeNode node) {
        if (node == null) return 0;
        return node.height;
    }

    int getBalance(TreeNode node) {
        if (node == null) return 0;
        return height(node.left) - height(node.right);
    }

    TreeNode rightRotate(TreeNode y) {
        TreeNode x = y.left;
        TreeNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    TreeNode leftRotate(TreeNode x) {
        TreeNode y = x.right;
        TreeNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    void insert(int value) {
        System.out.println("\nInsert " + value + ":");
        root = insertRec(root, value);
        printTreeState(); // Print the tree state after insertion
    }

    TreeNode insertRec(TreeNode node, int value) {
        if (node == null) {
            System.out.println("- " + value + " becomes the root node");
            return new TreeNode(value);
        }

        if (value < node.value) {
            System.out.println("- " + value + " becomes the left child of " + node.value);
            node.left = insertRec(node.left, value);
        } else if (value > node.value) {
            System.out.println("- " + value + " becomes the right child of " + node.value);
            node.right = insertRec(node.right, value);
        } else {
            System.out.println("- " + value + " is a duplicate and will not be inserted");
            return node; // Duplicate values are not allowed
        }

        // Update height and balance the tree
        node.height = 1 + Math.max(height(node.left), height(node.right));
        return balance(node);
    }

    void delete(int value) {
        System.out.println("\nDelete " + value + ":");
        root = deleteRec(root, value);
        printTreeState(); // Print the tree state after deletion
    }

    TreeNode deleteRec(TreeNode root, int value) {
        if (root == null) {
            System.out.println("- " + value + " not found in the tree");
            return root;
        }

        if (value < root.value) {
            System.out.println("- Searching for " + value + " in the left subtree of " + root.value);
            root.left = deleteRec(root.left, value);
        } else if (value > root.value) {
            System.out.println("- Searching for " + value + " in the right subtree of " + root.value);
            root.right = deleteRec(root.right, value);
        } else {
            System.out.println("- " + value + " found and will be deleted");
            if (root.left == null || root.right == null) {
                TreeNode temp = (root.left != null) ? root.left : root.right;
                if (temp == null) {
                    System.out.println("- " + value + " is a leaf node and will be removed");
                    temp = root;
                    root = null;
                } else {
                    System.out.println("- " + value + " has one child (" + temp.value + ") and will be replaced");
                    root = temp;
                }
            } else {
                // Replace with the inorder predecessor (max value in the left subtree)
                TreeNode maxValLeft = findMax(root.left);
                System.out.println("- " + value + " has two children. Replacing with inorder predecessor (" + maxValLeft.value + ")");
                root.value = maxValLeft.value;
                root.left = deleteRec(root.left, maxValLeft.value);
            }
        }

        if (root == null) return root;

        // Update height and balance the tree
        root.height = 1 + Math.max(height(root.left), height(root.right));
        return balance(root);
    }

    // Find the node with the maximum value in a subtree
    TreeNode findMax(TreeNode node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    // Balance the tree
    TreeNode balance(TreeNode node) {
        int balanceFactor = getBalance(node);

        // Left-heavy
        if (balanceFactor > 1) {
            if (getBalance(node.left) >= 0) {
                System.out.println("- Unbalance: Left-Left case at node " + node.value);
                System.out.println("- Performing right rotation on " + node.value);
                return rightRotate(node);
            } else {
                System.out.println("- Unbalance: Left-Right case at node " + node.value);
                System.out.println("- Performing left rotation on " + node.left.value + ", then right rotation on " + node.value);
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }

        // Right-heavy
        if (balanceFactor < -1) {
            if (getBalance(node.right) <= 0) {
                System.out.println("- Unbalance: Right-Right case at node " + node.value);
                System.out.println("- Performing left rotation on " + node.value);
                return leftRotate(node);
            } else {
                System.out.println("- Unbalance: Right-Left case at node " + node.value);
                System.out.println("- Performing right rotation on " + node.right.value + ", then left rotation on " + node.value);
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }

        System.out.println("- Tree is balanced at node " + node.value);
        return node;
    }

    void fillArray(TreeNode node, int[] array, int index) {
        if (node == null) return;
        array[index] = node.value;
        fillArray(node.left, array, 2 * index + 1);
        fillArray(node.right, array, 2 * index + 2);
    }

    void preorder() {
        List<Integer> result = new ArrayList<>();
        preorderRec(root, result);
        printTraversal(result);
    }

    void preorderRec(TreeNode node, List<Integer> result) {
        if (node != null) {
            result.add(node.value);
            preorderRec(node.left, result);
            preorderRec(node.right, result);
        }
    }

    void inorder() {
        List<Integer> result = new ArrayList<>();
        inorderRec(root, result);
        printTraversal(result);
    }

    void inorderRec(TreeNode node, List<Integer> result) {
        if (node != null) {
            inorderRec(node.left, result);
            result.add(node.value);
            inorderRec(node.right, result);
        }
    }

    void postorder() {
        List<Integer> result = new ArrayList<>();
        postorderRec(root, result);
        printTraversal(result);
    }

    void postorderRec(TreeNode node, List<Integer> result) {
        if (node != null) {
            postorderRec(node.left, result);
            postorderRec(node.right, result);
            result.add(node.value);
        }
    }

    private void printTraversal(List<Integer> result) {
        for (int i = 0; i < result.size(); i++) {
            System.out.print(result.get(i));
            if (i < result.size() - 1) System.out.print(", ");
        }
        System.out.println();
    }

    int getArraySize() {
        int h = height(root);
        return (int) Math.pow(2, h) - 1;
    }

    void printTreeState() {
        int arraySize = getArraySize();
        int[] arrayRepresentation = new int[arraySize];
        fillArray(root, arrayRepresentation, 0);

        System.out.println("\n1-D Array Representation:");
        System.out.println("Size of array = 2^K - 1 = 2^" + height(root) + " - 1 = " + arraySize + " nodes");
        System.out.print("Declaration = int AVL[" + arraySize + "] = {");
        for (int i = 0; i < arrayRepresentation.length; i++) {
            System.out.print(arrayRepresentation[i]);
            if (i < arrayRepresentation.length - 1) System.out.print(", ");
        }
        System.out.println("}\n");

        System.out.println("Index Table:");
        for (int i = 0; i < arrayRepresentation.length; i++) {
            System.out.printf("%-8d", i);
        }
        System.out.println();
        for (int i = 0; i < arrayRepresentation.length; i++) {
            System.out.printf("%-8d", arrayRepresentation[i]);
        }
        System.out.println("\n");

        System.out.println("Traversals:");
        System.out.print("Preorder = ");
        preorder();
        System.out.print("Inorder = ");
        inorder();
        System.out.print("Postorder = ");
        postorder();
    }
}

public class AVLTree2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AVLTree avl = new AVLTree();

        while (true) {
            System.out.println("\nEnter operation (e.g., 'Insert: 10, 35, 50' or 'Delete: 35, 48'). Type 'reset' to clear the tree or 'done' to exit:");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("done")) {
                System.out.println("Exiting the program.");
                break; // Exit the loop and terminate the program
            }

            if (input.equalsIgnoreCase("reset")) {
                avl = new AVLTree();
                System.out.println("AVL tree has been reset.");
                continue;
            }

            String[] parts = input.split(":", 2);
            if (parts.length != 2) {
                System.out.println("Invalid input format. Use 'Insert: values' or 'Delete: values'.");
                continue;
            }

            String operation = parts[0].trim();
            String[] values = parts[1].trim().split(",");

            for (String value : values) {
                try {
                    int num = Integer.parseInt(value.trim());
                    if (operation.equalsIgnoreCase("Insert")) {
                        avl.insert(num);
                    } else if (operation.equalsIgnoreCase("Delete")) {
                        avl.delete(num);
                    } else {
                        System.out.println("Invalid operation. Use 'Insert' or 'Delete'.");
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid value: " + value + ". Please enter valid integers.");
                }
            }
        }

        scanner.close(); // Close the scanner before exiting
    }
}
