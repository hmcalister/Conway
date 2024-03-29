package Conway;
/**
 * Hayden McAlister
 * CONWAY GAME OF LIFE
 * Aug 2019
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Conway.Constant;

public class CellPanel extends JPanel{
    /**
     * Add the cells to the panel
     * 
     */
    public CellPanel(){
        //Set the size and background of the game panel
        setPreferredSize(new Dimension(Constant.CELL_PANEL_WIDTH,Constant.CELL_PANEL_HEIGHT));
        setBackground(new Color(240,240,255));
        
        //Add the mouse listeners to check for mouse events
        addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent event) {
                try{
                   Cell.toggleState(event.getX()/Constant.CELL_SIZE, event.getY()/Constant.CELL_SIZE);
                } catch (ArithmeticException e){
                    //Do nothing, user has clicked a zero which will simply be ignored
                }
                repaint();
            }                
            //Override remaining methods
            public void mouseExited(MouseEvent event) {}                
            public void mouseEntered(MouseEvent event) {}
            public void mousePressed(MouseEvent event) {}
            public void mouseReleased(MouseEvent event) {}
        });
        addMouseMotionListener(new MouseMotionListener(){
            //Update a cell's filled flag when clicked
            Cell currentCell = null; //keep track of what cell has last been dragged on, to avoid many activations of same cell
            public void mouseDragged(MouseEvent event){
                try{//Ensure event occurs in cell array
                    if(event.getX()/Constant.CELL_SIZE < 0 ||
                       event.getX()/Constant.CELL_SIZE >= Constant.NUM_CELLS_X ||
                       event.getY()/Constant.CELL_SIZE < 0 ||
                       event.getY()/Constant.CELL_SIZE >= Constant.NUM_CELLS_Y){
                        //User has selected something from outside the cell array, do nothing
                        
                    } else if(currentCell!=Cell.getCell(event.getX()/Constant.CELL_SIZE, event.getY()/Constant.CELL_SIZE)){ //Check to see if cell was just activated
                        currentCell=Cell.getCell(event.getX()/Constant.CELL_SIZE, event.getY()/Constant.CELL_SIZE); //Update the most recent cell
                        currentCell.toggleState(); //Activate the cell
                        repaint();
                    }
                } catch (ArrayIndexOutOfBoundsException e){
                    //Do nothing, event is simply outside array of cells
                }
            }
            public void mouseMoved(MouseEvent event){} //Override
        });
        
        //Add the cells
        Cell.initalise();
    }
    
    //Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Cell.displayAll(g);
    }
    
}
