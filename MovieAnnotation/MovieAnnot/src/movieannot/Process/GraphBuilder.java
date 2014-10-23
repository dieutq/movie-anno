/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movieannot.Process;

/**
 *
 * @author dieutq
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
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

public class GraphBuilder {

    public void script(JPanel Panel, List<String> lstCharacter, float[][] rlRelationship) {
        //Init a project - and therefore a workspace
        ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
        pc.newProject();
        Workspace workspace = pc.getCurrentWorkspace();

        //Get a graph model - it exists because we have a workspace
        GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();

        List<Node> ListNodes = new ArrayList<>();
        String nd = "Node ";
        for (int i = 0; i < lstCharacter.size(); i++) {
            ListNodes.add(graphModel.factory().newNode(String.valueOf(i)));
            ListNodes.get(i).getNodeData().setLabel(lstCharacter.get(i));
        }

        List<Edge> edg = new ArrayList<>();
        for (int i = 0; i < lstCharacter.size(); i++) {
            for (int j = 0; j < lstCharacter.size(); j++) {
                if (rlRelationship[i][j] != 0) {
                    edg.add(graphModel.factory().newEdge(ListNodes.get(i), ListNodes.get(j), rlRelationship[i][j], false));
                }
            }
        }
        
        Edge e1 = graphModel.factory().newEdge(ListNodes.get(0), ListNodes.get(1), 0.1f, false);
        Edge e2 = graphModel.factory().newEdge(ListNodes.get(1), ListNodes.get(2), 0.5f, false);
        Edge e3 = graphModel.factory().newEdge(ListNodes.get(0), ListNodes.get(2), 0.7f, false);
        Edge e4 = graphModel.factory().newEdge(ListNodes.get(0), ListNodes.get(3), 0.7f, false);
        Edge e5 = graphModel.factory().newEdge(ListNodes.get(2), ListNodes.get(3), 0.7f, false);
                
        //Get a UndirectedGraph now and count edges
        UndirectedGraph undirectedGraph = graphModel.getUndirectedGraph();
        GraphView view = undirectedGraph.getView();
        for (int i = 0; i < ListNodes.size(); i++) {
            undirectedGraph.addNode(ListNodes.get(i));
        }
        for (int i = 0; i < edg.size(); i++) {
            undirectedGraph.addEdge(edg.get(i));
        }
       
        //Rank Graph
        AttributeModel attributeModel = Lookup.getDefault().lookup(AttributeController.class).getModel();
        GraphDistance distance = new GraphDistance();
        distance.setDirected(false);
        distance.execute(graphModel, attributeModel);

        AttributeColumn col = attributeModel.getNodeTable().getColumn(GraphDistance.CLOSENESS);
        for (Node n : undirectedGraph.getNodes()) {
            Double centrality = (Double) n.getNodeData().getAttributes().getValue(col.getIndex());         
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
        PreviewModel model = previewController.getModel(workspace);

        model.getProperties().putValue(PreviewProperty.NODE_LABEL_SHOW_BOX, Boolean.FALSE);
        model.getProperties().putValue(PreviewProperty.SHOW_NODE_LABELS, Boolean.TRUE);
        model.getProperties().putValue(PreviewProperty.NODE_LABEL_FONT,
                model.getProperties().getFontValue(PreviewProperty.NODE_LABEL_FONT).deriveFont(50));
        model.getProperties().putValue(PreviewProperty.EDGE_COLOR, new EdgeColor(Color.BLACK));
        model.getProperties().putValue(PreviewProperty.EDGE_THICKNESS, new Float(1f));
        model.getProperties().putValue(PreviewProperty.EDGE_CURVED, Boolean.TRUE);
        model.getProperties().putValue(PreviewProperty.ARROW_SIZE, new Float(10f));
        model.getProperties().putValue(PreviewProperty.NODE_OPACITY, new Float(0f));
        model.getProperties().putValue(PreviewProperty.NODE_LABEL_BOX_OPACITY, new Float(80f));
        previewController.refreshPreview();

        //model.getTopLeftPosition().setLocation(new Point(Panel.getX(), Panel.getY()));
        //model.getTopLeftPosition().setLocation(0,-135);
        //New Processing target, get the PApplet
        ProcessingTarget target = (ProcessingTarget) previewController.getRenderTarget(RenderTarget.PROCESSING_TARGET);
        PApplet applet = target.getApplet();
        //OKapplet.setSize(500,500);
        applet.init();

        //Refresh the preview and reset the zoom
        previewController.render(target);
        target.refresh();
        target.resetZoom();

        //Add the applet to a JFrame and display
        Panel.add(applet, BorderLayout.CENTER);
        Panel.setVisible(true);
        Panel.repaint();
    }

}
