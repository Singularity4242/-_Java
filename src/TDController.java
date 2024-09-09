import java.util.Stack;

public class TDController {
    private final Stack<String> fileStack = new Stack<>();
    private final Stack<String> tempt = new Stack<>();
    private final String path = "";

    public void push(String Item){
        fileStack.push(Item);
    }

    public void initFolder(String path){
        //全栈为空或栈顶不为空
        if(fileStack.isEmpty() || !fileStack.peek().equals(this.path)){
            push(path);
            tempt.clear();
        }
    }
}
