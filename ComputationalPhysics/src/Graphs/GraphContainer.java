package Graphs;

import java.util.ArrayList;
import java.util.List;

public class GraphContainer {
    private int numberOfGraphs;
    private List graphs = new ArrayList();
    
    public GraphContainer(int numberOfGraphs, String... labels){
        this.numberOfGraphs = numberOfGraphs;
        
        String[] labelArr = new String[this.numberOfGraphs];
        
        int labelCount = 0;
        
        for (String label : labels){
            labelArr[labelCount++] = label;
        }
        
        labelCount = 0;
        
        for (int ndx = 0; ndx < this.numberOfGraphs; ndx++){
            graphs.add(new Graph(labelArr[labelCount++]));
        }
    }
}
