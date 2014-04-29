package Game;

import java.awt.Color;
import java.io.Serializable;

import javax.swing.*;

public class PhasePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int p;
	private int handOneLimit;
	private int handTwoLimit;
	private Hand _otherHand;			//hand in hand part panel
	private JPanel phaseHandOnePanel;
	private JPanel phaseHandTwoPanel;
	private Hand phaseHandOne;			//cards you are playing 
	private Hand phaseHandTwo;			//more cards you are playing
	
	public PhasePanel(int whichPhase, Hand myHand) {
		this.setLayout(null);
		this.setBounds(0, 23, 448, 240);
		this.setBackground( Color.RED );
		this.p = whichPhase;
		this.phaseHandOne = new Hand();
		this.phaseHandTwo = new Hand();
		this._otherHand = myHand;
		
		switch(p){
		case 1:
		case 2:
		case 3:
		case 7:
		case 9:
		case 10:
			phaseHandTwoPanel = new JPanel();
			phaseHandTwoPanel.setBounds(0, 110, 448, 109);
			phaseHandTwoPanel.setBackground(Color.BLUE);
			this.add(phaseHandTwoPanel);
		case 4:
		case 5:
		case 6:
		case 8:
			phaseHandOnePanel = new JPanel();
			phaseHandOnePanel.setBounds(0, 10, 448, 109);
			phaseHandOnePanel.setBackground(Color.GREEN);
			this.add(phaseHandOnePanel);
		}
		
		switch(p){
		case 1: handOneLimit = 3; handTwoLimit = 3; break;
		case 2: handOneLimit = 3; handTwoLimit = 4; break;
		case 3: handOneLimit = 4; handTwoLimit = 4; break;
		case 4: handOneLimit = 7; handTwoLimit = 0; break;
		case 5: handOneLimit = 8; handTwoLimit = 0; break;
		case 6: handOneLimit = 9; handTwoLimit = 0; break;
		case 7: handOneLimit = 4; handTwoLimit = 4; break;
		case 8: handOneLimit = 7; handTwoLimit = 0; break;
		case 9: handOneLimit = 5; handTwoLimit = 2; break;
		case 10: handOneLimit = 5; handTwoLimit = 3; break;
		}
	}
	
	/**
	 * remove the currently selected card from the hand that was passed to the constructor.
	 * Place this exact card into the section that is specified by the argument
	 * @param hand , the hand that the currently selected card should be placed in
	 */
	public PhasePanel addTo( int hand){
		if(hand == 1){
			if (handOneLimit > 0) {
				Card c = this._otherHand.cardSelected();
				c.unselect();
				this._otherHand.removeCard(c);
				this.phaseHandOne.addCard( c);
				
				this.render();
				
				this._otherHand.render( this._otherHand.getBackground() );
			}
		}
		else if( hand == 2){
			if (handTwoLimit > 0) {
				Card c = this._otherHand.cardSelected();
				c.unselect();
				this._otherHand.removeCard(c);
				this.phaseHandTwo.addCard( c);
				
				this.render();
				
				this._otherHand.render( this._otherHand.getBackground() );
			}
		}
		this.setSize( this.getPreferredSize());
		//System.out.println(this);
		return this;
	}
	
	/**
	 * 
	 */
	public void returnAllCards(Hand usersHand) {
		usersHand.addCards(phaseHandOne.removeAllCards());
		usersHand.addCards(phaseHandTwo.removeAllCards());
		this.render();
	}
	
	/**
	 * 
	 */
	public void render(){
		this.phaseHandOnePanel.removeAll();
		this.phaseHandOnePanel.add( this.phaseHandOne.render(Color.YELLOW) );
		this.phaseHandOnePanel.setBackground( Color.YELLOW );
		this.phaseHandOnePanel.setSize( this.phaseHandOnePanel.getPreferredSize());

		this.phaseHandTwoPanel.removeAll();
		this.phaseHandTwoPanel.add( this.phaseHandTwo.render( Color.YELLOW));
		this.phaseHandTwoPanel.setBackground( Color.YELLOW );
		this.phaseHandTwoPanel.setSize( this.phaseHandTwoPanel.getPreferredSize());

		this.setSize( this.getPreferredSize());
	}
}