package Border_Layouts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SpiralBorder {
    private JFrame mainWindow;
    private final int DEPTH_SIZE = 5;
    private final Dimension DIMENSION_SIZE = new Dimension(50,50);
    
    public SpiralBorder(int desiredDepth){
        createMainWindow();
        createSpiralJpanels(desiredDepth);
        mainWindow.setVisible(true);
    }

    private void createMainWindow() {
        this.mainWindow = new JFrame();
        mainWindow.setTitle("Spiral Border???");
        mainWindow.setSize(500,500);
        mainWindow.setLayout(new BorderLayout());
        mainWindow.setAlwaysOnTop(true);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createSpiralJpanels(int desiredDepth){
        int depthCount = 0;
        Container lastContainer = mainWindow;
        PaletteChanger p = new OuterPalette();
        List<JPanel> madeDepth;
        do{
            madeDepth = createDepth();
            setPanelBG(madeDepth, p.getPalette());
            setPanelPrefferedSize(madeDepth);
            arrangePanels(madeDepth, lastContainer);
            lastContainer = madeDepth.get(madeDepth.size() - 1);
            lastContainer.setLayout(new BorderLayout());
            depthCount++;
            if(p instanceof OuterPalette){
                p = new InnerPalette();
            }
            else{
                p = new OuterPalette();
            }
        }while(depthCount != desiredDepth);
    }

    private List<JPanel> createDepth(){
        List<JPanel> madePanels = new ArrayList<>();
        for(int i = 0; i < DEPTH_SIZE; i++){
            madePanels.add(new JPanel());
        }
        return madePanels;
    }

    private void setPanelBG(List<JPanel> allPanels, List<Color> palette){
        int i,j;
        i = j = 0;  
        ArrayList<Color> p = new ArrayList<>(palette);
        Collections.shuffle(p);
        while(i < allPanels.size() || j < p.size()){
            allPanels.get(i).setBackground(p.get(j));
            i++;
            j++;
        }
    }

    private void setPanelPrefferedSize(List<JPanel> allPanels){
        for(JPanel jPanel : allPanels){
            jPanel.setPreferredSize(DIMENSION_SIZE);
        }
    }

    private void arrangePanels(List<JPanel> allJPanels, Container component){
        List<Object> constraints = List.of(BorderLayout.NORTH,
                                           BorderLayout.WEST,
                                           BorderLayout.EAST,
                                           BorderLayout.SOUTH,
                                           BorderLayout.CENTER);
        
        int i = 0;
        for(JPanel jPanel : allJPanels){
            component.add(jPanel, constraints.get(i));
            i++;
        }
    }

    public interface PaletteChanger{
        public List<Color> getPalette();
    }

    public class OuterPalette implements PaletteChanger{
        @Override
        public List<Color> getPalette(){
            return List.of( Color.red,
                            Color.green,
                            Color.yellow,
                            Color.blue,
                            Color.white);
        }
    }

    public class InnerPalette implements PaletteChanger{
        @Override
        public List<Color> getPalette() {
            return List.of( Color.magenta,
                            Color.orange,
                            Color.gray,
                            Color.lightGray,
                            Color.black);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SpiralBorder b = new SpiralBorder(15);
            }
        });
    }
}
