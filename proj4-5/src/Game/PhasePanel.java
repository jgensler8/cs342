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
	private Hand _otherHand;			//hand in hand part panel
	private JPanel phaseHandOnePanel;
	private JPanel phaseHandTwoPanel;
	private Hand phaseHandOne;			//cards you are playing 
	private Hand phaseHandTwo;			//more cards you are playing
	
	public PhasePanel(int whichPhase, Hand myHand) {
		this.setBackground( Color.RED );
		this.p = whichPhase;
		this.phaseHandOne = new Hand();
		this.phaseHandTwo = new Hand();
		this._otherHand = myHand;
		
		switch( whichPhase){
		case 1:
		case 2:
		case 3:
		case 7:
		case 9:
		case 10:
			phaseHandTwoPanel = new JPanel();
			phaseHandTwoPanel.setBounds(0, 143, 448, 138);
			phaseHandTwoPanel.setBackground(Color.BLUE);
			this.add(phaseHandTwoPanel);
		case 4:
		case 5:
		case 6:
		case 8:
			phaseHandOnePanel = new JPanel();
			phaseHandOnePanel.setBounds(0, 0, 448, 138);
			phaseHandOnePanel.setBackground(Color.GREEN);
			this.add(phaseHandOnePanel);
		}
	}
	
	/**
	 * remove the currently selected card from the hand that was passed to the constructor.
	 * Place this exact card into the section that is specified by the argument
	 * @param hand , the hand that the currently selected card should be placed in
	 */
	public PhasePanel addTo( int hand){
		if(hand == 1){
			Card c = this._otherHand.cardSelected();
			c.unselect();
			this.phaseHandOne.addCard( c);
			this._otherHand.removeCard(c);
			this._otherHand.render( this._otherHand.getBackground() );
			
			this.phaseHandOnePanel.removeAll();
			this.phaseHandOnePanel.add( this.phaseHandOne.render(Color.BLUE) );
			this.phaseHandOnePanel.setBackground( Color.BLACK );
			this.phaseHandOnePanel.setSize( this.phaseHandOnePanel.getPreferredSize());
		}
		else if( hand == 2){
			Card c = this._otherHand.cardSelected();
			c.unselect();
			this.phaseHandTwo.addCard( c);
			this._otherHand.removeCard(c);
			this._otherHand.render( this._otherHand.getBackground() );
			
			this.phaseHandTwoPanel.removeAll();
			this.phaseHandTwoPanel.add( this.phaseHandTwo.render( Color.YELLOW));
			this.phaseHandOnePanel.setBackground( Color.YELLOW );
			this.phaseHandTwoPanel.setSize( this.phaseHandTwoPanel.getPreferredSize());
		}
		this.setSize( this.getPreferredSize());
		return this;
	}
}