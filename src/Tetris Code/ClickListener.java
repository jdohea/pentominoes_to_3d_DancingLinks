import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
/**
* An action listener that prints a message. 
*/
public class ClickListener implements ActionListener{

    public void actionPerformed(ActionEvent event) {
    	if (event.getSource()==Game.restartButton){
    		Game.f.remove(Game.temporaryPanel);
        	Game.mainPanel.setVisible(true); 
        	//Game.playGame();

    	}else if (event.getSource()==Game.quitButton){
        	//close the window
        	System.exit(0);
        }
	}

}