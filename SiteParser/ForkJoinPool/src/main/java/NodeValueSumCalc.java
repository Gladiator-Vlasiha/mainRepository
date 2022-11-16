import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class NodeValueSumCalc extends RecursiveTask<Long> {

    private  Node node;

    public NodeValueSumCalc(Node node) {
        this.node = node;
    }

    @Override
    protected Long compute()
    {
        long sum = node.getValue();
        List<NodeValueSumCalc> taskList= new ArrayList<>();
        for(Node child : node.getChildren())
        {
            NodeValueSumCalc task = new NodeValueSumCalc(child);
            task.fork();
            taskList.add(task);
        }
        for(NodeValueSumCalc task : taskList){
            sum+=task.join();
        }
        return sum;
    }
}
