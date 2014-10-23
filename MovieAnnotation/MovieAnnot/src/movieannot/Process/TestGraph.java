/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movieannot.Process;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeController;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.graph.api.*;
import org.gephi.io.processor.plugin.DefaultProcessor;
import org.gephi.layout.plugin.AutoLayout;
import org.gephi.layout.plugin.force.StepDisplacement;
import org.gephi.layout.plugin.force.yifanHu.YifanHuLayout;
import org.gephi.layout.plugin.forceAtlas.ForceAtlasLayout;
import org.gephi.preview.api.PreviewController;
import org.gephi.preview.api.PreviewModel;
import org.gephi.preview.api.PreviewProperty;
import org.gephi.preview.api.ProcessingTarget;
import org.gephi.preview.api.RenderTarget;
import org.gephi.preview.types.DependantOriginalColor;
import org.gephi.preview.types.EdgeColor;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.gephi.statistics.plugin.GraphDistance;
import org.openide.util.Lookup;
import processing.core.PApplet;

/**
 * This demo shows basic features from GraphAPI, how to create and query a graph
 * programmatically.
 *
 * @author Mathieu Bastian
 */
public class TestGraph {

    public void script() {
        //Init a project - and therefore a workspace
        ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
        pc.newProject();
        Workspace workspace = pc.getCurrentWorkspace();

        //Get a graph model - it exists because we have a workspace
        GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();

        List<Node> ListNodes = new ArrayList<>();
        String nd = "Node ";
        for (int i = 0; i < 3; i++) {
            nd = nd + String.valueOf(i);
            ListNodes.add(graphModel.factory().newNode(nd));
            ListNodes.get(i).getNodeData().setLabel(nd);
        }
        
        Edge e1 = graphModel.factory().newEdge(ListNodes.get(0), ListNodes.get(1), 0.1f, false);
        Edge e2 = graphModel.factory().newEdge(ListNodes.get(1), ListNodes.get(2), 0.5f, false);
        Edge e3 = graphModel.factory().newEdge(ListNodes.get(0), ListNodes.get(2), 0.7f, false);
        //Create three nodes
        /*
        Node n0 = graphModel.factory().newNode("n0");
        n0.getNodeData().setLabel("Node 0");
        Node n1 = graphModel.factory().newNode("n1");
        n1.getNodeData().setLabel("Node 1");
        Node n2 = graphModel.factory().newNode("n2");
        n2.getNodeData().setLabel("Node 2");
        
        Edge e1 = graphModel.factory().newEdge(n1, n2, 0.1f, false);
        Edge e2 = graphModel.factory().newEdge(n0, n2, 0.2f, false);
        Edge e3 = graphModel.factory().newEdge(n2, n0, 0.3f, false);   //This is e2's mutual edge
        Edge e4 = graphModel.factory().newEdge(n1, n0, 0.5f, false); 
        */

        //Create three edges
        

    //Append as a Directed Graph
        /*   
         DirectedGraph directedGraph = graphModel.getDirectedGraph();
         directedGraph.addNode(n0);
         directedGraph.addNode(n1);
         directedGraph.addNode(n2);
         directedGraph.addEdge(e1);
         directedGraph.addEdge(e2);
         directedGraph.addEdge(e3);

         //Count nodes and edges
         System.out.println("Nodes: "+directedGraph.getNodeCount()+" Edges: "+directedGraph.getEdgeCount());
         */
        //Get a UndirectedGraph now and count edges;
        
        //Create three nodes
        /*
        Node n0 = graphModel.factory().newNode("n0");
        n0.getNodeData().setLabel("Node 0");
        Node n1 = graphModel.factory().newNode("n1");
        n1.getNodeData().setLabel("Node 1");
        Node n2 = graphModel.factory().newNode("n2");
        n2.getNodeData().setLabel("Node 2");
        
        Edge e1 = graphModel.factory().newEdge(n1, n2, 0.1f, false);
        Edge e2 = graphModel.factory().newEdge(n0, n2, 0.2f, false);
        Edge e3 = graphModel.factory().newEdge(n2, n0, 0.3f, false);   //This is e2's mutual edge
        Edge e4 = graphModel.factory().newEdge(n1, n0, 0.5f, false); 
        */

        //Create three edges
        

    //Append as a Directed Graph
        /*   
         DirectedGraph directedGraph = graphModel.getDirectedGraph();
         directedGraph.addNode(n0);
         directedGraph.addNode(n1);
         directedGraph.addNode(n2);
         directedGraph.addEdge(e1);
         directedGraph.addEdge(e2);
         directedGraph.addEdge(e3);

         //Count nodes and edges
         System.out.println("Nodes: "+directedGraph.getNodeCount()+" Edges: "+directedGraph.getEdgeCount());
         */
        //Get a UndirectedGraph now and count edges
        UndirectedGraph undirectedGraph = graphModel.getUndirectedGraph();
        GraphView view = undirectedGraph.getView();
        for (int i = 0; i < ListNodes.size(); i++) { 
            undirectedGraph.addNode(ListNodes.get(i));
        }
        undirectedGraph.addEdge(e1);
        undirectedGraph.addEdge(e2);
        undirectedGraph.addEdge(e3);
        /*
            
        undirectedGraph.addNode(n0);
        undirectedGraph.addNode(n1);
        undirectedGraph.addNode(n2);
        undirectedGraph.addEdge(e3);
        undirectedGraph.addEdge(e2);
        undirectedGraph.addEdge(e1);
        undirectedGraph.addEdge(e4);
                */
        
        //Rank Graph

        AttributeModel attributeModel = Lookup.getDefault().lookup(AttributeController.class).getModel();
        GraphDistance distance = new GraphDistance();
        distance.setDirected(false);
        distance.execute(graphModel, attributeModel);

        AttributeColumn col = attributeModel.getNodeTable().getColumn(GraphDistance.CLOSENESS);
        for (Node n : undirectedGraph.getNodes()) {
            Double centrality = (Double) n.getNodeData().getAttributes().getValue(col.getIndex());
            System.out.println(centrality.toString());
        }

        //Layout 
        YifanHuLayout layout = new YifanHuLayout(null, new StepDisplacement(1f));
        layout.setGraphModel(graphModel);
        layout.initAlgo();
        layout.resetPropertiesValues();
        layout.setOptimalDistance(50f);

        for (int i = 0; i < 100 && layout.canAlgo(); i++) {
            layout.goAlgo();
        }
        layout.endAlgo();

        //Visualization
        PreviewController previewController = Lookup.getDefault().lookup(PreviewController.class);
        PreviewModel model = previewController.getModel();

        model.getProperties().putValue(PreviewProperty.NODE_LABEL_SHOW_BOX, Boolean.FALSE);
        model.getProperties().putValue(PreviewProperty.SHOW_NODE_LABELS, Boolean.TRUE);
        model.getProperties().putValue(PreviewProperty.NODE_LABEL_FONT,
                model.getProperties().getFontValue(PreviewProperty.NODE_LABEL_FONT).deriveFont(50));
        model.getProperties().putValue(PreviewProperty.EDGE_COLOR, new EdgeColor(Color.BLACK));
        model.getProperties().putValue(PreviewProperty.EDGE_THICKNESS, new Float(0.5f));
        model.getProperties().putValue(PreviewProperty.EDGE_CURVED, Boolean.FALSE);
        model.getProperties().putValue(PreviewProperty.ARROW_SIZE, new Float(10f));
        model.getProperties().putValue(PreviewProperty.NODE_OPACITY, new Float(0f));
        model.getProperties().putValue(PreviewProperty.NODE_LABEL_BOX_OPACITY, new Float(80f));
        previewController.refreshPreview();

        //New Processing target, get the PApplet
        ProcessingTarget target = (ProcessingTarget) previewController.getRenderTarget(RenderTarget.PROCESSING_TARGET);
        PApplet applet = target.getApplet();
        applet.init();
        //Refresh the preview and reset the zoom
        previewController.render(target);
        target.refresh();
        target.resetZoom();

        //Add the applet to a JFrame and display
        JFrame frame = new JFrame("Test Preview");
        frame.setLayout(new BorderLayout());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(applet, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
        frame.repaint();
    }

    public static void main(String[] args) {
        TestGraph previewJFrame = new TestGraph();
        previewJFrame.script();
    }
}
